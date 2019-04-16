package com.hxb.smart.tomcat.v1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-04 17:29:38
 */
public class SimpleHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

    }
}
