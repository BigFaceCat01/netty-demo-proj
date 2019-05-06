package com.hxb.smart.rpc.api.impl;

import com.hxb.smart.rpc.annotation.DemoRpcServiceImpl;
import com.hxb.smart.rpc.api.HelloService;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-06 18:06:59
 */
@DemoRpcServiceImpl
public class HelloServiceImpl implements HelloService {
    @Override
    public int plus(int a, int b) {
        return a + b;
    }
}
