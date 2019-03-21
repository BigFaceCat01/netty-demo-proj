package com.hxb.smart.factorial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.ssl.SslContext;

import static com.hxb.smart.constant.Constant.LOCAL_HOST;
import static com.hxb.smart.constant.Constant.PORT;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 10:31:24
 */
public class FactorialClientInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;

    public FactorialClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(ch.alloc(),LOCAL_HOST ,PORT));
        }

        // Enable stream compression (you can remove these two if unnecessary)
        pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
        pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

        // Add the number codec first,
        pipeline.addLast(new NumberArrayDecode());
        pipeline.addLast(new NumberArrayEncode());

        // and then business logic.
        pipeline.addLast(new NumberArrayClientHandler());
    }
}
