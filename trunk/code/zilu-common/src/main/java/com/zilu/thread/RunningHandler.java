package com.zilu.thread;
/**
 * 注意本线程需要自行加入同步控制，否则当多线程出错时，有可能出现并发调用
 * @author 陈华敏
 * @Time 
 * @Description
 */
public interface RunningHandler {
	
	/**
	 * 
	 * @param p 线程池
	 * @param r 任务
	 * @param t 异常
	 * @return 返回true，该线程将被终止，回收，false该线程并不会被回收。
	 */
	public boolean throwableCatched( GrassThreadPoolV2 p, Runnable r, Throwable t); 
}
