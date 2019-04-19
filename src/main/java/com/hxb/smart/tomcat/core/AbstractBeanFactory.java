package com.hxb.smart.tomcat.core;

import com.hxb.smart.tomcat.exception.BeanException;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-19 17:17:11
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    @Override
    public Object getBean(String name) throws BeanException {
        return null;
    }

    @Override
    public Object getBean(String name, Class requiredType) throws BeanException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }
}
