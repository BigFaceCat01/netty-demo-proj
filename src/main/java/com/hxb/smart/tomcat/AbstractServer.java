package com.hxb.smart.tomcat;

import com.hxb.smart.constant.Constant;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-04 16:44:49
 */
public abstract class AbstractServer {
    protected String host;

    protected int port;

    protected AbstractServer(){
        this(Constant.LOCAL_HOST,Constant.PORT);
    }

    protected AbstractServer(String host, int port){
        this.host = host;
        this.port = port;
    }

    public abstract void launch();
}
