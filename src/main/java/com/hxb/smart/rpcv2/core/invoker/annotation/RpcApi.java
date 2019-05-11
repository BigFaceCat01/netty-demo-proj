package com.hxb.smart.rpcv2.core.invoker.annotation;

import java.lang.annotation.*;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 16:10:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface RpcApi {
    String serviceName();
}
