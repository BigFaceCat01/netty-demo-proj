package com.hxb.smart.rpc.api;

import com.hxb.smart.rpcv2.core.invoker.annotation.RpcApi;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-06 18:04:52
 */
@RpcApi(serviceName = "helloService",address = "192.168.10.148:9090")
public interface HelloService {
    /**
     * 做加法
     * @param a 整数
     * @param b 整数
     */
    int plus(int a,int b);
}
