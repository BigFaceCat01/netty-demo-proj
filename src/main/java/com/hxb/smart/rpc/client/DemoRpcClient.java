package com.hxb.smart.rpc.client;

import com.hxb.smart.constant.Constant;
import com.hxb.smart.rpc.codec.SimpleDecode;
import com.hxb.smart.rpc.codec.SimpleEncode;
import com.hxb.smart.rpc.model.SimpleRpcResponse;
import com.hxb.smart.rpc.serializer.AbstractSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

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
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new SimpleDecode(SimpleRpcResponse.class,AbstractSerializer.SerializeEnum.HESSIAN))
                                .addLast(new SimpleEncode(AbstractSerializer.SerializeEnum.HESSIAN))
                                .addLast(new DemoRpcClientHandler());
                    }
                });
        try{
            client.connect((new InetSocketAddress("192.168.20.198",Constant.PORT)))
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
