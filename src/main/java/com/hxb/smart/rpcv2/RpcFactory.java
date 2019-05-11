package com.hxb.smart.rpcv2;

import com.hxb.smart.rpcv2.core.invoker.RpcInvokerFactory;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 10:57:30
 */
public class RpcFactory {
    private RpcInvokerFactory rpcInvokerFactory;



    public static RpcFactory.RpcFactoryBuilder builder(){
        return new RpcFactory.RpcFactoryBuilder();
    }

    private void init(){
        initInvokerFactory();

        initServiceRegistry();
    }


    private void initInvokerFactory(){

    }

    private void initServiceRegistry(){

    }

    public static class RpcFactoryBuilder{

        public RpcFactoryBuilder config(String configPath){return this;}

        public RpcFactory build(){
            RpcFactory rpcFactory = new RpcFactory();
            rpcFactory.init();
            return rpcFactory;
        }
    }


    public RpcInvokerFactory getRpcInvokerFactory() {
        return rpcInvokerFactory;
    }

    private RpcFactory(){}
}
