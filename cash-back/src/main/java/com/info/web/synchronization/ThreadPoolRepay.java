package com.info.web.synchronization;

import java.util.LinkedList;
/**
 * 
 * @author gaoyuhai
 * 还款--
 *
 */
public class ThreadPoolRepay extends ThreadGroup {
	private boolean isClosed = false;  
	private LinkedList<Runnable> workQueue;      
	private static int threadPoolID = 1;  
	private static ThreadPoolRepay pool;
	
	private ThreadPoolRepay(int poolSize) {  
		super(threadPoolID + "");      
		setDaemon(true);               
		workQueue = new LinkedList<Runnable>();  
		for(int i = 0; i < poolSize; i++) {
			new WorkThread(i).start();  
		}
	}
	
	public static ThreadPoolRepay getInstance()
	{
		if(null == pool)
			pool = new ThreadPoolRepay(2);//初始化线程池
		return pool;
	}
	
	/** 向工作队列中加入一个新任务,由工作线程去执行该任务*/
	public synchronized void execute(Runnable task) {
		if(isClosed) {
			throw new IllegalStateException();
		}
		if(task != null) {
			workQueue.add(task);
			notify(); 			
		}
	}
	
	/** 从工作队列中取出一个任务,工作线程会调用此方法*/
	private synchronized Runnable getTask(int threadid) throws InterruptedException {
		while(workQueue.size() == 0) {
			if(isClosed) return null;
			System.out.println("工作线程"+threadid+"等待任务...");
			wait();				
		}
		System.out.println("工作线程"+threadid+"开始执行任务...");
		return (Runnable) workQueue.removeFirst(); 
	}
	
	/** 关闭线程池 */
	public synchronized void closePool() {
		if(! isClosed) {
			waitFinish();        
			isClosed = true;
			workQueue.clear();  
			interrupt(); 		
		}
	}
	
	/** 等待工作线程把所有任务执行完毕*/
	public void waitFinish() {
		synchronized (this) {
			isClosed = true;
			notifyAll();			
		}
		Thread[] threads = new Thread[activeCount()]; 
		int count = enumerate(threads); 
		for(int i =0; i < count; i++) { 
			try {
				threads[i].join();	
			}catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 内部类,工作线程,负责从工作队列中取出任务,并执行
	 * @author gaoyuhai
	 */
	private class WorkThread extends Thread {
		private int id;
		public WorkThread(int id) {
			
			super(ThreadPoolRepay.this,"线程"+id);
			this.id =id;
		}
		public void run() {
			while(! isInterrupted()) { 
				Runnable task = null;
				try {
					task = getTask(id);		
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
				
				if(task == null) return;
				
				try {
					task.run();  
				}catch(Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
}

