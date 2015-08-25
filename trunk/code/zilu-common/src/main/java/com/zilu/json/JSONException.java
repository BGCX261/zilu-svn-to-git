package com.zilu.json;

public class JSONException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6850044983908931053L;

	public JSONException(String message)
    {
        super(message);
    }

    public JSONException(Throwable t)
    {
        super(t.getMessage());
        cause = t;
    }

    public Throwable getCause()
    {
        return cause;
    }

    private Throwable cause;
}
