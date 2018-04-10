package com.info.web.synchronization;

import com.info.back.service.ThreadPoolDealOverdueLoan;
import com.info.back.task.ThreadPoolDealwithOrderUpgrade;
import com.info.back.task.ThreadPoolUpdateOrderInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Administrator
 * @Description:
 * @CreateTime 2018-03-13 下午 3:13
 **/
public class MyListener implements ServletContextListener {

    @Autowired
    private ThreadPoolDealwithOrderUpgrade threadPoolDealwithOrderUpgrade;

    @Autowired
    private ThreadPoolUpdateOrderInfo threadPoolUpdateOrderInfo;

    @Autowired
    private ThreadPoolDealOverdueLoan threadPoolDealOverdueLoan;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        threadPoolDealwithOrderUpgrade.destroy();
        threadPoolUpdateOrderInfo.destroy();
        threadPoolDealOverdueLoan.destroy();
        destory();
    }

    private Thread[] findAllThreads() {
        ThreadGroup group =
                Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // traverse the ThreadGroup tree to the top
        while ( group != null ) {
            topGroup = group;
            group = group.getParent();
        }
        // Create a destination array that is about
        // twice as big as needed to be very confident
        // that none are clipped.
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
        // Load the thread references into the oversized
        // array. The actual number of threads loaded
        // is returned.
        int actualSize = topGroup.enumerate(slackList);
        // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        return list;
    }

    public void destory(){
        Thread[] threads = findAllThreads();
        for(Thread thread : threads){
            thread.destroy();
        }
    }
}
