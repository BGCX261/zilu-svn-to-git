package com.zilu.util.mission;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MissionController {
	
	Timer timer;
	
	HashMap<String, DaemonTask> daemonAbles;
	
	static MissionController instance = new MissionController();
	
	public static MissionController instance() {
		return instance;
	}
	
	public MissionController() {
		timer = new Timer();
		daemonAbles = new HashMap<String, DaemonTask>();
	}
	
	/**
	 * 开启任务
	 * @param daemonAble
	 */
	public synchronized void start(Mission daemonAble) {
		try {
			DaemonTask task = new DaemonTask();
			task.setDaemonAble(daemonAble);
			timer.schedule(task, 0, daemonAble.getInterval());
			daemonAbles.put(daemonAble.toString(), task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void stop(Mission daemonAble) {
		try {
			DaemonTask task = daemonAbles.remove(daemonAble.toString());
			if (task == null) {
				return;
			}
			if (!task.cancel()) {
				System.out.println("task error cancel");
			}
			task.setDaemonAble(null);
			task = null;
			timer.purge();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	static class DaemonTask extends TimerTask {

		Mission daemonAble;
		
		public Mission getDaemonAble() {
			return daemonAble;
		}

		public void setDaemonAble(Mission daemonAble) {
			this.daemonAble = daemonAble;
		}

		@Override
		public void run() {
			synchronized (MissionController.instance()) {
				if (daemonAble == null) {
					this.cancel();
					return;
				}
				daemonAble.execute();
			}
		}
		
	}
	
	public static void main(String args) {
		
	}
}
