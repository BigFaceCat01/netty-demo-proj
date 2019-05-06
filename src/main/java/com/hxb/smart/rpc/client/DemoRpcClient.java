package com.hxb.smart.rpc.client;

import com.hxb.smart.constant.Constant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-06 17:20:21
 */
public class DemoRpcClient {
    public static void main(String[] args) {
        Bootstrap client = new Bootstrap();

        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        client.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpResponseDecoder())
                                .addLast(new HttpRequestEncoder())
                                .addLast(new HttpObjectAggregator(5*1024*1024))
                                .addLast(new DemoRpcClientHandler());
                    }
                });
        try{
            client.connect(Constant.LOCAL_HOST,Constant.PORT)
                    .sync()
                    .channel()
                    .closeFuture()
                    .sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workGroup.shutdownGracefully();
        }

    }
}
