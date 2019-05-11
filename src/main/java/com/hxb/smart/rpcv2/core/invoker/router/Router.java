package com.hxb.smart.rpcv2.core.invoker.router;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 17:43:29
 */
public interface Router {
    String router(String serviceName,Strategy strategy);

    public enum Strategy{
        ROUND,
        RANDOM;
    }
}
