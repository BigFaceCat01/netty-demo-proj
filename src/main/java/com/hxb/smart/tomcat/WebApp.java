package com.hxb.smart.tomcat;

import com.hxb.smart.tomcat.constant.Constant;
import com.hxb.smart.tomcat.decode.HttpProtocolDecode;
import com.hxb.smart.tomcat.encode.HttpProtocolEncode;
import com.hxb.smart.tomcat.handler.HandlerIntercepter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Objects;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 10:35:43
 */
public class WebApp {

    private WebApp(){}

    public static void start(){
        start(Constant.HOST,Constant.PORT);
    }

    public static void start(String host,int port){
        assert  !Objects.isNull(host);

        EventLoopGroup workerGroup =new NioEventLoopGroup();
        EventLoopGroup bossGroup =new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(workerGroup,bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(
                                    new HttpProtocolEncode(),
                                    new HttpProtocolDecode(),
                                    new HandlerIntercepter()
                            );
                        }
                    });
            serverBootstrap.bind(com.hxb.smart.constant.Constant.LOCAL_HOST, com.hxb.smart.constant.Constant.PORT)
                    .channel()
                    .closeFuture()
                    .sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
