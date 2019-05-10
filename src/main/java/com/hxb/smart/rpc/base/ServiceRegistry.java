package com.hxb.smart.rpc.base;

import java.util.List;

/**
 * 服务注册接口
 * @author Created by huang xiao bao
 * @date 2019-05-09 17:40:04
 */
public interface ServiceRegistry extends LifeCycle{
    List<ServiceInstance> get(String serviceName);
}
