package com.zilu.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zilu.util.BeanUtils;

public class XmlMap extends HashMap<String, Object>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5642550817519170332L;
	
	Map<String, Object> attributes;
	
	public static XmlMap parse(String xml) throws XmlException {
		XmlParser parser = new XmlDom4jParser();
		return parser.parse(xml);
	}
	
	public XmlMap addChild(String name) {
		XmlMap child = new XmlMap();
		put(name, child);
		return child;
	}
	
	public XmlMap getXmlMap(String name) throws XmlException {
		Object obj = get(name);
		if (obj instanceof XmlMap) {
			return (XmlMap) obj;
		}
		else {
			throw new XmlException("instance:" + name +"is not XmlObject--" + obj);
		}
	}
	
	public List<XmlMap> getXmlList(String name) throws XmlException {
		Object obj = get(name);
		List<XmlMap> list = new ArrayList<XmlMap>();
		if (obj instanceof XmlMap) {
			list.add((XmlMap) obj);
		}
		else if (obj instanceof Collection) {
			list.addAll((Collection) obj);
		}
		return list;
	}
	
	@Override
	public Object put(String name, Object obj) {
		if (containsKey(name)) {
			Object o = get(name);
			if (o instanceof Collection) {
				((Collection)o).add(obj);
			}
			else {
				List<Object> olist = new ArrayList<Object>();
				olist.add(remove(name));
				olist.add(obj);
				put(name, olist);
			}
		}
		else {
			super.put(name, obj);
		}
		return obj;
	}
	
	
	public void populate(Object obj) {
		BeanUtils.populateDeeply(obj, this);
	}

	public String toXml() {
		StringBuilder sb = new StringBuilder();
		for (String key : keySet()) {
			Object obj = get(key);
			if (obj instanceof Collection) {
				for (Object subObj : (Collection)obj) {
					if (subObj instanceof XmlMap) {
						sb.append(createXml(key, ((XmlMap)subObj).toXml()));
					}
					else {
						sb.append(createXml(key, subObj.toString()));
					}
				}
			}
			else if (obj instanceof XmlMap) {
				sb.append(createXml(key, ((XmlMap)obj).toXml()));
			}
			else {
				sb.append(createXml(key, obj == null?"":obj.toString()));
			}
		}
		return sb.toString();
	}
	
	private String createXml(String key, String content) {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(key).append(">");
		sb.append(content);
		sb.append("</").append(key).append(">");
		return sb.toString();
	}
}
