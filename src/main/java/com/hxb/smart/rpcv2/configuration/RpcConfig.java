package com.hxb.smart.rpcv2.configuration;

import com.hxb.smart.rpc.base.Resource;
import com.hxb.smart.rpc.base.ServiceInstance;
import com.hxb.smart.rpc.enums.RegistryType;
import com.hxb.smart.rpc.model.RpcProxyBean;
import lombok.Data;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-10 17:33:30
 */
@Data
public class RpcConfig {
    private String registryCenterAddress;
    private String token;
    private ConcurrentHashMap<String,List<ServiceInstance>> registryConfig;
    private ConcurrentHashMap<String,RpcProxyBean> proxyBeanConfig;
    private boolean initialize;
    private Resource resource;
    private int timeout;

    public void init(){

    }

    public List<ServiceInstance> getRegistry(String serviceName){
        return registryConfig.get(serviceName);
    }

    public RpcProxyBean getProxyBean(String interfaceName){
        return proxyBeanConfig.get(interfaceName);
    }



    private RpcConfig(){}
}
