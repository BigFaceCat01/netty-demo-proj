package com.hxb.smart.rpc.invoker;

import com.hxb.smart.rpc.model.SimpleRpcFutureResponse;
import com.hxb.smart.rpc.model.SimpleRpcResponse;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-07 16:20:34
 */
public class RpcInvokerFactory {
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
