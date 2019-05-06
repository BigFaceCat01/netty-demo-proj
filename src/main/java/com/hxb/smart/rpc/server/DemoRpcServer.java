package com.hxb.smart.rpc.server;

import com.hxb.smart.constant.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-06 17:19:45
 */
public class DemoRpcServer {
    public static void main(String[] args) {
        ServerBootstrap server = new ServerBootstrap();

        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        server.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<ServerChannel>() {
                    @Override
                    protected void initChannel(ServerChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpRequestDecoder())
                                .addLast(new HttpResponseEncoder())
                                .addLast(new HttpObjectAggregator(5*1024*1024))
                                .addLast(new DemoRpcServerHandler());
                    }
                });
        try {
            server.bind(Constant.LOCAL_HOST, Constant.PORT)
                    .channel()
                    .closeFuture()
                    .sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
