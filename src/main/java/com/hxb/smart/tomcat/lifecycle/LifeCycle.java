package com.hxb.smart.tomcat.lifecycle;

import com.hxb.smart.tomcat.protocol.HttpRequest;
import com.hxb.smart.tomcat.protocol.HttpResponse;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 14:24:13
 */
public interface LifeCycle {
    void init();
    void service(HttpRequest request, HttpResponse response);
    void destroy();
}
