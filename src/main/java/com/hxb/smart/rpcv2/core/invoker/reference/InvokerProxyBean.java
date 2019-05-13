package com.hxb.smart.rpcv2.core.invoker.reference;

import com.hxb.smart.rpcv2.RpcFactory;
import com.hxb.smart.rpcv2.core.callback.BaseCallback;
import com.hxb.smart.rpcv2.core.invoker.RpcInvokerFactory;
import com.hxb.smart.rpcv2.core.invoker.annotation.RpcApi;
import com.hxb.smart.rpcv2.core.net.param.RpcRequest;
import com.hxb.smart.rpcv2.exception.RpcException;
import com.hxb.structure.util.SnowFlakesUtil;

import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:05:38
 */
public class InvokerProxyBean {
    private String serviceName;
    private Class<?>[] interfaces;
    private RpcRequest rpcRequest;
    private Object proxyBean;
    private RpcInvokerFactory rpcInvokerFactory;
    private BaseCallback callback;
    private boolean sync;


    public static InvokerProxyBean newProxyBean(Class<?> source,RpcFactory rpcFactory,BaseCallback resultCallBack){
        RpcApi rpcApi = source.getDeclaredAnnotation(RpcApi.class);
        if(Objects.isNull(rpcApi)){
            throw new RpcException(source.getName() + ": RpcApi not present");
        }
        RpcRequest rpcRequest = RpcRequest.builder()
                .className(source.getSimpleName())
                .build();
        rpcFactory.getServiceRegistry().refreshData(rpcApi.serviceName(),rpcApi.address());
        return new InvokerProxyBean(
                rpcFactory.getRpcInvokerFactory()
                ,rpcApi.serviceName()
                ,rpcRequest,
                resultCallBack,
                Objects.isNull(resultCallBack),
                source.getInterfaces());
    }

    public static InvokerProxyBean newProxyBean(Class<?> source,RpcFactory rpcFactory){
        return newProxyBean(source,rpcFactory,null);
    }

    public Object getObject(){
        if(Objects.nonNull(proxyBean)){
            return proxyBean;
        }
        synchronized (InvokerProxyBean.class) {
            if(Objects.nonNull(proxyBean)){
                return proxyBean;
            }
            this.proxyBean = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    interfaces,
                    (proxy, method, args) -> {
                        RpcRequest rpcRequest = getRpcRequest();
                        rpcRequest.setRequestId(SnowFlakesUtil.nextId());
                        rpcRequest.setMethodName(method.getName());
                        rpcRequest.setMethodParam(method.getParameterTypes());
                        rpcRequest.setParams(method.getParameterTypes());
                        if (sync) {
                            //同步调用
                            return rpcInvokerFactory.syncSend(rpcRequest, serviceName);
                        } else {
                            //异步调用
                            rpcInvokerFactory.asyncSend(rpcRequest, serviceName, callback);
                            return null;
                        }
                    });
            return proxyBean;
        }

    }

    public RpcRequest getRpcRequest() {
        return rpcRequest;
    }

    private InvokerProxyBean(RpcInvokerFactory rpcInvokerFactory, String serviceName, RpcRequest rpcRequest,BaseCallback callback,boolean sync,Class<?>[] interfaces){
        this.rpcInvokerFactory = rpcInvokerFactory;
        this.serviceName = serviceName;
        this.rpcRequest = rpcRequest;
        this.callback = callback;
        this.sync = sync;
        this.interfaces = interfaces;
    }
}
