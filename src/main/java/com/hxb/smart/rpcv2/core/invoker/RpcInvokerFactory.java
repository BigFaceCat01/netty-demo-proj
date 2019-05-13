package com.hxb.smart.rpcv2.core.invoker;

import com.hxb.smart.rpcv2.configuration.RpcConfig;
import com.hxb.smart.rpcv2.core.callback.BaseCallback;
import com.hxb.smart.rpcv2.core.invoker.router.Router;
import com.hxb.smart.rpcv2.core.net.AbstractClient;
import com.hxb.smart.rpcv2.core.net.connect.AbstractConnect;
import com.hxb.smart.rpcv2.core.net.impl.netty.client.NettyConnectClient;
import com.hxb.smart.rpcv2.core.net.param.RpcFutureResponse;
import com.hxb.smart.rpcv2.core.net.param.RpcRequest;
import com.hxb.smart.rpcv2.core.net.param.RpcResponse;
import com.hxb.smart.rpcv2.registry.ServiceRegistry;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;
import com.hxb.smart.rpcv2.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:04:11
 */
@Slf4j
public class RpcInvokerFactory {
    private AbstractClient client;
    private Router router;
    private ConcurrentHashMap<Long, RpcFutureResponse> futureResponsePool;
    private volatile ThreadPoolExecutor callbackExecutor;
    private Class<? extends AbstractConnect> connectImpl;
    private AbstractSerializer serializer;
    private ServiceRegistry serviceRegistry;

    public void asyncSend(RpcRequest rpcRequest, String serviceName, BaseCallback callback) {
        String address = router.router(serviceName, serviceRegistry);
        RpcFutureResponse rpcFutureResponse = new RpcFutureResponse();
        try {
            client.send(address, rpcRequest, serializer, connectImpl, this);
            setInvokerFuture(rpcRequest.getRequestId(), rpcFutureResponse);
            RpcResponse rpcResponse = rpcFutureResponse.get(100, TimeUnit.MILLISECONDS);
            callbackExecutor.execute(() -> callback.run(rpcResponse));
        } catch (TimeoutException e) {
            log.error("invoke time out : {}", rpcRequest, e);
        } catch (Exception e) {
            log.error("invoke error : {}", rpcRequest, e);
        }
    }

    public Object syncSend(RpcRequest rpcRequest, String serviceName) {
        String address = router.router(serviceName, serviceRegistry);
        RpcFutureResponse rpcFutureResponse = new RpcFutureResponse();
        try {
            client.send(address, rpcRequest, serializer, connectImpl, this);
            setInvokerFuture(rpcRequest.getRequestId(), rpcFutureResponse);
            RpcResponse rpcResponse = rpcFutureResponse.get(100, TimeUnit.MILLISECONDS);
            log.info("调用结果：{}",rpcResponse);
            return rpcResponse.getResult();
        } catch (TimeoutException e) {
            log.error("invoke time out : {}", rpcRequest, e);
        } catch (Exception e) {
            log.error("invoke error : {}", rpcRequest, e);
        }
        return null;
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

    public void init() {
        this.futureResponsePool = new ConcurrentHashMap<>(32);

        ThreadFactory executeFactory = new ThreadFactory() {
            private String prefix = "callback execute pool-thread";
            private AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, prefix + count.getAndIncrement());
            }
        };
        this.callbackExecutor = new ThreadPoolExecutor(
                8,
                15,
                3,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(64), executeFactory);
    }

    public RpcInvokerFactory(RpcConfig rpcConfig) {
        this.client = rpcConfig.getNetType().getClientImpl();
        this.router = rpcConfig.getRouter();
        this.serializer = rpcConfig.getSerializer();
        this.serviceRegistry = rpcConfig.getServiceRegistry();
        this.connectImpl = rpcConfig.getNetType().getConnectImpl();

        init();
    }
}
