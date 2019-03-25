package com.hxb.smart.tomcat.servlet;

import com.hxb.smart.tomcat.lifecycle.LifeCycle;
import com.hxb.smart.tomcat.protocol.HttpRequest;
import com.hxb.smart.tomcat.protocol.HttpResponse;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 14:23:14
 */
public abstract class AbstractServlet implements LifeCycle {

    @Override
    public void init() {

    }

    /**
     * subclass must be rewrite
     */
    public void doGet(HttpRequest request, HttpResponse response){
        throw new RuntimeException();
    }

    /**
     * subclass must be rewrite
     */
    public void doPost(HttpRequest request, HttpResponse response){
        throw new RuntimeException();
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void destroy() {

    }
}
