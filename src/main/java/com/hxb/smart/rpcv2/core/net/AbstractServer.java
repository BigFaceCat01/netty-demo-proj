package com.hxb.smart.rpcv2.core.net;

import com.hxb.smart.rpcv2.core.callback.BaseCallback;
import com.hxb.smart.rpcv2.serializer.AbstractSerializer;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:09:20
 */
public abstract class AbstractServer {
    private BaseCallback startCallBack;
    private BaseCallback stopCallBack;

    public abstract void init(String address, AbstractSerializer serializer) throws Exception;

    public void start(){}

    public void onStart(){

    }
}
