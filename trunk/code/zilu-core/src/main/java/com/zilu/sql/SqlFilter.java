package com.zilu.sql;

import java.util.HashMap;
import java.util.Map;


public abstract class SqlFilter {

	static final class Type {
		private static final Map<String, Type> types = new HashMap<String, Type>();

		public static final Type FREEMARKER = new Type("FreeMarker");

		public static final Type COMMON = new Type("Common");
		
		public static final Type TEXT_REPLACEMENT = new Type("TextReplacement");

		private final String typeName;

		private Type(String typeName) {
			this.typeName = typeName;
		}

		/**
		 * @inheritDoc
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return this.typeName;
		}

		static {
			types.put(COMMON.toString(), COMMON);
			types.put(FREEMARKER.toString(), FREEMARKER);
		}

		static boolean isFreeMarker(String statement) {
			return ((statement.indexOf("<#if") > -1 && statement
					.indexOf("</#if>") > -1) || (statement.indexOf("<#list") > -1 && statement
					.indexOf("</#list>") > -1));
		}
		
		static boolean isTestReplacement(String statement) {
			return statement.indexOf("{{") > -1 && statement.indexOf("}}") > -1;
		}

		static Type getInstance(String name) throws NoSuchFieldException {
			if (name == null || name.trim().length() == 0) {
				return COMMON;
			}
			if (!types.containsKey(name)) {
				throw new NoSuchFieldException("Invalid statement type '"
						+ name + "'");
			}
			return (Type) types.get(name);
		}
	}
	
	public abstract String doFilter(String sql) ;
	
}
