package com.zilu.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


import org.apache.log4j.Logger;

//import com.fsti.util.debug.Debug;
//import com.fsti.util.debug.Debugs;



/**
 * @author 陈华敏
 * @Time 2009年06月
 * @Description 
 */
public class GrassThreadPool implements Executor{
	
	private static Logger logger = Logger.getLogger(GrassThreadPool.class);
//	private static Debug debug = Debugs.getFileDebug("e:/pool.txt");

	private static final int DEAFULT_CORE_SIZE = 10;
	private static final int DEFAULT_MAX_SIZE = 200;
	private static final int DEFAULT_IDLE_TIMEOUT = 5 * 60 * 1000;
	private static final int DEFAULT_INTERVAL = 200;
	private static final int DEFAULT_SPARE_SIZE = 5;
	
	private static final int STATE_STOP = 0;
	private static final int STATE_START = 1;

//	核心线程数，最小线程数
	private int coreSize = DEAFULT_CORE_SIZE;
	
	private int currentSize;
	
	private int spareSize = DEFAULT_SPARE_SIZE;

//	最大线程数
	private int maxSize = DEFAULT_MAX_SIZE;
	
//	等待任务队列
	private BlockingQueue<Runnable> queue; 
	
	
	volatile int runSize = 0;

	private int idleTimeout = DEFAULT_IDLE_TIMEOUT;
	
	private int interval = DEFAULT_INTERVAL;
	
	volatile int runState;
	
	private final ReentrantLock lock = new ReentrantLock();
	
	protected WorkThread[] pool = null;
	
	
	QueueExecuteThread monitor;
	FreeCleanThread freer;
	KillCleanThread killer;
	
	Condition hasFreeThread;
	
	Condition hasTask;
	
	volatile int successTime;
	
	volatile int failTime;
	
	public synchronized void success() {
		successTime++;
	}
	
	public synchronized void fail() {
		failTime++;
	}
	
	public static GrassThreadPool create() {
		GrassThreadPool grassPool = new GrassThreadPool();
		grassPool.queue = new LinkedBlockingQueue<Runnable>();
		return grassPool;
	}
	
	public static GrassThreadPool create(int coreNum, int maxNum) {
		GrassThreadPool grassPool = new GrassThreadPool();
		grassPool.queue = new LinkedBlockingQueue<Runnable>();
		grassPool.coreSize = coreNum;
		grassPool.maxSize = maxNum;
		return grassPool;
	}
	
	private GrassThreadPool() {
		init();
	}
	
	
	private void init() {
		hasFreeThread = lock.newCondition();
		hasTask = lock.newCondition();
		pool = new WorkThread[maxSize];
		monitor = new QueueExecuteThread();
		freer = new FreeCleanThread();
		killer = new KillCleanThread();
	}
	
	
	public synchronized void start() {
		runState = STATE_START;
		openThread(coreSize);
		monitor.start();
		freer.start();
//		killer.start();
	}
	
	public void stop() {
		stop(0);
	}
	
	public void stop(int maxTime) {
		if (maxTime != 0) {
			awaitFinish(maxTime);
		}
		synchronized (this) {
			runState = STATE_STOP;
		}
		try {
			lock.lock();
			hasFreeThread.signalAll();
			hasTask.signalAll();
			releaseFreeThread(currentSize - runSize);
		} finally {
			lock.unlock();
		}
		logger.info("线程池即将关闭");
	}
	
	public void execute(Runnable runnable) {
		if (isStop()) {
			return;
		}
		WorkThread thread = findFreeWorkThread();
		if (thread != null) {
			thread.runTask(runnable);
		}
		else {
			addTask(runnable);
		}
	}
	
	private void addTask(Runnable runnable) {
		lock.lock();
		try {
			logger.debug("任务" + runnable + "加入等待队列");
//			debug.log("任务" + runnable + "加入等待队列");
			queue.add(runnable);
			hasTask.signal();
		} finally {
			lock.unlock();
		}
	}
	
	
	/**
	 * 等待线程池中的所有线程运行完毕，
	 * @param maxTime 超时设置
	 * @return true表示成功返回，false表示超时返回
	 */
	public boolean awaitFinish(long maxTime) {
		long st = System.currentTimeMillis();
		while (true) {
			long ct = System.currentTimeMillis();
			if (ct - st > maxTime) {
				return false;
			}
			else if (runSize == 0) {
				return true;
			}
			else {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public WorkThread findFreeWorkThread() {
		try {
			lock.lock();
			WorkThread workThread = null;
//			线程用完
			while(runSize == currentSize) {
//				假如线程未超出最大线程
				if (currentSize < maxSize) {
					int toSize = currentSize + spareSize;
					openThread(toSize);
				}
				else {
					return null;
				}
			}
			int pos = currentSize - runSize - 1;
			workThread = pool[pos];
			// 释放当前线程池对该线程的引用,使用线程的数量也要加1
			pool[pos] = null;
			runSize++;
			return workThread;
		} finally {
			lock.unlock();
		}
	}
	
	private void openThread(int toOpen) {
		if (toOpen > maxSize) {
			toOpen = maxSize; 
		}
		logger.debug("开启线程数量：" + toOpen);
//		debug.log("开启线程数量：" + toOpen);
		for (int i = currentSize; i < toOpen; i++) {
			pool[i - runSize] = new WorkThread(this, i);
			logger.debug("创建线程 " + pool[i - runSize].toString());
//			debug.log("创建线程 " + pool[i - runSize].toString());
		} 
		currentSize = toOpen;
	}
	
	public void releaseFreeThreads() {
		try {
			lock.lock();
			int leftSize = currentSize - runSize;
			int freeSize = 0;
			for (int i = 0; i < leftSize; i ++) {
				WorkThread workThread = pool[i];
				if (leftSize - freeSize <= 0) {
					break;
				}
				if (System.currentTimeMillis() - workThread.lastStartTime > idleTimeout) {
					logger.debug("释放线程：" + workThread + " index: " + workThread.index + " lastExecuteTime: " + workThread.lastStartTime);
//					debug.log("释放线程：" + workThread + " index: " + workThread.index + " lastExecuteTime: " + workThread.lastStartTime);
					workThread.terminate();
					pool[i] = null;
					freeSize ++ ;
				}
				else {
//					所有线程前移
					pool[i - freeSize] = pool[i];
					pool[i] = null;
				}
			}
		} finally {
			lock.unlock();
		}
	}
	
	private void releaseFreeThread(int size) {
		int freeSize = currentSize - runSize;
		if (freeSize < size) {
			size = freeSize;
		}
		for (int i = 0; i < size; i ++) {
			WorkThread workThread = pool[freeSize - 1 - i];
			workThread.terminate();
			pool[freeSize - 1 - i] = null;
			currentSize --;
		}
	}
	
	public void runOver(WorkThread workThread) {
		try {
			lock.lock();
			runSize --;
			pool[currentSize - runSize - 1] = workThread;
			logger.debug("线程" + workThread.index + "执行任务完毕");
//			debug.log("线程" + workThread.index + "执行任务完毕");
			hasFreeThread.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public synchronized boolean isStop() {
		return runState == STATE_STOP;
	}
	
	private class QueueExecuteThread extends Thread {
		
		public void run() {
			while (true) {
				try {
					if (isStop()) {
						break;
					}
					try {
						lock.lock();
						if (queue.size() == 0) {
							hasTask.await();
						}
						Runnable runable = queue.take();
						hasFreeThread.await();
						execute(runable);
					} finally {
						lock.unlock();
					}
				} catch (Throwable t) {
					t.printStackTrace();
					logger.error("error when start task in queue");
				}
			}
			logger.debug("轮询队列线程结束");
		}
	}
	
	private class FreeCleanThread extends Thread {
		
		public void run() {
			while (true) {
				try {
					if (isStop()) {
						break;
					}
					synchronized (this) {
						this.wait(1000*60);
					}
					releaseFreeThreads();
				} catch (Throwable t) {
					t.printStackTrace();
					logger.error("FreeCleanThread  error");
				}
			}
			logger.debug("空闲清理线程结束");
		}
	}
	
	private class KillCleanThread extends Thread {
		
		public void run() {
			while (true) {
				try {
					if (isStop()) {
						break;
					}
					sleep(interval);
				} catch (Throwable t) {
					t.printStackTrace();
					logger.error("FreeCleanThread  error");
				}
			}
			logger.debug("超时清理线程结束");
		}
	}
	
	
	public static class WorkThread implements Runnable {

		Thread t;
		
		GrassThreadPool p;
		
		volatile long lastStartTime;                    
		
		volatile boolean active;
		
		private boolean shouldTerminate;// 停止标志
		private boolean shouldRun;
		
		int index;
		
		Runnable toRun;
		
		public WorkThread(GrassThreadPool p, int index) {
			this.t = new Thread(this);
			this.index = index;
			this.p = p;
			this.t.start();
		}
		
		

		synchronized boolean isActive() {
			return shouldRun;
		}

		public void run() {
			boolean _shouldRun = false;
			boolean _shouldTerminate = false;
			Runnable _toRun = null;
			try {
				while (true) {
					try {
						if (p.isStop()) {
							this.terminate();
						}
						synchronized(this) {
							while(!shouldRun&& !shouldTerminate) {
								this.wait();
							}
//							转换成本地变量，保证不受同步影响
							_shouldRun = this.shouldRun;
							_shouldTerminate = this.shouldTerminate;
							_toRun = this.toRun;
						}
						if (_shouldRun) {
							try {
								lastStartTime = System.currentTimeMillis();
								if(_toRun != null) {
									logger.debug("线程" + index + "开始执行任务: " + _toRun);
//									debug.log("线程" + index + "开始执行任务: " + _toRun);
									_toRun.run();
									p.success();
								}
								else {
									logger.info("no runnable available");
								}
							} catch (Throwable e) {
								p.fail();
								e.printStackTrace();
							} finally {
								synchronized (this) {
									this.shouldRun = false;
									this.toRun = null;
								}
								p.runOver(this);
							}
						}
						if (_shouldTerminate) { 
							logger.debug("线程" + index + "停止");
//							debug.log("线程" + index + "停止");
							break;
						}
					} catch (Throwable t) {
						t.printStackTrace();
						logger.error("uncatched exception or error");
					} 
				}  
			}  finally {
				
			}
		}
		
		public synchronized void runTask(Runnable runnable) {
			shouldRun = true;
			toRun = runnable;
			this.notify();
		}
		
		public synchronized void terminate() {
			shouldTerminate = true;
			this.notify();
		}

	}

}
