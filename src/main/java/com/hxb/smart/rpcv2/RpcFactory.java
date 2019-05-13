package com.hxb.smart.rpcv2;

import com.hxb.smart.rpcv2.configuration.RpcConfig;
import com.hxb.smart.rpcv2.core.invoker.RpcInvokerFactory;
import com.hxb.smart.rpcv2.core.provider.RpcProviderFactory;
import com.hxb.smart.rpcv2.core.provider.annotation.Rpc;
import com.hxb.smart.rpcv2.registry.ServiceRegistry;
import com.hxb.structure.util.ClassScanUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 10:57:30
 */
@Slf4j
public class RpcFactory {
    private RpcInvokerFactory rpcInvokerFactory;
    private RpcConfig rpcConfig;
    private RpcProviderFactory rpcProviderFactory;



    public static RpcFactory.RpcFactoryBuilder builder(){
        return new RpcFactory.RpcFactoryBuilder();
    }

    private void init(){
        initInvokerFactory();

        initServiceRegistry();

        initProviderFactory();
    }


    private void initInvokerFactory(){
        rpcInvokerFactory = new RpcInvokerFactory(rpcConfig.getNetType().getClientImpl(),rpcConfig.getRouter());
    }

    private void initProviderFactory(){
        rpcProviderFactory = new RpcProviderFactory(rpcConfig,()->{
            Map<String,Object> res = new HashMap<>(32);
            ClassScanUtil.scan(rpcConfig.getBasePackage(),(source)->{
                Rpc rpc = source.getDeclaredAnnotation(Rpc.class);
                if(Objects.isNull(rpc)){
                    return;
                }
                String iface = rpc.iface();
                String interfaceName;
                if("".equals(iface.trim())){
                    interfaceName = source.getInterfaces()[0].getName();
                }else {
                    interfaceName = rpc.iface();
                }
                Object o = null;
                try {
                    o = source.newInstance();
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
                res.put(interfaceName, o);
            });
            return res;
        });
    }

    private void initServiceRegistry(){
        rpcConfig.getServiceRegistry().start();
    }

    public static class RpcFactoryBuilder{
        private RpcConfig rpcConfig;

        public RpcFactoryBuilder config(String configPath){
            this.rpcConfig = RpcConfig.builder().build();
            return this;
        }


        public RpcFactory build(){
            return new RpcFactory(this.rpcConfig);
        }
    }


    public RpcInvokerFactory getRpcInvokerFactory() {
        return rpcInvokerFactory;
    }

    public RpcConfig getRpcConfig() {
        return rpcConfig;
    }

    public ServiceRegistry getServiceRegistry() {
        return rpcConfig.getServiceRegistry();
    }

    private RpcFactory(RpcConfig rpcConfig){
        this.rpcConfig = rpcConfig;
        init();
    }
}
