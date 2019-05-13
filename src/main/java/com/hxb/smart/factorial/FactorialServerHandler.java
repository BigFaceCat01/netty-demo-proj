package com.hxb.smart.factorial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-21 10:12:36
 */
public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger> {

    /**
     * 求阶乘
     * @param n
     * @return
     */
    private int factorial(int n){
        if(n <= 0){
            return 0;
        }
        return n == 1 ? 1 : n * factorial(n-1);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
        // Calculate the cumulative factorial and send it to the client.
        int result = factorial(msg.intValue());
        System.out.println("calculate:"+msg + "="+result);
        BigInteger b = BigInteger.valueOf(result);
        ctx.writeAndFlush(b);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
