package com.zilu.dao.sql;

import java.util.ArrayList;
import java.util.List;

public class SqlFilterBuilder {
	
	
	static List<SqlFilter> build(String statement) {
		List<SqlFilter> filters = new ArrayList<SqlFilter>();
		filters.add(new CommonSqlFilter());
		if (SqlFilter.Type.isFreeMarker(statement)) {
			filters.add(new FreemarkerSqlFilter());
    	}
		if (SqlFilter.Type.isTestReplacement(statement)) {
			filters.add(new TextReplacementSqlFilter());
		}
		return filters;
	}
	
	static List<SqlFilter> buildSimple(String statement) {
		List<SqlFilter> filters = new ArrayList<SqlFilter>();
		filters.add(new CommonSqlFilter());
		return filters;
	}
}
