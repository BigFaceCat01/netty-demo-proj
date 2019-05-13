package com.hxb.smart.rpcv2.core.provider;

import com.hxb.smart.rpcv2.configuration.RpcConfig;
import com.hxb.smart.rpcv2.core.net.AbstractServer;
import com.hxb.smart.rpcv2.exception.RpcException;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;
import com.hxb.smart.rpcv2.util.IpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-13 14:04:17
 */
@Slf4j
public class RpcProviderFactory {
    private ConcurrentHashMap<String,Object> providerObjects;
    private Scan scan;
    private AbstractServer server;
    private String address;
    private AbstractSerializer serializer;

    public Object get(String interfaceName){
        return providerObjects.get(interfaceName);
    }

    public void put(String interfaceName,Object object){
        providerObjects.put(interfaceName,object);
    }

    public void putAll(Map<String,Object> list){
        providerObjects.putAll(list);
    }

    public interface Scan{
        Map<String,Object> scan();
    }

    public RpcProviderFactory(RpcConfig rpcConfig,Scan scan){
        this.server = rpcConfig.getNetType().getServerImpl();
        this.scan = scan;
        this.address = IpUtil.buildLocalHost(rpcConfig.getPort());
        this.serializer = rpcConfig.getSerializer();

        init();
    }

    public void init(){
        this.providerObjects = new ConcurrentHashMap<>(32);

        putAll(scan.scan());

        try {
            server.init(address,serializer,this);
            log.info(">>>>>>> run server in {} success",address);
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }
}
