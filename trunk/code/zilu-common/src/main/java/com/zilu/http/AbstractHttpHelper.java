package com.zilu.http;

import org.apache.log4j.Logger;

public abstract class AbstractHttpHelper implements HttpHelper {
	
	protected static final int DEFAULT_CONNECT_TIMEOUT = 20000;
    protected static final int DEFAULT_READ_TIMEOUT = 120000;
    protected static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 1000;
    protected static final int DEFAULT_MAX_CONNECTIONS_PER_HOST = 2;
    protected static final boolean DEFAULT_STALE_CHECKING_ENABLED = true;

    protected final Logger logger = Logger.getLogger(getClass());
}
