package com.hxb.smart.tomcat.exception;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-19 16:58:42
 */
public class BeanException extends RuntimeException {
    public BeanException() {
    }

    public BeanException(String message) {
        super(message);
    }

    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanException(Throwable cause) {
        super(cause);
    }
}
