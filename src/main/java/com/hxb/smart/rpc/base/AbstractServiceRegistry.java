package com.hxb.smart.rpc.base;

import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-09 17:46:23
 */
public abstract class AbstractServiceRegistry implements ServiceRegistry,LifeCycle{
    @Override
    public final void init(){

    }

    public final void registry(){
        List<Class<?>> scan = scan();
    }

    @Override
    public final void destroy() {

    }
}
