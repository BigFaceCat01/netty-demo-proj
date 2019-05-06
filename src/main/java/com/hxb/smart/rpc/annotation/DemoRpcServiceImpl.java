package com.hxb.smart.rpc.annotation;

import java.lang.annotation.*;

/**
 * api接口实现
 * @author Created by huang xiao bao
 * @date 2019-05-06 17:17:09
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DemoRpcServiceImpl {
}
