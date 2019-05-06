package com.hxb.smart.rpc.client;

import com.hxb.smart.rpc.annotation.DemoRpcService;
import com.hxb.smart.rpc.api.HelloService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-06 18:00:15
 */
public class DemoRpcClientHandler extends SimpleChannelInboundHandler<FullHttpResponse> {
    @DemoRpcService
    private HelloService helloService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse msg) throws Exception {
        int plus = helloService.plus(2, 5);

    }
}
