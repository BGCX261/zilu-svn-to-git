package com.zilu.dao;

import java.util.List;
import java.util.Map;

import com.zilu.vo.PageBean;


public interface DaoHelper {
	
	public List queryList(String qname, Object... params);
	
	public Object querySingle(String qname, Object... params);
	
	public Object queryFirst(String qname, Object... params);
	
	public List queryList(String qname, Map<String, Object> paramMap);
	
	public List queryList(String qname, Map<String, Object> paramMap, int firstRow, int rowSize);
	
	public Object querySingle(String qname, Map<String, Object> parameterMap);
	
	public Object queryFirst(String qname, Map<String, Object> parameterMap);
	
	public PageBean queryPage(String qname, Map<String, Object> conditions, int pageNo, int pageSize, String orderBy);

	public PageBean queryPage(String qname, String cname, Map<String, Object> conditions, int pageNo, int pageSize, String orderBy);
	
	public void executeUpdate(String qname, Object... params);
	
	public void executeUpdate(String qname, Map<String, Object> paraMap);
}
