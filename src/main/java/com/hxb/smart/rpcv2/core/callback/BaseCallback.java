package com.hxb.smart.rpcv2.core.callback;

import com.hxb.smart.rpcv2.core.net.param.RpcResponse;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-11 11:46:10
 */
public interface BaseCallback {
    void run(RpcResponse response);
}
