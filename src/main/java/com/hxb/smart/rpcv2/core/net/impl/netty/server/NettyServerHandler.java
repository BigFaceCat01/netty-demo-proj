package com.hxb.smart.rpcv2.core.net.impl.netty.server;

import com.hxb.smart.rpcv2.core.net.param.RpcRequest;
import com.hxb.smart.rpcv2.core.net.param.RpcResponse;
import com.hxb.smart.rpcv2.core.provider.RpcProviderFactory;
import com.hxb.smart.rpcv2.util.ThrowableUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 13:50:43
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private RpcProviderFactory rpcProviderFactory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        RpcResponse invoke = invoke(msg);

        ctx.writeAndFlush(invoke);
    }

    private RpcResponse invoke(RpcRequest rpcRequest){
        Object target = rpcProviderFactory.get(rpcRequest.getClassName());
        try {
            Method method = target.getClass().getDeclaredMethod(rpcRequest.getMethodName(),rpcRequest.getMethodParam());
            Object invokeResult = method.invoke(target, rpcRequest.getParams());
            return RpcResponse.builder()
                    .requestId(rpcRequest.getRequestId())
                    .result(invokeResult)
                    .build();
        } catch (Exception e) {
            return RpcResponse.builder()
                    .requestId(rpcRequest.getRequestId())
                    .exception(ThrowableUtil.toString(e))
                    .build();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(),cause);
    }

    public NettyServerHandler(RpcProviderFactory rpcProviderFactory){
        this.rpcProviderFactory = rpcProviderFactory;
    }
}
