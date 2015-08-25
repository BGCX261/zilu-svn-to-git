package com.zilu.dao.hibernate;

import java.util.List;
import java.util.Map;

import com.zilu.dao.DaoFactory;
import com.zilu.dao.DaoHelper;
import com.zilu.dao.DaoTemplate;
import com.zilu.dao.sql.SqlFacade;
import com.zilu.util.Strings;
import com.zilu.vo.PageBean;

public class HibernateDaoHelper implements DaoHelper {
	
	boolean nativeQuery = false;
	
	HibernateDaoHelper() {
		
	}
	
	HibernateDaoHelper(boolean nativeQuery) {
		this.nativeQuery = nativeQuery;
	}

	public void executeUpdate(String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		getTemplate().executeUpdate(sql, params);
	}

	public void executeUpdate(String qname, Map<String, Object> paramMap) {
		String sql = SqlFacade.getSql(qname);
		getTemplate().executeUpdate(sql, paramMap);
	}

	public Object queryFirst(String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate().queryFirst(sql, params);
	}

	public List queryList(String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate().queryList(sql, params);
	}

	public List queryList(String qname, Map<String, Object> paramMap) {
		return queryList(qname, paramMap, -1, -1);
	}

	public List queryList(String qname, Map<String, Object> paramMap,
			int firstRow, int rowSize) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate().queryList(sql, paramMap, firstRow, rowSize);
	}

	public Object queryFirst(String qname, Map<String, Object> parameterMap) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate().queryFirst(sql, parameterMap);
	}
	
	public Object querySingle(String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate().querySingle(sql, params);
	}
	
	public Object querySingle(String qname, Map<String, Object> paramMap) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate().querySingle(sql, paramMap);
	}

	public PageBean queryPage(String qname, Map<String, Object> conditions, int pageNo, int pageSize, String orderBy) {
		String qhql = SqlFacade.getSql(qname);
		int index = qhql.indexOf("order by");
		String chql = qhql;
		if (index != -1) {
			chql = qhql.substring(0, index);
		}
		if (chql.toLowerCase().indexOf("select")==0 && chql.toLowerCase().indexOf("from")>0){
			chql = chql.substring(chql.indexOf("from"));
		}
		chql = "select count(*) " + chql;
		if (!Strings.isEmpty(orderBy)) {
			qhql = qhql + " "+ orderBy;
		}
		return getTemplate().queryPage(chql, qhql, conditions, pageNo, pageSize);
	}
	
	public PageBean queryPage(String qname, String cname, Map<String, Object> conditions, int pageNo, int pageSize, String orderBy) {
		String qhql = SqlFacade.getSql(qname);
		String chql = SqlFacade.getSql(cname);
		if (!Strings.isEmpty(orderBy)) {
			qhql = qhql + orderBy;
		}
		return getTemplate().queryPage(chql, qhql, conditions, pageNo, pageSize);
	}
	
	public boolean isNativeQuery() {
		return nativeQuery;
	}

	public void setNativeQuery(boolean nativeQuery) {
		this.nativeQuery = nativeQuery;
	}
	
	public DaoTemplate getTemplate() {
		if (nativeQuery) {
			return DaoFactory.hibernateFacotry().daoTemplate();
		}
		else {
			return DaoFactory.hibernateFacotry().hqlDaoTemplate();
		}
	}


}
