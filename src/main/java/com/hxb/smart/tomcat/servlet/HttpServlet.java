package com.hxb.smart.tomcat.servlet;

import com.hxb.smart.tomcat.protocol.HttpRequest;
import com.hxb.smart.tomcat.protocol.HttpResponse;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 14:46:31
 */
public class HttpServlet extends AbstractServlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setContentType("ContentType:text/html;charset=utf-8");
        request.getServletContext().forward("index.ftl",request,response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        doGet(request,response);
    }
}
