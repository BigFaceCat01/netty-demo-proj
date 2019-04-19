package com.hxb.smart.tomcat.annotation;

import java.lang.annotation.*;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-19 13:42:42
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface RequestMapping {
    /**
     * 请求路径
     * @return
     */
    String value() default "";

    String requestMethod() default "";
}
