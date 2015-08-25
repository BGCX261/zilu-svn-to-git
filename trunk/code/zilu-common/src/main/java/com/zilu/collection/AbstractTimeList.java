package com.zilu.collection;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractTimeList<E> implements TimeList<E> {
	
	
	int interval;
	
	int aliveTime;
	
	Timer timer; 
	
	RemoveTask task;
	
	public AbstractTimeList(int alive) {
		aliveTime = alive*1000;
		interval = alive / 10;
		interval = interval > 10000? 10000 : interval;
		startTimer();
	}
	
	public AbstractTimeList(int alive, int inter) {
		if (alive < interval) {
			throw new RuntimeException("alive not allow smaller than interval");
		}
		aliveTime = alive * 1000;
		interval = inter * 1000;
	}
	
	protected abstract void timerCheck();
	
	
	public void startTimer() {
		if (timer == null) {
			timer = new Timer();
			task = new RemoveTask(this);
			timer.schedule(task, 10, interval);
		}
	}

	public void stopTimer() {
		task.cancel();
		task = null;
		timer.cancel();
		timer = null;
	}
	
	
    class RemoveTask extends TimerTask {
    	
    	TimeList<E> list;
    	
    	RemoveTask(TimeList<E> list) {
    		this.list = list;
    	}
    	
		@Override
		public void run() {
			synchronized (list) {
				timerCheck();
			}
		}
		
	}
}
