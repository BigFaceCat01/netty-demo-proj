package com.hxb.smart.rpc.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-10 17:17:35
 */
@Data
public class ServiceInstance implements Serializable {
    private String address;
    private int port;
}
