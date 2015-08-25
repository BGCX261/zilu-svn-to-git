package com.zilu.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author chenhm
 * @version 创建时间：Feb 24, 2009 12:13:54 PM
 * @Describe DAO助手接口定义
 * 
 */
public interface DaoTemplate {
	/**
	 * 查询获取列表
	 * 
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数列表
	 * @return
	 */
	public List queryList(String sql, Object... params);

	/**
	 * 查询获取单个对象（假设不存在抛出异常）
	 * 
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数列表
	 * @return
	 */
	public Object querySingle(String sql, Object... params);

	/**
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数列表
	 * @return
	 */
	public Object queryFirst(String sql, Object... params);

	/**
	 * 执行更新
	 * 
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数列表
	 * @return
	 */
	public int executeUpdate(String sql, Object... params);

	/**
	 * 执行更新
	 * 
	 * @param sql
	 * @param params
	 *            参数
	 * @return
	 */
	public int executeUpdate(String sql, Map params);

	/**
	 * 执行更新
	 * 
	 * @param clazz
	 *            类名
	 * @param p1
	 *            属性-》名称对
	 * @param p2
	 *            名称—》值对
	 * @return
	 */
	public int executeUpdate(Class clazz, Map<String, Object> p1, Map<String, Object> p2);

	/**
	 * 查询获取对象列表
	 * 
	 * @param queryQL
	 *            查询语句
	 * @param parameterMap
	 *            参数-》值对
	 * @return
	 */
	public List queryList(String queryQL, Map parameterMap);

	/**
	 * 查询获取对象列表
	 * 
	 * @param queryQL
	 *            查询语句
	 * @param parameterMap
	 *            参数-》值对
	 * @param firstRow
	 *            第一条记录
	 * @param pageRowSize
	 *            共取几条记录
	 * @return
	 */
	public List queryList(String queryQL, Map parameterMap, int firstRow, int pageRowSize);

	/**
	 * @param sql
	 *            查询语句
	 * @param params
	 *            查询参数列表
	 * @return
	 */
	public Object queryFirst(String sql, Map parameterMap);

	/**
	 * 查询获得唯一的结果（不存在抛出异常）
	 * 
	 * @param queryQL
	 *            查询语句
	 * @param parameterMap
	 *            参数-》值对
	 * @return
	 */
	public Object querySingle(String queryQL, Map parameterMap);


	public PageBean queryPage(String countHql, String queryHql, Map<String, Object> conditions, int pageNo, int PageSize);

}
