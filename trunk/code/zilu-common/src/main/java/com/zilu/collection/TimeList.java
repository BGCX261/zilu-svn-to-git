package com.zilu.collection;


public interface TimeList<E>  {
	
	public void add(E e);
	
	public int getCount(E e);
	
	public void setTimerRemoveCallback(TimerRemoveCallback<E> callback);
	
	public void clear();
	
	public void startTimer();
	
	public void stopTimer();
	
	public void remove(int i);
	
}
