package com.hxb.smart.rpcv2.core.net.connect;

import com.hxb.smart.rpcv2.core.net.param.RpcRequest;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:52:51
 */
public abstract class AbstractConnect {

    private static ConcurrentHashMap<String, AbstractConnect> connectPool;
    private static ConcurrentHashMap<String, Object> lockPool = new ConcurrentHashMap<>(16);

    public abstract void init(String address, AbstractSerializer serializer) throws Exception;

    public abstract void close();

    public abstract void send(RpcRequest rpcRequest) throws Exception;

    public abstract boolean isAlive();


    public static void doSend(String address, RpcRequest rpcRequest, Class<? extends AbstractConnect> connectImpl) throws Exception {
        AbstractConnect connect = getConnect(address, connectImpl);
        connect.send(rpcRequest);
    }


    private static AbstractConnect getConnect(String address, Class<? extends AbstractConnect> connectImpl) throws Exception {

        if (Objects.isNull(connectPool)) {
            synchronized (AbstractConnect.class) {
                connectPool = new ConcurrentHashMap<>(16);
            }
        }

        AbstractConnect connect = connectPool.get(address);
        if(Objects.nonNull(connect) && connect.isAlive()){
            return connect;
        }
        Object lock = lockPool.get(address);
        if(Objects.isNull(lock)){
            lockPool.putIfAbsent(address,new Object());
            lock = lockPool.get(address);
        }

        synchronized (lock){
            connect = connectPool.get(address);
            if(Objects.nonNull(connect) && connect.isAlive()){
                return connect;
            }
            if(Objects.nonNull(connect)){
                //表示isAlive为false，移除有问题的通道
                connect.close();
                connectPool.remove(address);
            }
            AbstractConnect con = connectImpl.newInstance();
            con.init(address,null);
            connectPool.put(address,con);
            return con;
        }
    }

}
