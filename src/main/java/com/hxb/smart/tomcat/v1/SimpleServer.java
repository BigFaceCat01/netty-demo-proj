package com.hxb.smart.tomcat.v1;

import com.hxb.smart.tomcat.AbstractServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-04 16:51:37
 */
public class SimpleServer extends AbstractServer {

    @Override
    public void launch() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.channel(ServerSocketChannel.class)
                .group(bossGroup,workGroup)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new LoggingHandler())
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringEncoder())
                                .addLast(new StringDecoder());
                    }
                })
                .bind(this.host,this.port);
    }
}
