package com.zilu.sql;

/**
 * 过滤回车，换行等字符
 * @author 陈华敏
 * @Time 
 * @Description
 */
public class CommonSqlFilter extends SqlFilter{
	
	private static final char[] REPLACEMENT_CHARS = new char[] {'\t', '\f', '\r', '\n'};


	private static String cleanStatement(String statement) {
        statement = statement.trim();
        for (int i = 0; i < REPLACEMENT_CHARS.length; i++) {
            statement = statement.replace(REPLACEMENT_CHARS[i], ' ');
        }
        return statement;
    }


	@Override
	public String doFilter(String sql) {
		sql = cleanStatement(sql);
		return sql;
	}
}
