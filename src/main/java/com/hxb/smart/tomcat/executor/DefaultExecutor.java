package com.hxb.smart.tomcat.executor;

import com.hxb.smart.tomcat.protocol.AbstractProtocol;
import com.hxb.smart.tomcat.protocol.HttpRequest;
import com.hxb.smart.tomcat.protocol.HttpResponse;
import com.hxb.smart.tomcat.servlet.AbstractServlet;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by huang xiao bao
 * @date 2019-03-25 11:41:36
 */
public class DefaultExecutor {

    private final String prefix = "executor-pool-";

    private AtomicInteger increase;

    private volatile static DefaultExecutor instance;

    private ThreadPoolExecutor pool;

    private DefaultExecutor(){
        init();
    }

    private void init(){

        increase = new AtomicInteger(1);
        /**
         * rewrite thread name
         */
        ThreadFactory nameFactory = r -> {
            Thread thread = new Thread();
            thread.setName(prefix + increase.getAndIncrement());
            return thread;
        };
        pool = new ThreadPoolExecutor(
                6,
                8,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(128),nameFactory);
    }

    public static DefaultExecutor getInstance(){
        if(Objects.nonNull(instance)){
            return instance;
        }
        synchronized (DefaultExecutor.class){
            if(Objects.isNull(instance)){
                instance = new DefaultExecutor();
            }
            return instance;
        }
    }

    public void execute(Runnable task){
        pool.execute(task);
    }

    public void execute(AbstractServlet servlet, HttpRequest request, HttpResponse response){
        pool.execute(new ServletTask(servlet,request,response));
    }

    public Future<AbstractProtocol> submit(Callable<AbstractProtocol> task){
        return pool.submit(task);
    }

    private final class ServletTask implements Runnable{

        private AbstractServlet servlet;
        private HttpRequest request;
        private HttpResponse response;

        public ServletTask(AbstractServlet servlet,HttpRequest request,HttpResponse response){
            this.servlet = servlet;
            this.request = request;
            this.response = response;
        }

        @Override
        public void run() {
            servlet.service(request,response);
        }
    }

}
