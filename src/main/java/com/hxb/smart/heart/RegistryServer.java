package com.hxb.smart.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-15 15:58:30
 */
@Slf4j
public class RegistryServer {

    public static void main(String[] args) throws Exception{
        String host = "192.168.10.148";
        int port = 9090;

        ServerBootstrap server = new ServerBootstrap();

        EventLoopGroup workGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        server.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new MsgInbound1())
                                .addLast(new MsgInbound2())
                                .addLast(new StringDecoder())
                                .addLast(new MsgInbound3())
                                .addLast(new MsgOutbound1())
                                .addFirst(new MsgOutbound2())
                                .addFirst(new MsgOutbound3())
                                .addFirst(new StringEncoder());
                    }
                });
        Channel channel = server.bind(new InetSocketAddress(host, port))
                .sync()
                .channel();
        log.info(">>>>>>> run server in {}:{} success",host,port);
        channel.closeFuture().sync();
    }
}
