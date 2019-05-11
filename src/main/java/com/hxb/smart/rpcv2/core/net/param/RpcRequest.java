package com.hxb.smart.rpcv2.core.net.param;

import com.hxb.smart.rpc.base.DefaultBeanRegistry;
import com.hxb.smart.rpc.base.DefaultServiceRegistry;
import com.hxb.smart.rpc.invoker.RpcInvokerFactory;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-07 10:48:47
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    private Long requestId;
    private String className;
    private String methodName;
    private Class<?>[] methodParam;
    private Object[] params;

    public static void main(String[] args) {
        RpcInvokerFactory build = RpcInvokerFactory.builder()
                .config("")
                .beanRegistry(new DefaultBeanRegistry())
                .serviceRegistry(new DefaultServiceRegistry())
                .build();
    }
}
