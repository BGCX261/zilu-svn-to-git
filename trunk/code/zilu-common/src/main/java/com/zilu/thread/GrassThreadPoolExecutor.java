package com.zilu.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GrassThreadPoolExecutor extends ThreadPoolExecutor {
	
	
	@Override
	protected void afterExecute(Runnable runnable, Throwable throwable) {
		super.afterExecute(runnable, throwable);
	}

	public GrassThreadPoolExecutor(int size, long keepAliveTime) {
		super(size, Integer.MAX_VALUE, keepAliveTime, TimeUnit.SECONDS,  new LinkedBlockingQueue<Runnable>());
	}
	
	
}
