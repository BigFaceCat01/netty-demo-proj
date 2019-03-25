package com.hxb.smart.tomcat.protocol;

import com.hxb.smart.tomcat.config.ServletContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 14:40:34
 */
public class HttpRequest {
    /**
     * request method
     */
    private String method;
    /**
     * request uri
     */
    private String uri;

    private String url;

    private Map<String,String> header;

    private Map<String,String> parameter;

    private ServletContext servletContext;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public String getHeader(String key){
        return header.get(key);
    }

    public String getParameter(String key){
        return parameter.get(key);
    }

    public Map<String, String> getHeaders() {
        return header;
    }

    public Map<String, String> getParameters() {
        return parameter;
    }
}
