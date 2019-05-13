package com.hxb.smart.rpc.client;

import com.hxb.smart.rpc.invoker.RpcInvokerFactory;
import com.hxb.smart.rpc.model.SimpleRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-06 18:00:15
 */
@Slf4j
public class DemoRpcClientHandler extends SimpleChannelInboundHandler<SimpleRpcResponse> {

    private RpcInvokerFactory invokerFactory = RpcInvokerFactory.getInstance();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(new PlusController(ctx)).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleRpcResponse msg) throws Exception {
        invokerFactory.notifyInvokerFuture(msg.getRequestId(),msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(),cause);
        ctx.close();
    }
}
