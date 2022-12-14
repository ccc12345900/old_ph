package com.zuo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import java.util.List;

public class SocketChooseHandle extends ByteToMessageDecoder {
    /** 默认暗号长度为23 */
    private static final int MAX_LENGTH = 23;
    /** WebSocket握手的协议前缀 */
    private static final String WEBSOCKET_PREFIX = "GET /";

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String protocol = getBufStart(in);
        if (protocol.startsWith(WEBSOCKET_PREFIX)) {
            ctx.pipeline().addBefore("commonhandler","http-codec",new HttpServerCodec());

            // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
            ctx.pipeline().addBefore("commonhandler","aggregator",new HttpObjectAggregator(65535));

            // ChunkedWriteHandler：向客户端发送HTML5文件,文件过大会将内存撑爆
            ctx.pipeline().addBefore("commonhandler","http-chunked",new ChunkedWriteHandler());
            ctx.pipeline().addBefore("commonhandler","WebSocketAggregator",new WebSocketFrameAggregator(65535));
            //用于处理websocket, /ws为访问websocket时的uri
            ctx.pipeline().addBefore("commonhandler","ProtocolHandler", new WebSocketServerProtocolHandler("/ws"));

            ctx.pipeline().remove(StringDecoder.class);
            ctx.pipeline().remove(StringEncoder.class);



        }

        in.resetReaderIndex();
        ctx.pipeline().remove(this.getClass());

    }





    private String getBufStart(ByteBuf in){
        int length = in.readableBytes();
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }

        // 标记读位置
        in.markReaderIndex();
        byte[] content = new byte[length];
        in.readBytes(content);
        return new String(content);
    }
}
