package com.hxb.smart.tomcat.v1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-19 13:48:37
 */
public class ExecuteBean {
    private Class<?> target;
    private Method method;
    private Object[] args;

    public Object execute(Object obj,Object... args){
        try {
            return method.invoke(obj,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
