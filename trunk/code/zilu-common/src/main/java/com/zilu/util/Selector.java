/**
 * 
 */
package com.zilu.util;

import java.io.Serializable;

/**
 * @author dell
 *
 */
public interface Selector<T> extends Serializable{
	boolean isAvailable(T obj);
}
