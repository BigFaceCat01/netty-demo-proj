package com.hxb.smart.factorial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import static com.hxb.smart.constant.Constant.LOCAL_HOST;
import static com.hxb.smart.constant.Constant.PORT;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 10:29:37
 */
public class FactorialClient {
    static final boolean SSL = System.getProperty("ssl") != null;

    public static void main(String[] args) throws Exception{
        new FactorialClient().launch();
    }

    public void launch() throws Exception {
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new FactorialClientInitializer(sslCtx));

            // Make a new connection.
            ChannelFuture f = b.connect(LOCAL_HOST, PORT).sync();

            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
