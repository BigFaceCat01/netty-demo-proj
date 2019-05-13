package com.hxb.smart.rpcv2.core.invoker.router.impl;

import com.hxb.smart.rpcv2.core.invoker.router.Router;
import com.hxb.smart.rpcv2.registry.ServiceRegistry;

import java.util.List;
import java.util.Random;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 18:10:27
 */
public class RandomRouter implements Router {
    private Random random = new Random();

    @Override
    public String router(String serviceName,ServiceRegistry serviceRegistry) {
        List<String> serviceInstances = serviceRegistry.get(serviceName);
        int size = serviceInstances.size();
        int r = random.nextInt(size);
        return serviceInstances.get(r);
    }
}
