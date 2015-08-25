/**
 * 
 */
package com.zilu.util.file;


import java.io.File;

import com.zilu.util.Selector;




/**
 * @author dell
 *
 */
public abstract class FileSelector implements Selector<File> {
	protected int order;
	public  FileSelector() {
		order = FileConstants.TRAVEL_WIDTH;
	}
	
	public  FileSelector(int order) {
		this.order = order;
	}
	public int getOrder() {
		return order;
	}
}