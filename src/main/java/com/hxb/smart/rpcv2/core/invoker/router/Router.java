package com.hxb.smart.rpcv2.core.invoker.router;

import com.hxb.smart.rpcv2.registry.ServiceRegistry;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 17:43:29
 */
public interface Router {
    String router(String serviceName, ServiceRegistry serviceRegistry);

    public enum Strategy{
        ROUND,
        RANDOM;
    }
}
