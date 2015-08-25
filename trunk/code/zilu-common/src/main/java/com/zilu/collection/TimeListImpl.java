package com.zilu.collection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TimeListImpl<E> extends AbstractTimeList<E> {

	LinkedList<E> elements;
	LinkedList<Long> times;
	Map<E, Integer> countMap;
	TimerRemoveCallback<E> callback;
	
	public TimeListImpl(int alive) {
		super(alive);
		elements = new LinkedList<E>();
		times = new LinkedList<Long>();
		countMap = new HashMap<E, Integer>();
	}
	
	public synchronized void add(E o) {
		elements.add(o);
		times.add(System.currentTimeMillis());
		Integer count = countMap.get(o);	
		if (count == null) {
			count = 0;
		}
		countMap.put(o, count+1);
	}


	public synchronized int getCount(E e) {
		Integer count = countMap.get(e);
		return count == null? 0 : count;
	}

	
	public void setTimerRemoveCallback(TimerRemoveCallback<E> callback) {
		this.callback = callback;
	}

	public synchronized void clear() {
		elements.clear();
		times.clear();
		countMap.clear();
	}

	public synchronized void remove(int i) {
		long time = times.remove(i);
		E e = elements.remove(i);
//		减少数量
		Integer count = countMap.get(e);
		if (count - 1 <= 0) {
			countMap.remove(e);
		}
		else {
			countMap.put(e, count - 1);
		}
		if (callback != null) {
			callback.remove(e, time);
		}
	}
	
	@Override
	protected synchronized void timerCheck() {
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < times.size(); i ++) {
			long time = times.get(i);
//			假如存活时间超出
			if (currentTime - time >= aliveTime) {
				remove(i);
			}
			else {
				break;
			}
		}
	}

	

	
}
