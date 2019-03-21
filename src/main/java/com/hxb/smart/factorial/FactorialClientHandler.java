package com.hxb.smart.factorial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 10:32:27
 */
public class FactorialClientHandler extends SimpleChannelInboundHandler<BigInteger> {
    private Integer lastNum ;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        lastNum = Integer.valueOf(Math.round(Math.random()*10)+"");
        System.out.println(lastNum+"! = ?");
        ctx.writeAndFlush(lastNum);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, final BigInteger msg) {
        System.out.println(lastNum+"! = "+msg.intValue());
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
