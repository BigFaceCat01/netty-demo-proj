package com.hxb.smart.rpc.base;

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
    private RegistryType registryType;
    private String registryCenterAddress;
    private int port;
    private String token;
    private ConcurrentHashMap<String,List<ServiceInstance>> registryConfig;
    private ConcurrentHashMap<String,RpcProxyBean> proxyBeanConfig;
    private boolean initialize;
    private Resource resource;

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
