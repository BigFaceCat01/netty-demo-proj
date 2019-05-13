package com.hxb.smart.rpcv2.configuration;

import com.hxb.smart.rpc.base.Resource;
import com.hxb.smart.rpc.model.RpcProxyBean;
import com.hxb.smart.rpcv2.core.invoker.router.Router;
import com.hxb.smart.rpcv2.core.net.NetType;
import com.hxb.smart.rpcv2.registry.ServiceRegistry;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-10 17:33:30
 */
@Data
public class RpcConfig {
    private String registryCenterAddress;
    private int port;
    private String token;
    private ConcurrentHashMap<String,RpcProxyBean> proxyBeanConfig;
    private boolean initialize;
    private Resource resource;
    private String basePackage;
    private int timeout;
    private Router router;
    private ServiceRegistry serviceRegistry;
    private NetType netType;
    private AbstractSerializer serializer;


    public RpcProxyBean getProxyBean(String interfaceName){
        return proxyBeanConfig.get(interfaceName);
    }

    public static RpcConfig.RpcConfigBuilder builder(){
        return new RpcConfig.RpcConfigBuilder();
    }

    public static class RpcConfigBuilder{

        public RpcConfig build(){
            return new RpcConfig();
        }
    }

    private RpcConfig(){
        this.proxyBeanConfig = new ConcurrentHashMap<>(32);
    }
}
