package com.hxb.smart.rpc.base;

import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-09 17:54:25
 */
public class DefaultServiceRegistry implements ServiceRegistry {
    private RpcConfig rpcConfig;

    @Override
    public List<ServiceInstance> get(String serviceName) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
