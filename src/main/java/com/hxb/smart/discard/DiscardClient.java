package com.hxb.smart.discard;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.util.Objects;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-20 13:59:53
 */
public class DiscardClient {
    static final boolean SSL = !Objects.isNull(System.getProperty("ssl"));
    static final int SIZE = 256;
    private String host;
    private int port;

    public static void main(String[] args) throws Exception {
        new DiscardClient().launch();
    }

    public DiscardClient(){
        this(DiscardServer.LOCALHOST,DiscardServer.DEFAULT_PORT);
    }

    public DiscardClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void launch() throws Exception{
        final SslContext sslCtx;
        if(SSL){
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        }else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();

                            if(sslCtx != null){
                                p.addLast(sslCtx.newHandler(ch.alloc(),host,port));
                            }
                            p.addLast(new DiscardClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host,port).sync();

            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
