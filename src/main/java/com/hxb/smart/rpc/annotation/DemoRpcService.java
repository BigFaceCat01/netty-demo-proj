package com.hxb.smart.rpc.annotation;

import java.lang.annotation.*;

/**
 * api接口
 * @author Created by huang xiao bao
 * @date 2019-05-06 17:17:09
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DemoRpcService {
    /**
     *
     * @return service name
     */
    String serviceName();
}
