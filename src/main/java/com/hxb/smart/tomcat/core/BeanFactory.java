package com.hxb.smart.tomcat.core;

import com.hxb.smart.tomcat.exception.BeanException;

/**
 * @author Created by huang xiao bao
 * @date 2019-04-19 15:54:54
 */
public interface BeanFactory {
    /**
     * 根据bean的名字，获取在IOC容器中得到bean实例
     */
    Object getBean(String name) throws BeanException;

    /**
     * 根据bean的名字和Class类型来得到bean实例，增加了类型安全验证机制。
     */
    Object getBean(String name, Class requiredType) throws BeanException;

    /**
     * 提供对bean的检索，看看是否在IOC容器有这个名字的bean
     */
    boolean containsBean(String name);
}
