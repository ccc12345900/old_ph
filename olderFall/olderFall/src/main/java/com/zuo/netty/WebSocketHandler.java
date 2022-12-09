package com.zuo.netty;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaocg
 */
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static RabbitTemplate rabbitTemplate;
    private static Map<String, Channel> map = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate){
        WebSocketHandler.rabbitTemplate = rabbitTemplate;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object object) throws Exception {
        String msg = "";
        if (object instanceof TextWebSocketFrame){
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame)object;
            msg = textWebSocketFrame.text();
        }else {
            msg = String.valueOf(object);
            System.out.println("发送信息的管道"+ctx.channel().id().asLongText());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg",msg);
            jsonObject.put("netty_code",ctx.channel().id().asLongText());
            rabbitTemplate.convertAndSend("work-queue",jsonObject.toJSONString());
        }
        System.out.println("msg:"+msg);

        for (String key: map.keySet()) {
            if (key.equals(ctx.channel().id().toString())){
                continue;
            }
            Channel channel = map.get(key);
            ChannelFuture channelFuture = channel.writeAndFlush(msg);
            if (!channelFuture.isSuccess()) {
                channel.writeAndFlush(new TextWebSocketFrame(msg));
            }
        }

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded::"+ctx.channel().id().asLongText());
        InetSocketAddress socketAddress = (InetSocketAddress)ctx.channel().remoteAddress();
        String hostAddress = socketAddress.getAddress().getHostAddress();
        logger.info("IP:{}",hostAddress);
        String clientId = ctx.channel().id().toString();
        map.put(clientId,ctx.channel());
        logger.info("map:{}",map.toString());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved::"+ctx.channel().id().asLongText());
        String clientId = ctx.channel().id().toString();
        String msg = "{" + "status:0" + "}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",msg);
        jsonObject.put("netty_code",ctx.channel().id().asLongText());
        rabbitTemplate.convertAndSend("work-queue",jsonObject.toJSONString());
        map.remove(clientId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught::"+cause.getMessage());
        String clientId = ctx.channel().id().toString();
        map.remove(clientId);
        ctx.close();
    }
}
