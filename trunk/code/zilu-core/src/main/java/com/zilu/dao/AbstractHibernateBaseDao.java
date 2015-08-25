package com.zilu.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zilu.sql.SqlFacade;

/**
 * 
 * @author sai.l
 * 
 * @param <T>
 *            The model class
 * @param <ID>
 *            The ID of the model,it must implements Serializable
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateBaseDao<T, ID extends Serializable> extends HibernateDaoSupport {

	protected final Logger log = Logger.getLogger(getClass());

	private final Class<T> persistentClass;

	private final DaoTemplate sqlDaoTemplate;

	private final DaoTemplate hqlDaoTemplate;

	public AbstractHibernateBaseDao(SessionFactory sessionFactory) {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
		this.sqlDaoTemplate = DaoTemplateFactory.getInstance().getDaoTemplate(getHibernateTemplate(), true);
		this.hqlDaoTemplate = DaoTemplateFactory.getInstance().getDaoTemplate(getHibernateTemplate(), false);
		setSessionFactory(sessionFactory);
	}

	/**
	 * Find all
	 * 
	 * @return The model list
	 */
	public List<T> findAll() {
		return getHibernateTemplate().loadAll(persistentClass);
	}

	/**
	 * Find Object by Id
	 * 
	 * @param id
	 * @return Object
	 */
	public T findById(ID id) {
		return getHibernateTemplate().load(persistentClass, id);
	}

	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}

	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void refresh(T entity) {
		getHibernateTemplate().refresh(entity);
	}

	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	public void executeUpdate(DaoTemplateType type, String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		getTemplate(type).executeUpdate(sql, params);
	}

	public void executeUpdate(DaoTemplateType type, String qname, Map<String, Object> paramMap) {
		String sql = SqlFacade.getSql(qname);
		getTemplate(type).executeUpdate(sql, paramMap);
	}

	public Object queryFirst(DaoTemplateType type, String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate(type).queryFirst(sql, params);
	}

	
	public List queryList(DaoTemplateType type, String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate(type).queryList(sql, params);
	}

	public List queryList(DaoTemplateType type, String qname, Map<String, Object> paramMap) {
		return queryList(type, qname, paramMap, -1, -1);
	}

	public List queryList(DaoTemplateType type, String qname, Map<String, Object> paramMap, int firstRow, int rowSize) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate(type).queryList(sql, paramMap, firstRow, rowSize);
	}

	public Object queryFirst(DaoTemplateType type, String qname, Map<String, Object> parameterMap) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate(type).queryFirst(sql, parameterMap);
	}

	public Object querySingle(DaoTemplateType type, String qname, Object... params) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate(type).querySingle(sql, params);
	}

	public Object querySingle(DaoTemplateType type, String qname, Map<String, Object> paramMap) {
		String sql = SqlFacade.getSql(qname);
		return getTemplate(type).querySingle(sql, paramMap);
	}

	public PageBean queryPage(DaoTemplateType type, String qname, Map<String, Object> conditions, int pageNo,
			int pageSize, String orderBy) {
		String qhql = SqlFacade.getSql(qname);
		int index = qhql.indexOf("order by");
		String chql = qhql;
		if (index != -1) {
			chql = qhql.substring(0, index);
		}
		if (chql.toLowerCase().indexOf("select") == 0 && chql.toLowerCase().indexOf("from") > 0) {
			chql = chql.substring(chql.indexOf("from"));
		}
		chql = "select count(*) " + chql;
		if (!StringUtils.isEmpty(orderBy)) {
			qhql = qhql + " " + orderBy;
		}
		return getTemplate(type).queryPage(chql, qhql, conditions, pageNo, pageSize);
	}

	public PageBean queryPage(DaoTemplateType type, String qname, String cname, Map<String, Object> conditions,
			int pageNo, int pageSize, String orderBy) {
		String qhql = SqlFacade.getSql(qname);
		String chql = SqlFacade.getSql(cname);
		if (!StringUtils.isEmpty(orderBy)) {
			qhql = qhql + orderBy;
		}
		return getTemplate(type).queryPage(chql, qhql, conditions, pageNo, pageSize);
	}

	private DaoTemplate getTemplate(DaoTemplateType type) {
		if (DaoTemplateType.HQL.equals(type)) {
			return this.hqlDaoTemplate;
		}
		return this.sqlDaoTemplate;
	}

}
