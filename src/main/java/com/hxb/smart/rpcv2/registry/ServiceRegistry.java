package com.hxb.smart.rpcv2.registry;

import com.hxb.smart.rpc.base.ServiceInstance;

import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 16:18:48
 */
public interface ServiceRegistry {
    List<String> get(String serviceName);
}
