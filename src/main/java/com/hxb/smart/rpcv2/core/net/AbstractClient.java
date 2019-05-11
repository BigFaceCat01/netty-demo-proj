package com.hxb.smart.rpcv2.core.net;

import com.hxb.smart.rpcv2.core.net.connect.AbstractConnect;
import com.hxb.smart.rpcv2.core.net.param.RpcRequest;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:09:26
 */
public abstract class AbstractClient {
    public abstract void send(String address, RpcRequest rpcRequest, Class<? extends AbstractConnect> connectImpl) throws Exception;
}