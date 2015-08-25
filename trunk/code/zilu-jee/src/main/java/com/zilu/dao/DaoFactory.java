package com.zilu.dao;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.zilu.dao.hibernate.HibernateDaoFactory;
import com.zilu.spring.SpringUtil;


public abstract class DaoFactory {
	
	private static HibernateDaoFactory hibernateFactory = new HibernateDaoFactory();
	
	public static HibernateDaoFactory hibernateFacotry() {
		return hibernateFactory;
	}
	
	public static HibernateTemplate springHibernateTemplate() {
		return (HibernateTemplate) SpringUtil.getBean("hibernateTemplate");
	}
	
	public abstract DaoTemplate daoTemplate();
	
	public abstract DaoHelper daoHelper();
	
}
