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
    /**
     * 这里若实现类接口有多个则可以指定有效的接口，默认第零个接口,注意：须填写全类名
     * @return 接口类名
     */
    String[] value() default {};
}
