package com.hxb.smart.rpcv2.core.invoker;

import com.hxb.smart.rpc.model.SimpleRpcFutureResponse;
import com.hxb.smart.rpc.model.SimpleRpcResponse;
import com.hxb.smart.rpcv2.core.callback.BaseCallback;
import com.hxb.smart.rpcv2.core.invoker.router.Router;
import com.hxb.smart.rpcv2.core.net.AbstractClient;
import com.hxb.smart.rpcv2.core.net.AbstractServer;
import com.hxb.smart.rpcv2.core.net.NetType;
import com.hxb.smart.rpcv2.core.net.impl.netty.client.NettyConnectClient;
import com.hxb.smart.rpcv2.core.net.param.RpcFutureResponse;
import com.hxb.smart.rpcv2.core.net.param.RpcRequest;
import com.hxb.smart.rpcv2.core.net.param.RpcResponse;
import com.hxb.smart.rpcv2.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:04:11
 */
@Slf4j
public class RpcInvokerFactory {
    private AbstractServer server;
    private AbstractClient client;
    private Router router;
    private ConcurrentHashMap<Long, RpcFutureResponse> futureResponsePool = new ConcurrentHashMap<>(32);
    private ThreadPoolExecutor callbackExecutor;

    public void asyncSend(RpcRequest rpcRequest, String serviceName, BaseCallback callback) {
        String address = router.router(serviceName,Router.Strategy.RANDOM);
        RpcFutureResponse rpcFutureResponse = new RpcFutureResponse();
        try {
            client.send(address,rpcRequest,NettyConnectClient.class);
            setInvokerFuture(rpcRequest.getRequestId(),rpcFutureResponse);
            RpcResponse rpcResponse = rpcFutureResponse.get(100, TimeUnit.MILLISECONDS);
            callback.run(rpcResponse);
        }catch (TimeoutException e) {
            log.error("invoke time out : {}",rpcRequest,e);
        }catch (Exception e){
            log.error("invoke error : {}",rpcRequest,e);
        }
    }

    public Object syncSend(RpcRequest rpcRequest, String serviceName) {
        String address = router.router(serviceName,Router.Strategy.RANDOM);
        RpcFutureResponse rpcFutureResponse = new RpcFutureResponse();
        try {
            client.send(address,rpcRequest,NettyConnectClient.class);
            setInvokerFuture(rpcRequest.getRequestId(),rpcFutureResponse);
            return rpcFutureResponse.get(100, TimeUnit.MILLISECONDS);
        }catch (TimeoutException e) {
            log.error("invoke time out : {}",rpcRequest,e);
            return RpcResponse.builder()
                            .exception(ThrowableUtil.toString(e))
                            .requestId(rpcRequest.getRequestId())
                            .build();
        }catch (Exception e){
            log.error("invoke error : {}",rpcRequest,e);
            return RpcResponse.builder()
                    .exception(ThrowableUtil.toString(e))
                    .requestId(rpcRequest.getRequestId())
                    .build();
        }
    }




    public void setInvokerFuture(Long requestId, RpcFutureResponse futureResponse) {
        futureResponsePool.put(requestId, futureResponse);
    }

    public void removeInvokerFuture(Long requestId) {
        futureResponsePool.remove(requestId);
    }

    public void notifyInvokerFuture(Long requestId, final RpcResponse rpcResponse) {
        // get
        final RpcFutureResponse futureResponse = futureResponsePool.get(requestId);
        if (futureResponse == null) {
            return;
        }

        futureResponse.setRpcResponse(rpcResponse);

        // do remove
        removeInvokerFuture(requestId);

    }
}
