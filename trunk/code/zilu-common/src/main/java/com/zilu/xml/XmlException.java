package com.zilu.xml;

public class XmlException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5806449923479301651L;

	public XmlException(String message)
    {
        super(message);
    }

    public XmlException(Throwable t)
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
