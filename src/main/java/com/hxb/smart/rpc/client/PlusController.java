package com.hxb.smart.rpc.client;

import com.hxb.smart.rpc.annotation.DemoRpcService;
import com.hxb.smart.rpc.api.HelloService;
import com.hxb.smart.rpc.api.impl.HelloServiceImpl;
import com.hxb.smart.rpc.invoker.RpcInvokerFactory;
import com.hxb.smart.rpc.model.SimpleRpcFutureResponse;
import com.hxb.smart.rpc.model.SimpleRpcRequest;
import com.hxb.smart.rpc.model.SimpleRpcResponse;
import com.hxb.structure.util.SnowFlakesUtil;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-07 11:27:11
 */
public class PlusController implements Runnable{
    @DemoRpcService
    private HelloService helloService;
    private Scanner scanner;
    private ChannelHandlerContext ctx;

    private RpcInvokerFactory rpcInvokerFactory = RpcInvokerFactory.getInstance();

    public PlusController(ChannelHandlerContext ctx){
        scanner = new Scanner(System.in);
        this.ctx = ctx;
        helloService = (HelloService) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{HelloService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                SimpleRpcRequest request = SimpleRpcRequest.builder()
                        .className(HelloServiceImpl.class.getName())
                        .methodName(method.getName())
                        .methodParam(method.getParameterTypes())
                        .params(args)
                        .requestId(SnowFlakesUtil.nextId())
                        .build();
                SimpleRpcFutureResponse simpleRpcFutureResponse = new SimpleRpcFutureResponse(request);
                rpcInvokerFactory.setInvokerFuture(request.getRequestId(),simpleRpcFutureResponse);
                ctx.writeAndFlush(request);
                SimpleRpcResponse simpleRpcResponse = simpleRpcFutureResponse.get(6, TimeUnit.SECONDS);
                return simpleRpcResponse.getResult();
            }
        });
    }

    @Override
    public void run() {
        while (true){
            String s = scanner.nextLine();
            if("q".equalsIgnoreCase(s)){
                break;
            }
            String[] split = s.split("\\+");
            int plus = helloService.plus(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
            System.out.println(s+" = " + plus);

        }
    }
}
