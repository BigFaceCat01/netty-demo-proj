package com.hxb.smart.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-19 17:22:30
 */
public class DiscardServer {
    static final String LOCALHOST = "127.0.0.1";
    static final int DEFAULT_PORT = 19090;
    private String host;
    private int port;
    static final boolean SSL = System.getProperty("ssl") != null;

    static ChannelFuture f;

    public static void main(String[] args) throws Exception {
        new DiscardServer().launch();
    }

    public DiscardServer(String host,int port){
        this.host = host;
        this.port = port;
    }

    public DiscardServer(){
        this(LOCALHOST,DEFAULT_PORT);
    }

    public void launch() throws Exception{
        final SslContext sslCtx;
        if(SSL){
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(),ssc.privateKey()).build();
        }else {
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(new DiscardServerHandler());
                        }
                    });

            f = b.bind(host,port).sync();

            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
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
