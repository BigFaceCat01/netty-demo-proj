package com.hxb.smart.rpc.model;

import lombok.Data;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-08 16:26:20
 */
@Data
public class RpcProxyBean {
    private String address;
    private int port;
    private String className;
    private Class<?>[] interfaces;
}
