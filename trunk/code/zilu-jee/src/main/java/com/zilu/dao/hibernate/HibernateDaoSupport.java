package com.zilu.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zilu.dao.DaoFactory;
import com.zilu.dao.DaoTemplate;


public abstract class HibernateDaoSupport {
	
	protected HibernateDaoHelper daoHelper() {
		return DaoFactory.hibernateFacotry().daoHelper();
	}
	
	protected HibernateDaoHelper hqlDaoHelper() {
		return DaoFactory.hibernateFacotry().hqlDaoHelper();
	}

	protected DaoTemplate daoTemplate() {
		return DaoFactory.hibernateFacotry().daoTemplate();
	}
	
	protected DaoTemplate hqlDaoTemplate() {
		return DaoFactory.hibernateFacotry().hqlDaoTemplate();
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Object obj) {
		hqlDaoTemplate().save(obj);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(Object obj) {
		hqlDaoTemplate().update(obj);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Object obj) {
		hqlDaoTemplate().delete(obj);
	}
	
	public void refresh(Object obj) {
		hqlDaoTemplate().refresh(obj);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> void batchSave(List<T> list) {
		for (Object obj : list) {
			hqlDaoTemplate().save(obj);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public <T> void batchSaveOrUpdate(List<T> list) {
		for (Object obj : list) {
			hqlDaoTemplate().saveOrUpdate(obj);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Object ...objs) {
		for (Object obj : objs) {
			hqlDaoTemplate().save(obj);
		}
	}
	
	public <T> T getById(Class<T> clazz, Serializable id) {
		return hqlDaoTemplate().get(clazz, id);
	}
	
	public <T> List<T> getAll(Class<T> clazz) {
		return hqlDaoTemplate().getAll(clazz);
	}
	
	public <T> void saveOrUpdate(Object obj) {
		hqlDaoTemplate().saveOrUpdate(obj);
	}
	
}
