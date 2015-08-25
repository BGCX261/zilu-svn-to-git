package com.zilu.dao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zilu.sql.SqlFilterFacade;

public abstract class AbstractDaoTemplate implements DaoTemplate {

	protected Session session = null;

	public AbstractDaoTemplate(HibernateTemplate template) {
		this.session = template.getSessionFactory().getCurrentSession();
	}

	protected abstract Query getQuery(String sql);

	private void launchParamValues(Query query, Map<String, Object> conditions) {
		String queryStr = query.getQueryString();
		if (conditions == null) {
			return;
		}
		for (Iterator<String> it = conditions.keySet().iterator(); it.hasNext();) {
			String name = (String) it.next();
			Object value = conditions.get(name);
			if (queryStr.indexOf(":" + name) == -1) {
				continue;
			}
			if (value instanceof String)
				query.setString(name, (String) value);
			else if (value instanceof Integer)
				query.setInteger(name, ((Integer) value).intValue());
			else if (value instanceof Long)
				query.setLong(name, ((Long) value).longValue());
			else if (value instanceof Float)
				query.setFloat(name, ((Float) value).floatValue());
			else if (value instanceof Double)
				query.setDouble(name, ((Double) value).doubleValue());
			else if (value instanceof Timestamp)
				query.setTimestamp(name, (Timestamp) value);
			else if (value instanceof Date)
				query.setDate(name, (Date) value);
			else if (value instanceof Collection)
				query.setParameterList(name, (Collection) value);
			else if (value instanceof Object[])
				query.setParameterList(name, (Object[]) value);
		}

	}

	@Override
	public int executeUpdate(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(String sql, Map params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		launchParamValues(query, params);
		return query.executeUpdate();
	}

	@Override
	public int executeUpdate(Class clazz, Map<String, Object> p1, Map<String, Object> p2) {
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

	@Override
	public Object queryFirst(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		List l = queryList(sql, params);
		if (l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Object queryFirst(String sql, Map parameterMap) {
		List l = queryList(sql, parameterMap, -1, -1);
		if (l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List queryList(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

	@Override
	public List queryList(String sql, Map parameterMap) {
		return queryList(sql, parameterMap, -1, -1);
	}

	@Override
	public List queryList(String sql, Map parameterMap, int firstRow, int pageRowSize) {
		sql = SqlFilterFacade.filterSql(sql, parameterMap);
		Query query = getQuery(sql);
		launchParamValues(query, parameterMap);
		if (firstRow != -1) {
			query.setFirstResult(firstRow);
		}
		if (pageRowSize != -1) {
			query.setMaxResults(pageRowSize);
		}
		return query.list();
	}

	@Override
	public PageBean queryPage(String countHql, String queryHql, Map<String, Object> conditions, int pageNo, int PageSize) {
		PageBean pageBean = new PageBean();
		countHql = SqlFilterFacade.filterSql(countHql, conditions);
		Query query = getQuery(countHql);
		launchParamValues(query, conditions);
		Object tmpCount = (Object) query.uniqueResult();
		int count = 0;
		if (tmpCount instanceof Long) {
			count = ((Long) tmpCount).intValue();
		} else {
			count = ((BigDecimal) tmpCount).intValue();
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

	@Override
	public Object querySingle(String sql, Object... params) {
		sql = SqlFilterFacade.filterSql(sql, params);
		Query query = getQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.uniqueResult();
	}

	@Override
	public Object querySingle(String queryQL, Map parameterMap) {
		queryQL = SqlFilterFacade.filterSql(queryQL, parameterMap);
		Query query = getQuery(queryQL);
		launchParamValues(query, parameterMap);
		return query.uniqueResult();
	}

}
