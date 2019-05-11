package com.hxb.smart.rpcv2.core.net;

import com.hxb.smart.rpcv2.core.net.connect.AbstractConnect;
import com.hxb.smart.rpcv2.core.net.impl.netty.client.NettyClient;
import com.hxb.smart.rpcv2.core.net.impl.netty.client.NettyConnectClient;
import com.hxb.smart.rpcv2.core.net.impl.netty.server.NettyServer;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 17:52:30
 */
public enum NetType {
    NETTY(NettyServer.class,NettyClient.class,NettyConnectClient.class);



    private Class<? extends AbstractServer> serverImpl;
    private Class<? extends AbstractClient> clientImpl;
    private Class<? extends AbstractConnect> connectImpl;

    NetType(Class<? extends AbstractServer> serverImpl, Class<? extends AbstractClient> clientImpl, Class<? extends AbstractConnect> connectImpl) {
        this.serverImpl = serverImpl;
        this.clientImpl = clientImpl;
        this.connectImpl = connectImpl;
    }


}
