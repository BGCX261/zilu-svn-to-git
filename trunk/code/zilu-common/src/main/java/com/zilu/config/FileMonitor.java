
package com.zilu.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
*
* @author chenhm
* @Company 福建邮科电信业务部
* @version 创建时间：Feb 24, 2009 11:34:14 AM
* @Describe 文件控制器（主要响应文件的变化事件，重载文件）
*
*/
public class FileMonitor
{
	private static final FileMonitor instance = new FileMonitor();
	private Timer timer;
	private List<FileMonitorTask> timerEntries;
	
	private FileMonitor() {
		this.timerEntries = new ArrayList<FileMonitorTask>();
		this.timer = new Timer(true);
	}
	
	public static FileMonitor getInstance() {
		return instance;
	}
	
	public void start() {
//		统一控制时间
		for (FileMonitorTask task : timerEntries) {
			int intervalTime = 1000;
			timer.schedule(task, intervalTime, intervalTime);
		}
		
	}
	
	/**
	 * Add a file to the monitor
	 * 
	 * @param listener The file listener
	 * @param filename The filename to watch
	 * @param period The watch interval.
	 */
	public void addListener(File file, FileChangeListener listener) {
		FileMonitorTask task = new FileMonitorTask(listener, file);
		timerEntries.add(task);
	}
	
	private static class FileMonitorTask extends TimerTask {
		private FileChangeListener listener;
		private File monitoredFile;
		private long lastModified;
		
		public FileMonitorTask(FileChangeListener listener, File monitoredFile) {
			this.listener = listener;
			
			this.monitoredFile = monitoredFile;
			if (!this.monitoredFile.exists()) {
				return;
			}
			
			this.lastModified = this.monitoredFile.lastModified();
		}
		
		public void run() {
			long latestChange = this.monitoredFile.lastModified();
			if (this.lastModified != latestChange) {
				this.lastModified = latestChange;
				this.listener.change(monitoredFile);
			}
		}
	}
}
