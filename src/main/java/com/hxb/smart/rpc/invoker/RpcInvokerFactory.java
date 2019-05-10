package com.hxb.smart.rpc.invoker;

import com.hxb.smart.rpc.base.BeanRegistry;
import com.hxb.smart.rpc.base.RpcConfig;
import com.hxb.smart.rpc.base.ServiceRegistry;
import com.hxb.smart.rpc.model.SimpleRpcFutureResponse;
import com.hxb.smart.rpc.model.SimpleRpcResponse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-07 16:20:34
 */
public class RpcInvokerFactory {
    /**
     * 获得注册服务
     */
    private ServiceRegistry serviceRegistry;
    /**
     * 获得注册的bean
     */
    private BeanRegistry beanRegistry;
    /**
     * 执行任务的线程池
     */
    private ThreadPoolExecutor taskPool;




    public static RpcInvokerFactoryBuilder builder(){
        return new RpcInvokerFactoryBuilder();
    }

    public static class RpcInvokerFactoryBuilder{
        void config(String configPath){}
        void config(RpcConfig rpcConfig){}
        ThreadPoolExecutor taskPool(ThreadPoolExecutor taskPool){
            return null;
        }
    }





    private static final RpcInvokerFactory RPC_INVOKER_FACTORY = new RpcInvokerFactory();
    public static RpcInvokerFactory getInstance(){
        return RPC_INVOKER_FACTORY;
    }
    private RpcInvokerFactory(){

    }
    private ConcurrentHashMap<Long,SimpleRpcFutureResponse> futureResponsePool = new ConcurrentHashMap<>(32);

    public void setInvokerFuture(Long requestId, SimpleRpcFutureResponse futureResponse){
        futureResponsePool.put(requestId, futureResponse);
    }

    public void removeInvokerFuture(Long requestId){
        futureResponsePool.remove(requestId);
    }

    public void notifyInvokerFuture(Long requestId, final SimpleRpcResponse simpleRpcResponse){

        // get
        final SimpleRpcFutureResponse futureResponse = futureResponsePool.get(requestId);
        if (futureResponse == null) {
            return;
        }

        futureResponse.setSimpleRpcResponse(simpleRpcResponse);

        // do remove
        removeInvokerFuture(requestId);

    }
}
