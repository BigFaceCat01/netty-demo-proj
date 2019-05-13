package com.hxb.smart.rpc.base;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-10 13:50:56
 */
public interface BeanRegistry {
    Object get(String interfaceName);
    <T> T get(String interfaceName,Class<T> target);
}
