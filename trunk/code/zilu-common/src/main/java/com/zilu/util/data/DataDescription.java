/**
 * 
 */
package com.zilu.util.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zilu.util.BeanUtils;

/**
 * Company: fsti
 * @author chenhm
 * Create: 2010-7-14
 * Modify：
 * Description:
 */
public class DataDescription {

	private List<String> headers;
	
	private List<String> columnProperties;
	
	private Map<String, ValueConverter> converters;
	
	public DataDescription() {}

	public DataDescription(String[] headers, String[] columns) {
		 this.headers = Arrays.asList(headers);
		 this.columnProperties = Arrays.asList(columns);
	}
	
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getColumnProperties() {
		return columnProperties;
	}

	public void setColumnProperties(List<String> columnProperties) {
		this.columnProperties = columnProperties;
	}
	
	public void addHeader(String header) {
		if (headers == null) {
			headers = new ArrayList<String>();
		}
		headers.add(header);
	}
	
	public void addColumnProperty(String columnProperty) {
		if (columnProperties == null) {
			columnProperties = new ArrayList<String>();
		}
		columnProperties.add(columnProperty);
	}
	
	public void addConverter(String propName, ValueConverter converter) {
		if (converters == null) {
			converters = new HashMap<String, ValueConverter>();
		}
		converters.put(propName, converter);
	}
	
	public List<String> getHeaderValues() {
		return headers;
	}
	
	public List<Object> getColumnValues(Object obj) {
		List<Object> values = new ArrayList<Object>();
		Map<String, Object> properties =obj instanceof Map? (Map<String, Object>)obj : BeanUtils.describe(obj, false);
		
		List<String> selects;
		if (columnProperties != null){
			selects = columnProperties;
		}
		else {
			selects = new ArrayList<String>(properties.keySet());
		}
		for (String propName : selects) {
			Object value = properties.get(propName);
			if (converters != null) {
				ValueConverter converter = converters.get(propName);
				if (converter != null) {
					value = converter.convert(value, obj);
				}
			}
			values.add(value);
		}
		return values;
	}
}
