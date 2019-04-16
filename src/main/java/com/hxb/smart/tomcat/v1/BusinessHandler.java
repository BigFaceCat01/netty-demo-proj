package com.hxb.smart.tomcat.v1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-16 10:44:31
 */
public class BusinessHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println("请求路径："+msg.uri()+"；请求方法："+msg.method());
    }
}
