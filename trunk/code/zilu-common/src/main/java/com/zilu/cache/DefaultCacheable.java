package com.zilu.cache;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


import com.zilu.cache.CacheEngine;
import com.zilu.cache.Cacheable;

/**
 * 默认缓存实现
 * @author 陈华敏
 * @Time 
 * @Description
 */
public abstract class DefaultCacheable implements Cacheable {
	
	protected CacheEngine cache;
	
	private volatile boolean reload = false;
	private volatile int queryNum = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition loadCon = lock.newCondition();
	private Condition queryCon = lock.newCondition();
	
	public void setCacheEngine(CacheEngine engine) {
		this.cache = engine;
	}

	public CacheEngine getCache() {
		return cache;
	}
	
	public abstract void doReload();
	
	/**
	 * 开始重新加载（上锁）
	 */
	protected void startReload() {
		try {
			lock.lock();
			while (queryNum > 0) {
				try {
					queryCon.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			reload = true;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 结束重新加载（放锁）
	 */
	protected void endReload() {
		try {
			lock.lock();
			reload = false;
			loadCon.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 开始查询（上锁）
	 */
	protected void startQuery() {
		try {
			lock.lock();
			if (reload) {
				try {
					loadCon.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			queryNum ++;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 结束查询（放锁）
	 */
	protected void endQuery() {
		try {
			lock.lock();
			queryNum --;
			queryCon.signal();
		} finally {
			lock.unlock();
		}
	}
	
}
