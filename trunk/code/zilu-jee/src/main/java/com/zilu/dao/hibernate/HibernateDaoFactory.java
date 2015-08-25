package com.zilu.dao.hibernate;

import com.zilu.dao.DaoFactory;
import com.zilu.dao.DaoTemplate;
import com.zilu.spring.SpringUtil;


public class HibernateDaoFactory extends DaoFactory {
	
	
	private HibernateDaoHelper daoHelper = new HibernateDaoHelper(true);
	
	private HibernateDaoHelper hqlDaoHelper = new HibernateDaoHelper();
	
	@Override
	public HibernateDaoHelper daoHelper() {
		return daoHelper;
	}
	
	public HibernateDaoHelper hqlDaoHelper() {
		return hqlDaoHelper;
	}

	@Override
	public DaoTemplate daoTemplate() {
		return (DaoTemplate) SpringUtil.getBean("hibernateSqlDaoTemplate");
	}
	
	public DaoTemplate hqlDaoTemplate() {
		return (DaoTemplate) SpringUtil.getBean("hibernateDaoTemplate");
	}
	
}
