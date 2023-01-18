package com.zuo.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;


/**
 * @author xiaocg
 */
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        channel.pipeline().addLast("socketChoose",new SocketChooseHandle());
        channel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
        channel.pipeline().addLast("commonhandler",new WebSocketHandler());
    }
}
