/**
 * 
 */
package com.zilu.util;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * @author dell
 *
 */
public class FieldComparator implements Comparator<Object>, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 744097078302579013L;
	
	String fieldName;
	
	public FieldComparator(String fieldName) {
		this.fieldName = fieldName;
	}
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		Comparable v1 = (Comparable) getFieldValue(arg0);
		Comparable v2 = (Comparable) getFieldValue(arg1);
		int result = v1.compareTo(v2);
		if (result == 0) {
			return -1;
		}
		else {
			return result;
		}
	}
	
	public Object getFieldValue(Object obj) {
		return ReflectUtil.getPropertyValue(obj, fieldName);
	}

}
