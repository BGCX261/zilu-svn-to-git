package com.zilu.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zilu.dao.DaoTemplate;
import com.zilu.dao.sql.SqlFilterFacade;
import com.zilu.spring.SpringUtil;
import com.zilu.vo.PageBean;




@SuppressWarnings("unchecked")
@Transactional(readOnly = true)
public class HibernateDaoTemplate implements DaoTemplate {
	
	boolean nativeQuery = false;
	
	HibernateDaoTemplate(boolean nativeQuery) {
		this.nativeQuery = nativeQuery;
	}
	
	HibernateDaoTemplate() {
		
	}
	
	public void setNativeQuery(boolean nativeQuery) {
		this.nativeQuery = nativeQuery;
	}

	public void refresh(Object obj) {
		getSession().refresh(obj);
	}
	

	public <T> T get(Class<T> classType, Serializable key) {
		return (T) getSession().get(classType, key);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Object obj) {
		getSession().update(obj);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Object obj) {
		getSession().save(obj);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdate(Object obj) {
		getSession().saveOrUpdate(obj);
	}
	
	public Query createNativeQuery(String sql) {
		return getSession().createSQLQuery(sql);
	}
	
	public Query createQuery(String sql) {
		return getSession().createQuery(sql);
	}
	
	public Query getQuery(String sql) {
		if (nativeQuery) {
			return createNativeQuery(sql);
		}
		else {
			return createQuery(sql);
		}
	}
	
	public List queryList(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}
	
	public Object querySingle(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.uniqueResult();
	}
	
	public Object queryFirst(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		List l = queryList(sql, params);
		if (l.size() > 0) {
			return l.get(0);
		}
		else {
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int executeUpdate(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public int executeUpdate(String sql, Map params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		launchParamValues(query, params);
		return query.executeUpdate();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public int executeUpdate (Class clazz, Map<String, Object> p1, Map<String, Object> p2) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(clazz.getName()).append(" set ");
		boolean flag = false;
		for (String name : p1.keySet()) {
			if (flag) {
				sql.append(" , ");
			}
			sql.append(name).append("=:").append(name);
			flag = true;
		}
		sql.append(" where 1=1");
		for (String name : p2.keySet()) {
			sql.append(" and ");
			sql.append(name).append("=:").append(name);
		}
		Query query = getQuery(sql.toString());
		p1.putAll(p2);
		for (Entry<String, Object> entry : p1.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query.executeUpdate();
	}
	/**
	 * 
	 * @param queryQL
	 * @param parameterMap
	 * @return
	 */
	public List queryList(String queryQL, Map parameterMap) {
		return queryList(queryQL, parameterMap, -1, -1);
	}

	public List queryList(String queryQL, Map parameterMap,
			int firstRow, int pageRowSize) {
		queryQL = SqlFilterFacade.filterSql(queryQL, parameterMap);
		Query query = getQuery(queryQL);
		launchParamValues(query, parameterMap);
		if (firstRow != -1 ) {
			query.setFirstResult(firstRow);
		}
		if (pageRowSize != -1) {
			query.setMaxResults(pageRowSize);
		}
		return query.list();
	}
	
	public Object queryFirst(String queryQL, Map parameterMap) {
		List l = queryList(queryQL, parameterMap, -1, -1);
		if (l.size() > 0) {
			return l.get(0);
		}
		else {
			return null;
		}
	}

	/**
	 * 
	 * @param queryQL
	 * @param parameterMap
	 * @return
	 */
	public Object querySingle(String queryQL, Map parameterMap) {
		queryQL = SqlFilterFacade.filterSql(queryQL, parameterMap);
		Query query = getQuery(queryQL);
		launchParamValues(query, parameterMap);
		return query.uniqueResult();
	}

	public PageBean queryPage(String countHql, String queryHql, Map<String, Object> conditions, int pageNo, int PageSize) {
		PageBean pageBean = new PageBean();
		countHql = SqlFilterFacade.filterSql(countHql, conditions);
		Query query = getQuery(countHql);
		launchParamValues(query, conditions);
		Object tmpCount = (Object) query.uniqueResult();
		int count = 0;
		if (tmpCount instanceof Long) {
			count = ((Long)tmpCount).intValue();
		}
		else {
			count = ((BigDecimal)tmpCount).intValue();
		}
		
		pageBean.setRowCount(count);
		pageBean.setPageSize(PageSize);
		pageBean.setPageNo(pageNo);
		queryHql = SqlFilterFacade.filterSql(queryHql, conditions);
		query = getQuery(queryHql);
		launchParamValues(query, conditions);
		query.setFirstResult(pageBean.getFirstResult());
		query.setMaxResults(pageBean.getPageSize());
		pageBean.setQueryList(query.list());
		return pageBean;
	}
	
	
	private void launchParamValues(Query query, Map<String, Object> conditions)
    {
		String queryStr = query.getQueryString();
		if (conditions == null) {
			return ;
		}
        for(Iterator<String> it = conditions.keySet().iterator(); it.hasNext();)
        {
            String name = (String)it.next();
            Object value = conditions.get(name);
            if (queryStr.indexOf(":" + name) == -1) {
            	continue;
            }
            if(value instanceof String)
                query.setString(name, (String)value);
            else if(value instanceof Integer)
                query.setInteger(name, ((Integer)value).intValue());
            else if(value instanceof Long) 
            	query.setLong(name, ((Long)value).longValue());
            else if(value instanceof Float)
                query.setFloat(name, ((Float)value).floatValue());
            else if(value instanceof Double)
                query.setDouble(name, ((Double)value).doubleValue());
            else if(value instanceof Timestamp)
                query.setTimestamp(name, (Timestamp)value);
            else if(value instanceof Date) 
            	query.setDate(name, (Date)value);
            else if(value instanceof Collection)
                query.setParameterList(name, (Collection)value);
            else if(value instanceof Object[])
                query.setParameterList(name, (Object[])value);
        }

    }
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Object entity) {
		entity = getSession().merge(entity);
		getSession().delete(entity);
	}

	public <T> List<T> getAll(Class<T> clazz) {
		return createQuery("from " + clazz.getName()).list();
	}
	
	public Session getSession() {
		SessionFactory factory = ((SessionFactory) SpringUtil.getBean("sessionFactory"));
		Session session = factory.getCurrentSession();
		return session;
	}
	

}
