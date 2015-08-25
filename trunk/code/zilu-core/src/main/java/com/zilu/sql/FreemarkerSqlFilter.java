package com.zilu.sql;

import java.io.StringReader;
import java.io.StringWriter;


import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class FreemarkerSqlFilter extends DynamicSqlFilter {
	
	private static final String DEFAULT_TEMPLATE_NAME = "SqlTemplate";

	@SuppressWarnings("deprecation")
	@Override
	public String doFilter(String sql, SqlContext context) {
		String templateName = context.getName() == null? DEFAULT_TEMPLATE_NAME : context.getName();
		try {
			Template template = new Template(templateName, new StringReader(sql));
			StringWriter writer = new StringWriter();
			template.process(new SqlHashModel(context), writer);
			return writer.getBuffer().toString();
		} catch (Exception e) {
			throw new SqlException(e);
		}
	}

	class SqlHashModel extends SimpleHash {

		SqlContext context;
		/**
		 * 
		 */
		private static final long serialVersionUID = 7349437143780454407L;
		
		public SqlHashModel(SqlContext context) {
			this.context = context;
		}
		
		@Override
		public TemplateModel get(String name) throws TemplateModelException {
			 Object obj = context.get(name);
			 TemplateModel model = null;
			 if (obj != null) {
				 model = wrap(obj);
			 }
			 return model;
		}
		
	}

}
