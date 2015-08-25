package com.zilu.dao;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class HqlDaoTemplate extends AbstractDaoTemplate {

	public HqlDaoTemplate(HibernateTemplate template) {
		super(template);
	}

	@Override
	protected Query getQuery(String hql) {
		return this.session.createQuery(hql);
	}

}
