package com.hxb.smart.tomcat.protocol;

import com.hxb.smart.tomcat.constant.ResponseStatus;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 10:34:39
 */
public abstract class AbstractProtocol {
    /**
     * request method
     */
    private String method;
    /**
     * request uri
     */
    private String uri;

    private String url;

    private String contentType;

    private ResponseStatus status;

    private ConcurrentHashMap<String,String> header;

    private ConcurrentHashMap<String,String> parameter;


    public String getHeader(String key){
        return header.get(key);
    }

    public String getParameter(String key){
        return parameter.get(key);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ConcurrentHashMap getHeader() {
        return header;
    }

    public void setHeader(ConcurrentHashMap header) {
        this.header = header;
    }

    public ConcurrentHashMap getParameter() {
        return parameter;
    }

    public void setParameter(ConcurrentHashMap parameter) {
        this.parameter = parameter;
    }
}
