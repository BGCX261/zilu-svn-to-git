package com.zilu.sql;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.zilu.util.PrimitiveUtil;


public class SqlFilterFacade {
	
	public static boolean isParameterAble(Object obj) {
		return obj != null&& !PrimitiveUtil.isPrimitiveOrWrapper(obj.getClass())
			&& !PrimitiveUtil.isPrimitiveArray(obj.getClass())
			&& !(obj instanceof Collection);
	}
	
	public static String filterSql(String sql, Object... obj) {
		if (obj.length == 1) {
//			假设只有一个参数，则默认把它的属性作为map来使用。
			if (isParameterAble(obj[0])) {
				Map map = null;
				if (obj[0] instanceof Map) {
					map = (Map) obj[0];
				}
				else {
					try {
						map = BeanUtils.describe(obj[0]);
					} catch (Exception e) {
					} 
				}
				if (map != null) {
					return filterSql(sql, map);
				}
			} 
		}
		List<SqlFilter> filters = SqlFilterBuilder.buildSimple(sql);
		return filter(filters, sql);
	}
	
	public static String filterSql(String sql, Map<String, Object> params) {
		List<SqlFilter> filters = SqlFilterBuilder.build(sql);
		SqlContext context = new SqlContext(params);
		return filter(filters, sql, context);
	}
	
	static String filter(List<SqlFilter> filters,  String sql) {
		for (SqlFilter filter : filters) {
			if (filter instanceof DynamicSqlFilter) {
				continue;
			}
			sql = filter.doFilter(sql);
		}
		return sql;
	}
	
	static String filter(List<SqlFilter> filters,  String sql, SqlContext context) {
		for (SqlFilter filter : filters) {
			if (filter instanceof DynamicSqlFilter) {
				sql = ((DynamicSqlFilter)filter).doFilter(sql, context);
			}
			else {
				sql = filter.doFilter(sql);
			}
		}
		return sql;
	}
}
