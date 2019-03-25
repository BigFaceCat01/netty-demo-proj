package com.hxb.smart.tomcat.constant;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 15:21:48
 */
public enum ResponseStatus {
    OK(200);

    private int statusCode;

    ResponseStatus(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
