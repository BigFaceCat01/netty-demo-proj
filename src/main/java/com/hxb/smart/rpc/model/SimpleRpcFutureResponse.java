package com.hxb.smart.rpc.model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-07 15:53:27
 */
@Slf4j
public class SimpleRpcFutureResponse implements Future<SimpleRpcResponse> {

    private Object lock;
    private SimpleRpcRequest simpleRpcRequest;
    private SimpleRpcResponse simpleRpcResponse;
    private boolean done = false;

    public SimpleRpcFutureResponse(SimpleRpcRequest simpleRpcRequest){
        this.lock = new Object();
        this.simpleRpcRequest = simpleRpcRequest;
    }
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public SimpleRpcResponse get() throws InterruptedException, ExecutionException {
        try {
            return get(-1,TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            log.error("request timeout for requestId = {}",simpleRpcRequest.getRequestId());
            return simpleRpcResponse;
        }
    }

    public void setSimpleRpcResponse(SimpleRpcResponse simpleRpcResponse){
        this.simpleRpcResponse = simpleRpcResponse;
        synchronized (lock){
            this.done = true;
            lock.notifyAll();
        }
    }

    @Override
    public SimpleRpcResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if(isDone()){
            return simpleRpcResponse;
        }
        synchronized (lock){
            if(timeout < 0){
                lock.wait();
            }else {
                long t = TimeUnit.MILLISECONDS == unit ? timeout : TimeUnit.MILLISECONDS.convert(timeout,unit);
                lock.wait(t);
            }
        }
        if(!isDone()){
            throw new RuntimeException("xxl-rpc, request timeout at:"+ System.currentTimeMillis() +", request:" + simpleRpcRequest.toString());
        }
        return simpleRpcResponse;
    }
}
