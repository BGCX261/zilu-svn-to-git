package com.zilu.sql;

public abstract class DynamicSqlFilter extends SqlFilter {
	
	public String doFilter(String sql) {
		return doFilter(sql, new SqlContext());
	}
	
	public abstract String doFilter(String sql, SqlContext context);
}
