package com.info.web.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类描述：
 * 创建人：yyf
 * 创建时间：2018/11/15 0015下午 06:34
 */

public class ThreadPoolInstance {
    private static ThreadPoolInstance threadPoolInstance ;
    ExecutorService executorService = Executors.newFixedThreadPool(4);

    private ThreadPoolInstance (){}
    public static ThreadPoolInstance getInstance() {
        if(threadPoolInstance == null){
            synchronized (ThreadPoolInstance.class) {
                if(threadPoolInstance == null){//二次检查
                    threadPoolInstance = new ThreadPoolInstance();
                }
            }
        }
        return threadPoolInstance;
    }

//    public static ThreadPoolInstance getInstance() {
//        if(threadPoolInstance == null){
//               threadPoolInstance = new ThreadPoolInstance();
//        }
//        return threadPoolInstance;
//    }


    public void doExecute(Runnable r) {
        executorService.execute(r);
    }
    public void shutdown() {
        executorService.shutdown();
    }



}
