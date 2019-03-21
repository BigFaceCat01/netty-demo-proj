package com.hxb.smart.factorial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 15:28:55
 */
public class NumberArrayServerHandler extends SimpleChannelInboundHandler<List<Integer>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, List<Integer> msg) throws Exception {
        System.out.println("server accept: "+Arrays.toString(msg.toArray()));
        Collections.sort(msg);
        ctx.writeAndFlush(msg);
    }
}
