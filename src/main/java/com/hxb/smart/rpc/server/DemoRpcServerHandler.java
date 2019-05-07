package com.hxb.smart.rpc.server;

import com.hxb.smart.rpc.model.SimpleRpcRequest;
import com.hxb.smart.rpc.model.SimpleRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-06 17:34:12
 */
@Slf4j
public class DemoRpcServerHandler extends SimpleChannelInboundHandler<SimpleRpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleRpcRequest msg) throws Exception {
        log.info("收到RPC调用：{}",msg);
        SimpleRpcResponse simpleRpcResponse = doInvoke(msg);
        ctx.writeAndFlush(simpleRpcResponse);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(),cause);
        ctx.close();
    }

    private SimpleRpcResponse doInvoke(SimpleRpcRequest rpcRequest){
        String className = rpcRequest.getClassName();
        try {
            Class<?> target = Class.forName(className);
            Method targetMethod = target.getDeclaredMethod(rpcRequest.getMethodName(), rpcRequest.getMethodParam());
            Object result = targetMethod.invoke(target.newInstance(), rpcRequest.getParams());
            return SimpleRpcResponse.builder()
                    .result(result)
                    .requestId(rpcRequest.getRequestId())
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return SimpleRpcResponse.builder()
                    .exception(e.getMessage())
                    .requestId(rpcRequest.getRequestId())
                    .build();
        }
    }
}
