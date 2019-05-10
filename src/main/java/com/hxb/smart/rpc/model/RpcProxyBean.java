package com.hxb.smart.rpc.model;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-08 16:26:20
 */
@Data
public class RpcProxyBean {
    private String serviceName;
    private String className;
    private Class<?>[] interfaces;
    private Object proxyBean;

    public RpcProxyBean(String serviceName, String className, Class<?>[] interfaces) {
        this.serviceName = serviceName;
        this.className = className;
        this.interfaces = interfaces;
    }



    @SuppressWarnings("unchecked")
    public Object getProxyBean(){
        return Objects.nonNull(proxyBean) ? proxyBean : Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                interfaces,
                new ProxyBeanInvocationHandler());
    }

    @SuppressWarnings("unchecked")
    public Object getProxyBean(TimeoutCallback callback){
        return Objects.nonNull(proxyBean) ?
                proxyBean :
                Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                interfaces,
                new ProxyBeanInvocationHandler(callback));
    }

    public interface TimeoutCallback{

    }

    private static class ProxyBeanInvocationHandler implements InvocationHandler{
        private TimeoutCallback timeoutCallback;

        public ProxyBeanInvocationHandler(){
            this(null);
        }

        public ProxyBeanInvocationHandler(TimeoutCallback timeoutCallback) {
            this.timeoutCallback = timeoutCallback;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }

}
