package com.hxb.smart.file;

import com.hxb.smart.constant.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;


/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 16:32:28
 */
public class FileServer {

    public static void main(String[] args) throws Exception{
        new FileServer().launch();
    }

    public void launch() throws Exception{
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
                                    new StringEncoder(),
                                    new LineBasedFrameDecoder(8192),
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new ChunkedWriteHandler(),
                                    new FileServerHandler()
                                  );
                        }
                    });
            serverBootstrap.bind(Constant.LOCAL_HOST,Constant.PORT)
                    .channel()
                    .closeFuture()
                    .sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
