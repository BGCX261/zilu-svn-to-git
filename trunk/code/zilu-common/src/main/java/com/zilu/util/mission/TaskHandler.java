package com.zilu.util.mission;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TaskHandler {

	private static TaskHandler instance = new TaskHandler();
	
	Timer timer = new Timer();
	
	public static TaskHandler instance() {
		return instance;
	}
	
	public void schedule(TimerTask task, int delay) {
		timer.schedule(task, delay);
	}
	
	public void schedule(TimerTask task, Date date) {
		timer.schedule(task, date);
	}
	
	public void schedule(TimerTask task, int delay, int period) {
		timer.schedule(task, delay, period);
	}
	
	public void schedule(TimerTask task, Date first, int period) {
		timer.schedule(task, first, period);
	}
	
}
