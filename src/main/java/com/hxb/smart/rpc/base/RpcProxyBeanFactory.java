package com.hxb.smart.rpc.base;

import com.hxb.smart.rpc.annotation.DemoRpcService;
import com.hxb.smart.rpc.model.RpcProxyBean;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-10 11:31:25
 */
public class RpcProxyBeanFactory {

    public static RpcProxyBean build(Class<?> source){
        DemoRpcService demoRpcService = source.getDeclaredAnnotation(DemoRpcService.class);
        return new RpcProxyBean(demoRpcService.serviceName(),
                source.getName(),
                source.getInterfaces());
    }













    private RpcProxyBeanFactory(){}
}
