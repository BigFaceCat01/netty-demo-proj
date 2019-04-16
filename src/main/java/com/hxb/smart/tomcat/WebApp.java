package com.hxb.smart.tomcat;

import com.hxb.smart.tomcat.v1.SimpleServer;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 10:35:43
 */
public class WebApp {

    private WebApp(){}

    public static void main(String[] args) {
        start(new SimpleServer());
    }

    public static void start(AbstractServer server){
        server.launch();
    }

}
