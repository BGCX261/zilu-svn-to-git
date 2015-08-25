package com.zilu.base;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:applicationContext-dao.xml" })
public class AbstractBaseTestSupport extends AbstractTransactionalJUnit4SpringContextTests {
	protected Logger log = Logger.getLogger(getClass());

}
