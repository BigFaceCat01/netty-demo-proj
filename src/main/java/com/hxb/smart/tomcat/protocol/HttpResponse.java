package com.hxb.smart.tomcat.protocol;

import com.hxb.smart.tomcat.config.ServletContext;
import com.hxb.smart.tomcat.constant.ResponseStatus;

import java.io.OutputStream;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 14:40:44
 */
public class HttpResponse {

    private String contentType;

    private ResponseStatus status;

    private OutputStream outputStream;

    private ServletContext servletContext;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
