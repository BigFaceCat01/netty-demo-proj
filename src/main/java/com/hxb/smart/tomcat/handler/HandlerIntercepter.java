package com.hxb.smart.tomcat.handler;

import com.hxb.smart.tomcat.config.ServletConfig;
import com.hxb.smart.tomcat.executor.DefaultExecutor;
import com.hxb.smart.tomcat.protocol.AbstractProtocol;
import com.hxb.smart.tomcat.protocol.HttpRequest;
import com.hxb.smart.tomcat.protocol.HttpRequestFacade;
import com.hxb.smart.tomcat.protocol.HttpResponse;
import com.hxb.smart.tomcat.servlet.AbstractServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 11:24:28
 */
public class HandlerIntercepter extends SimpleChannelInboundHandler<AbstractProtocol> {

    private static final String root = "/**";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractProtocol msg) throws Exception {
        HttpRequest request = new HttpRequestFacade(msg);
        HttpResponse response = new HttpResponse();

        ConcurrentHashMap<String, AbstractServlet> servlets = ServletConfig.getInstance().getServlets();

        DefaultExecutor.getInstance().execute(servlets.get(root),request,response);
    }
}
