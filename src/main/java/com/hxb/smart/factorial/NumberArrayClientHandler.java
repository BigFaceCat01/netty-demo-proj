package com.hxb.smart.factorial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 15:30:46
 */
public class NumberArrayClientHandler extends SimpleChannelInboundHandler<List<Integer>> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        List<Integer> a = Arrays.asList(9, 8, 1, 2, 3, 4, 5, 6, 7);
        System.out.println("client send: "+a);
        ctx.writeAndFlush(a);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, List<Integer> msg) throws Exception {
        System.out.println("sort result: "+Arrays.toString(msg.toArray()));
        ctx.close();
    }
}
