/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.zilu.json;

import java.io.*;

import com.zilu.util.BeanUtils;

// Referenced classes of package com.taobao.api.json:
//            JSONException, JSONObject, JSONArray

public class JSONTokener
{

    public JSONTokener(Reader reader)
    {
        this.reader = ((Reader) (reader.markSupported() ? reader : ((Reader) (new BufferedReader(reader)))));
        useLastChar = false;
        index = 0;
    }

    public JSONTokener(String s)
    {
        this(((Reader) (new StringReader(s))));
    }

    public void back()
        throws JSONException
    {
        if(useLastChar || index <= 0)
        {
            throw new JSONException("Stepping back two steps is not supported");
        } else
        {
            index--;
            useLastChar = true;
            return;
        }
    }

    public static int dehexchar(char c)
    {
        if(c >= '0' && c <= '9')
            return c - 48;
        if(c >= 'A' && c <= 'F')
            return c - 55;
        if(c >= 'a' && c <= 'f')
            return c - 87;
        else
            return -1;
    }

    public boolean more()
        throws JSONException
    {
        char nextChar = next();
        if(nextChar == 0)
        {
            return false;
        } else
        {
            back();
            return true;
        }
    }

    public char next()
        throws JSONException
    {
        if(useLastChar)
        {
            useLastChar = false;
            if(lastChar != 0)
                index++;
            return lastChar;
        }
        int c;
        try
        {
            c = reader.read();
        }
        catch(IOException exc)
        {
            throw new JSONException(exc);
        }
        if(c <= 0)
        {
            lastChar = '\0';
            return '\0';
        } else
        {
            index++;
            lastChar = (char)c;
            return lastChar;
        }
    }

    public char next(char c)
        throws JSONException
    {
        char n = next();
        if(n != c)
            throw syntaxError((new StringBuilder()).append("Expected '").append(c).append("' and instead saw '").append(n).append("'").toString());
        else
            return n;
    }

    public String next(int n)
        throws JSONException
    {
        if(n == 0)
            return "";
        char buffer[] = new char[n];
        int pos = 0;
        if(useLastChar)
        {
            useLastChar = false;
            buffer[0] = lastChar;
            pos = 1;
        }
        try
        {
            int len;
            for(; pos < n && (len = reader.read(buffer, pos, n - pos)) != -1; pos += len);
        }
        catch(IOException exc)
        {
            throw new JSONException(exc);
        }
        index += pos;
        if(pos < n)
        {
            throw syntaxError("Substring bounds error");
        } else
        {
            lastChar = buffer[n - 1];
            return new String(buffer);
        }
    }

    public char nextClean()
        throws JSONException
    {
        char c;
        do
            c = next();
        while(c != 0 && c <= ' ');
        return c;
    }

    public String nextString(char quote)
        throws JSONException
    {
        StringBuffer sb = new StringBuffer();
        do
        {
            char c = next();
            switch(c)
            {
            case 0: // '\0'
            case 10: // '\n'
            case 13: // '\r'
                throw syntaxError("Unterminated string");

            case 92: // '\\'
                c = next();
                switch(c)
                {
                case 98: // 'b'
                    sb.append('\b');
                    break;

                case 116: // 't'
                    sb.append('\t');
                    break;

                case 110: // 'n'
                    sb.append('\n');
                    break;

                case 102: // 'f'
                    sb.append('\f');
                    break;

                case 114: // 'r'
                    sb.append('\r');
                    break;

                case 117: // 'u'
                    sb.append((char)Integer.parseInt(next(4), 16));
                    break;

                case 120: // 'x'
                    sb.append((char)Integer.parseInt(next(2), 16));
                    break;

                case 99: // 'c'
                case 100: // 'd'
                case 101: // 'e'
                case 103: // 'g'
                case 104: // 'h'
                case 105: // 'i'
                case 106: // 'j'
                case 107: // 'k'
                case 108: // 'l'
                case 109: // 'm'
                case 111: // 'o'
                case 112: // 'p'
                case 113: // 'q'
                case 115: // 's'
                case 118: // 'v'
                case 119: // 'w'
                default:
                    sb.append(c);
                    break;
                }
                break;

            default:
                if(c == quote)
                    return sb.toString();
                sb.append(c);
                break;
            }
        } while(true);
    }

    public String nextTo(char d)
        throws JSONException
    {
        StringBuffer sb = new StringBuffer();
        do
        {
            char c = next();
            if(c == d || c == 0 || c == '\n' || c == '\r')
            {
                if(c != 0)
                    back();
                return sb.toString().trim();
            }
            sb.append(c);
        } while(true);
    }

    public String nextTo(String delimiters)
        throws JSONException
    {
        StringBuffer sb = new StringBuffer();
        do
        {
            char c = next();
            if(delimiters.indexOf(c) >= 0 || c == 0 || c == '\n' || c == '\r')
            {
                if(c != 0)
                    back();
                return sb.toString().trim();
            }
            sb.append(c);
        } while(true);
    }

    public Object nextValue()
        throws JSONException
    {
        char c = nextClean();
        switch(c)
        {
        case 34: // '"'
        case 39: // '\''
            return nextString(c);

        case 123: // '{'
            back();
            return new JSONObject(this);

        case 40: // '('
        case 91: // '['
            back();
            return new JSONArray(this);
        }
        StringBuffer sb = new StringBuffer();
        char b = c;
        for(; c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0; c = next())
            sb.append(c);

        back();
        String s = sb.toString().trim();
        if(s.equals(""))
            throw syntaxError("Missing value");
        if(s.equalsIgnoreCase("true"))
            return Boolean.TRUE;
        if(s.equalsIgnoreCase("false"))
            return Boolean.FALSE;
        if(s.equalsIgnoreCase("null"))
            return JSONObject.NULL;
        if(b >= '0' && b <= '9' || b == '.' || b == '-' || b == '+')
        {
            if(b == '0')
                if(s.length() > 2 && (s.charAt(1) == 'x' || s.charAt(1) == 'X'))
                    try
                    {
                        return new Integer(Integer.parseInt(s.substring(2), 16));
                    }
                    catch(Exception e) { }
                else
                    try
                    {
                        return new Integer(Integer.parseInt(s, 8));
                    }
                    catch(Exception e) { }
            try
            {
                return new Integer(s);
            }
            catch(Exception e) { }
            try
            {
                return new Long(s);
            }
            catch(Exception f) { }
            try
            {
                return new Double(s);
            }
            catch(Exception g)
            {
                return s;
            }
        } else
        {
            return s;
        }
    }

    public char skipTo(char to)
        throws JSONException
    {
        int startIndex;
        startIndex = index;
        try {
	        reader.mark(2147483647);
	        char c = next();
	        while(c != to) {
		        if(c == 0)
		        {
		            reader.reset();
		            index = startIndex;
		            return c;
		        }
		        c = next();
	        }
	        back();
	        return c;
        } catch (IOException ie) {
        	throw new JSONException(ie);
        }
    }

    public JSONException syntaxError(String message)
    {
        return new JSONException((new StringBuilder()).append(message).append(toString()).toString());
    }

    public String toString()
    {
        return (new StringBuilder()).append(" at character ").append(index).toString();
    }

    private int index;
    private Reader reader;
    private char lastChar;
    private boolean useLastChar;
}

