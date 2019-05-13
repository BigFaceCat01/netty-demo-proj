package com.hxb.smart.rpcv2.core.net.impl.netty.client;

import com.hxb.smart.rpcv2.core.invoker.RpcInvokerFactory;
import com.hxb.smart.rpcv2.core.net.AbstractClient;
import com.hxb.smart.rpcv2.core.net.connect.AbstractConnect;
import com.hxb.smart.rpcv2.core.net.param.RpcRequest;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:26:39
 */
public class NettyClient extends AbstractClient {

    @Override
    public void send(String address, RpcRequest rpcRequest, AbstractSerializer serializer, Class<? extends AbstractConnect> connectImpl, RpcInvokerFactory rpcInvokerFactory) throws Exception{
        AbstractConnect.doSend(address,rpcRequest,serializer, connectImpl,rpcInvokerFactory);
    }
}
