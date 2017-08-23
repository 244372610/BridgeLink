package com.orient.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolFactory {

    private static final ThreadPoolFactory instance = new ThreadPoolFactory();
 

    public ExecutorService getConsumeThreadPool() {
        return consumeThreadPool;
    }

    private ExecutorService consumeThreadPool;


    public static ThreadPoolFactory getInstance(){
        return instance;
    }


    private ThreadPoolFactory() {
        consumeThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


}
