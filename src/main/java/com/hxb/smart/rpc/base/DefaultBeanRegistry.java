package com.hxb.smart.rpc.base;

import com.hxb.smart.rpc.model.RpcProxyBean;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-10 17:46:43
 */
@Slf4j
public class DefaultBeanRegistry implements BeanRegistry {
    private RpcConfig rpcConfig;

    @Override
    public Object get(String interfaceName) {
        return get(interfaceName,Object.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String interfaceName, Class<T> target) {
        RpcProxyBean rpcProxyBean = rpcConfig.getProxyBean(interfaceName);
        if(Objects.isNull(rpcProxyBean)){
            log.error("{} not found",interfaceName);
            throw new RuntimeException(interfaceName + "not found");
        }
        return (T)rpcProxyBean.getProxyBean();
    }
}
