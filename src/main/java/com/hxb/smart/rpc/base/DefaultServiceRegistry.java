package com.hxb.smart.rpc.base;

import com.hxb.smart.rpc.annotation.DemoRpcService;
import com.hxb.structure.util.ClassScanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by huang xiao bao
 * @date 2019-05-09 17:54:25
 */
public class DefaultServiceRegistry extends AbstractServiceRegistry {

    @Override
    public List<Class<?>> scan() {
        List<Class<?>> classes = new ArrayList<>();
        ClassScanUtil.scan("com.hxb", source -> {
            if(source.isAnnotationPresent(DemoRpcService.class)){
                classes.add(source);
            }
        });
        return classes;
    }
}
