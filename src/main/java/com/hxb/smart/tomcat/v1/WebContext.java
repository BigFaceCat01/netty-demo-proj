package com.hxb.smart.tomcat.v1;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-19 13:46:29
 */
public class WebContext {
    /**
     * web app name
     */
    private String webName;
    private ConcurrentHashMap<String,ExecuteBean> pathPattern;
    private ConcurrentHashMap<String,Object> beanFactory;

    public ExecuteBean get(String path){
        if(Objects.isNull(pathPattern)){
            return null;
        }
        return pathPattern.get(path);
    }

    public <T> T get(String beanName,Class<T> tClass){
        if(Objects.isNull(beanFactory)){
            return null;
        }
        return (T)beanFactory.get(beanName);
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public ConcurrentHashMap<String, ExecuteBean> getPathPattern() {
        return pathPattern;
    }

    public void setPathPattern(ConcurrentHashMap<String, ExecuteBean> pathPattern) {
        this.pathPattern = pathPattern;
    }

    public ConcurrentHashMap<String, Object> getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(ConcurrentHashMap<String, Object> beanFactory) {
        this.beanFactory = beanFactory;
    }
}
