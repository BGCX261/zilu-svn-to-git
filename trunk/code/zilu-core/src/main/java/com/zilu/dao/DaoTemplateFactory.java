package com.zilu.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

public final class DaoTemplateFactory {

	private static Object lock = new Object();

	private static DaoTemplateFactory instance;

	public static DaoTemplateFactory getInstance() {
		if (instance == null) {
			synchronized (lock) {
				instance = new DaoTemplateFactory();
			}
		}
		return instance;
	}

	public DaoTemplate getDaoTemplate(HibernateTemplate template, boolean isSql) {
		if (isSql) {
			return new SqlDaoTemplate(template);
		}
		return new HqlDaoTemplate(template);
	}

}
