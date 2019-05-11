package com.hxb.smart.rpcv2.core.net.impl.netty.server;

import com.hxb.smart.rpcv2.core.net.param.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 13:50:43
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
