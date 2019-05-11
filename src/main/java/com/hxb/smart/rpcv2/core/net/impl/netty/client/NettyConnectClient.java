package com.hxb.smart.rpcv2.core.net.impl.netty.client;

import com.hxb.smart.rpc.client.DemoRpcClientHandler;
import com.hxb.smart.rpc.model.SimpleRpcResponse;
import com.hxb.smart.rpcv2.core.net.connect.AbstractConnect;
import com.hxb.smart.rpcv2.core.net.impl.netty.protocol.impl.NettyDecode;
import com.hxb.smart.rpcv2.core.net.impl.netty.protocol.impl.NettyEncode;
import com.hxb.smart.rpcv2.core.net.param.RpcRequest;
import com.hxb.smart.rpcv2.core.net.param.RpcResponse;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;
import com.hxb.smart.rpcv2.util.IpUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 15:41:42
 */
public class NettyConnectClient extends AbstractConnect {

    private EventLoopGroup workGroup;
    private Channel channel;

    @Override
    public void init(String address, AbstractSerializer serializer) throws Exception{
        Object[] ipPort = IpUtil.parseIpPort(address);
        String host = (String) ipPort[0];
        int port = (int) ipPort[1];


        Bootstrap client = new Bootstrap();

        this.workGroup = new NioEventLoopGroup();

        client.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyDecode(RpcResponse.class, serializer))
                                .addLast(new NettyEncode(serializer))
                                .addLast(new NettyClientHandler(null));
                    }
                });
        this.channel = client.connect((new InetSocketAddress(host, port)))
                .sync()
                .channel();
    }

    @Override
    public void close() {

    }

    @Override
    public void send(RpcRequest rpcRequest) throws Exception{
        channel.writeAndFlush(rpcRequest).sync();
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
