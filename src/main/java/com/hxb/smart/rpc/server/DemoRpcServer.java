package com.hxb.smart.rpc.server;

import com.hxb.smart.constant.Constant;
import com.hxb.smart.rpc.codec.SimpleDecode;
import com.hxb.smart.rpc.codec.SimpleEncode;
import com.hxb.smart.rpc.model.SimpleRpcRequest;
import com.hxb.smart.rpc.model.SimpleRpcResponse;
import com.hxb.smart.rpc.serializer.AbstractSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new SimpleDecode(SimpleRpcRequest.class,AbstractSerializer.SerializeEnum.HESSIAN))
                                .addLast(new SimpleEncode(AbstractSerializer.SerializeEnum.HESSIAN))
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
