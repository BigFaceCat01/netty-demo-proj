package com.hxb.smart.rpc.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-07 10:48:47
 */
@Data
@Builder
public class SimpleRpcRequest implements Serializable {
    private Long requestId;
    private String className;
    private String methodName;
    private Class<?>[] methodParam;
    private Object[] params;
}
