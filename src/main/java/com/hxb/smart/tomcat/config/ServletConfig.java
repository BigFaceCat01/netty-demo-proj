package com.hxb.smart.tomcat.config;

import com.hxb.smart.tomcat.protocol.HttpResponse;
import com.hxb.smart.tomcat.servlet.AbstractServlet;
import com.hxb.smart.tomcat.servlet.HttpServlet;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 15:08:20
 */
public class ServletConfig {

    private volatile static ServletConfig instance;

    private ConcurrentHashMap<String,AbstractServlet> servlets;

    private ServletConfig(){}

    public ConcurrentHashMap<String, AbstractServlet> getServlets() {
        return servlets;
    }

    private void init(){
         servlets = new ConcurrentHashMap<>(128);
        HttpServlet root = new HttpServlet();
        //init servlet
        root.init();
         servlets.put("/**",root);
    }

    public static ServletConfig getInstance(){
        if(Objects.nonNull(instance)){
            return instance;
        }
        synchronized (ServletConfig.class){
            if(Objects.isNull(instance)){
                instance = new ServletConfig();
            }
            return instance;
        }
    }
}
