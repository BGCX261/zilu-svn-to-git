package com.zilu.dao;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class SqlDaoTemplate extends AbstractDaoTemplate {

	public SqlDaoTemplate(HibernateTemplate template) {
		super(template);
	}

	public Query getQuery(String sql) {
		return this.session.createSQLQuery(sql);
	}
}
