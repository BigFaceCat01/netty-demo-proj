package com.hxb.smart.heart;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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
public class RegistryClient {
    private RegistryConfig config;

    public static void main(String[] args) throws Exception{
        String host = "192.168.10.148";
        int port = 9090;

        Bootstrap client = new Bootstrap();

        EventLoopGroup workGroup = new NioEventLoopGroup();
        client.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder())
                                .addLast(new ClientHandler(client))
                                .addLast(new StringDecoder());
                    }
                });
        Channel channel = client.connect(new InetSocketAddress(host, port))
                .sync()
                .channel();
        log.info(">>>>>>> connect server {}:{} success",host,port);
        channel.closeFuture().sync();
    }
}
