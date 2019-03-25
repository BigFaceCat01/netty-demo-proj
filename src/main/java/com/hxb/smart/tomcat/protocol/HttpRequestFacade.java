package com.hxb.smart.tomcat.protocol;

import java.util.Map;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 14:53:15
 */
public class HttpRequestFacade extends HttpRequest {

    private AbstractProtocol abstractProtocol;

    public HttpRequestFacade(){}

    public HttpRequestFacade(AbstractProtocol protocol){
        this.abstractProtocol = protocol;
    }

    @Override
    public String getMethod() {
        return abstractProtocol.getMethod();
    }

    @Override
    public String getUri() {
        return abstractProtocol.getUri();
    }

    @Override
    public String getUrl() {
        return abstractProtocol.getUrl();
    }

    @Override
    public String getHeader(String key) {
        return abstractProtocol.getHeader(key);
    }

    @Override
    public String getParameter(String key) {
        return abstractProtocol.getParameter(key);
    }

    @Override
    public Map<String, String> getHeaders() {
        return super.getHeaders();
    }

    @Override
    public Map<String, String> getParameters() {
        return super.getParameters();
    }
}
