package com.zuo.netty;

import com.alibaba.fastjson.JSONObject;

import com.zuo.common.R;
import com.zuo.entity.FamilyData;
import com.zuo.service.DoorDataService;
import com.zuo.service.FamilyDataService;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaocg
 */
@Component
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static RabbitTemplate rabbitTemplate;

    private static FamilyDataService familyDataService;

    private static DoorDataService doorDataService;
    private static Map<String, Channel> map = new ConcurrentHashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate){
        WebSocketHandler.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setFamilyDataService(FamilyDataService familyDataService){
        WebSocketHandler.familyDataService = familyDataService;
    }

    @Autowired
    public void setDoorDataService(DoorDataService doorDataService){
        WebSocketHandler.doorDataService = doorDataService;
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
            //交给rabbitmq进行存储
            rabbitTemplate.convertAndSend("work-queue",jsonObject.toJSONString());
            //进行数据判断
            JSONObject data_obj = JSONObject.parseObject(jsonObject.toJSONString());
            data_obj = data_obj.getJSONObject("msg");
            Integer update = data_obj.getInteger("update");
            Integer id = data_obj.getInteger("id");
            JSONObject r_data = jsonObject.getJSONObject("r_data");
            if(update == null && r_data!=null)
            {
                //这里需要完善
                //首先获取到数据库中所有的数据
                List<FamilyData> familyDataById = familyDataService.getFamilyDataById(id);
                //对比
                List<FamilyData> familyData = new ArrayList<>();//存储对比之后的数据
                for(int i = 0;i <= 360;i++)//获取r_data发送过来的数据
                {
                    Double dis = r_data.getDouble("dis" + i);
                    String angle = r_data.getString("angle" + i);
                    for(FamilyData t : familyDataById)//获取数据库中的数据
                    {
                        if(t.getAngle().equals(angle) && Math.abs(dis - t.getRDist()) >= 1)//对比之后距离变化说明不是同一个点
                        {
                            FamilyData familyData1 = new FamilyData();
                            familyData1.setId(id);
                            familyData1.setAngle(angle);
                            familyData1.setRDist(dis);
                            familyData.add(familyData1);//存储发现不同的数据
                        }
                    }
                }
                //判断是否有人进入
            }
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
