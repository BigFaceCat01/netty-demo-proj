package com.hxb.smart.rpcv2.core.net;

import com.hxb.smart.rpcv2.core.provider.RpcProviderFactory;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:09:20
 */
public abstract class AbstractServer {

    public abstract void init(String address, AbstractSerializer serializer, RpcProviderFactory rpcProviderFactory) throws Exception;
    public abstract void close();

}
