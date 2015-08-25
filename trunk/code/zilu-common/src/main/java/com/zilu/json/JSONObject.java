
package com.zilu.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import com.zilu.util.BeanUtils;
import com.zilu.util.PrimitiveUtil;


public class JSONObject
{
    private static final class Null
    {

        protected final Object clone()
        {
            return this;
        }

        public boolean equals(Object object)
        {
            return object == null || object == this;
        }

        public String toString()
        {
            return "null";
        }

        private Null()
        {
        }

    }


    public JSONObject()
    {
        map = new HashMap();
    }

    public JSONObject(JSONObject jo, String names[])
        throws JSONException
    {
        this();
        for(int i = 0; i < names.length; i++)
            putOpt(names[i], jo.opt(names[i]));

    }

    public JSONObject(JSONTokener x)
        throws JSONException
    {
        this();
        if(x.nextClean() != '{')
            throw x.syntaxError("A JSONObject text must begin with '{'");
        do
        {
            char c = x.nextClean();
            switch(c)
            {
            case 0: // '\0'
                throw x.syntaxError("A JSONObject text must end with '}'");

            case 125: // '}'
                return;
            }
            x.back();
            String key = x.nextValue().toString();
            c = x.nextClean();
            if(c == '=')
            {
                if(x.next() != '>')
                    x.back();
            } else
            if(c != ':')
                throw x.syntaxError("Expected a ':' after a key");
            String nextValue =String.valueOf(x.nextValue());
            put(key, nextValue);
            switch(x.nextClean())
            {
            case 44: // ','
            case 59: // ';'
                if(x.nextClean() == '}')
                    return;
                x.back();
                break;

            case 125: // '}'
                return;

            default:
                throw x.syntaxError("Expected a ',' or '}'");
            }
        } while(true);
    }

    public JSONObject(Map map)
    {
        this.map = ((Map) (map != null ? map : ((Map) (new HashMap()))));
    }

    public JSONObject(Map map, boolean includeSuperClass)
    {
        this.map = new HashMap();
        if(map != null)
        {
            java.util.Map.Entry e;
            for(Iterator i = map.entrySet().iterator(); i.hasNext(); this.map.put(e.getKey(), new JSONObject(e.getValue(), includeSuperClass)))
                e = (java.util.Map.Entry)i.next();

        }
    }

    public JSONObject(Object bean)
    {
        this();
        populateInternalMap(bean, true);
    }

    public JSONObject(Object bean, boolean includeSuperClass)
    {
        this();
        populateInternalMap(bean, includeSuperClass);
    }

    private void populateInternalMap(Object bean, boolean includeSuperClass)
    {
        Class klass = bean.getClass();
        if(klass.getClassLoader() == null)
            includeSuperClass = false;
        Method methods[] = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
        for(int i = 0; i < methods.length; i++)
            try
            {
                Method method = methods[i];
                String name = method.getName();
                if (name.equals("getClass")) {
                	continue;
                }
                String key = "";
                if(name.startsWith("get"))
                    key = name.substring(3);
                else
                if(name.startsWith("is"))
                    key = name.substring(2);
                if(key.length() <= 0 || !Character.isUpperCase(key.charAt(0)) || method.getParameterTypes().length != 0)
                    continue;
                if(key.length() == 1)
                    key = key.toLowerCase();
                else
                if(!Character.isUpperCase(key.charAt(1)))
                    key = (new StringBuilder()).append(key.substring(0, 1).toLowerCase()).append(key.substring(1)).toString();
                Object result = method.invoke(bean, (Object[])null);
                if(result == null)
                {
//                  map.put(key, NULL);
                    continue;
                }
                if(result.getClass().isArray())
                {
                    map.put(key, new JSONArray(result, includeSuperClass));
                    continue;
                }
                if(result instanceof Collection)
                {
                    map.put(key, new JSONArray((Collection)result, includeSuperClass));
                    continue;
                }
                if(result instanceof Map)
                {
                    map.put(key, new JSONObject((Map)result, includeSuperClass));
                    continue;
                }
                if(isStandardProperty(result.getClass()))
                {
                    map.put(key, result);
                    continue;
                }
                if(result.getClass().getPackage().getName().startsWith("java") || result.getClass().getClassLoader() == null)
                    map.put(key, result.toString());
                else
                    map.put(key, new JSONObject(result, includeSuperClass));
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }

    }

    private boolean isStandardProperty(Class clazz) {
    	return PrimitiveUtil.isPrimitiveOrWrapper(clazz);
    }

    public JSONObject(Object object, String names[])
    {
        this();
        Class c = object.getClass();
        for(int i = 0; i < names.length; i++)
        {
            String name = names[i];
            try
            {
                Field field = c.getField(name);
                Object value = field.get(object);
                put(name, value);
            }
            catch(Exception e) { }
        }

    }

    public JSONObject(String source)
        throws JSONException
    {
        this(new JSONTokener(source));
    }

    public JSONObject accumulate(String key, Object value)
        throws JSONException
    {
        testValidity(value);
        Object o = opt(key);
        if(o == null)
            put(key, (value instanceof JSONArray) ? ((Object) ((new JSONArray()).put(value))) : value);
        else
        if(o instanceof JSONArray)
            ((JSONArray)o).put(value);
        else
            put(key, (new JSONArray()).put(o).put(value));
        return this;
    }

    public JSONObject append(String key, Object value)
        throws JSONException
    {
        testValidity(value);
        Object o = opt(key);
        if(o == null)
            put(key, (new JSONArray()).put(value));
        else
        if(o instanceof JSONArray)
            put(key, ((JSONArray)o).put(value));
        else
            throw new JSONException((new StringBuilder()).append("JSONObject[").append(key).append("] is not a JSONArray.").toString());
        return this;
    }

    public static String doubleToString(double d)
    {
        if(Double.isInfinite(d) || Double.isNaN(d))
            return "null";
        String s = Double.toString(d);
        if(s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0)
        {
            for(; s.endsWith("0"); s = s.substring(0, s.length() - 1));
            if(s.endsWith("."))
                s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public Object get(String key)
        throws JSONException
    {
        Object o = opt(key);
        if(o == null)
            throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] not found.").toString());
        else
            return o;
    }

    public boolean getBoolean(String key)
        throws JSONException
    {
        Object o = get(key);
        if(o.equals(Boolean.FALSE) || (o instanceof String) && ((String)o).equalsIgnoreCase("false"))
            return false;
        if(o.equals(Boolean.TRUE) || (o instanceof String) && ((String)o).equalsIgnoreCase("true"))
            return true;
        else
            throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a Boolean.").toString());
    }

    public double getDouble(String key)
        throws JSONException
    {
        Object o = get(key);
        try
        {
            return (o instanceof Number) ? ((Number)o).doubleValue() : Double.valueOf((String)o).doubleValue();
        }
        catch(Exception e)
        {
            throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a number.").toString());
        }
    }

    public int getInt(String key)
        throws JSONException
    {
        Object o = get(key);
        return (o instanceof Number) ? ((Number)o).intValue() : (int)getDouble(key);
    }

    public JSONArray getJSONArray(String key)
        throws JSONException
    {
        Object o = get(key);
        if(o instanceof JSONArray)
            return (JSONArray)o;
        else
            throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a JSONArray.").toString());
    }

    public JSONObject getJSONObject(String key)
        throws JSONException
    {
        Object o = get(key);
        if(o instanceof JSONObject)
            return (JSONObject)o;
        else
            throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a JSONObject.").toString());
    }

    public long getLong(String key)
        throws JSONException
    {
        Object o = get(key);
        return (o instanceof Number) ? ((Number)o).longValue() : (long)getDouble(key);
    }

    public static String[] getNames(JSONObject jo)
    {
        int length = jo.length();
        if(length == 0)
            return null;
        Iterator i = jo.keys();
        String names[] = new String[length];
        for(int j = 0; i.hasNext(); j++)
            names[j] = (String)i.next();

        return names;
    }

    public static String[] getNames(Object object)
    {
        if(object == null)
            return null;
        Class klass = object.getClass();
        Field fields[] = klass.getFields();
        int length = fields.length;
        if(length == 0)
            return null;
        String names[] = new String[length];
        for(int i = 0; i < length; i++)
            names[i] = fields[i].getName();

        return names;
    }

    public String getString(String key)
        throws JSONException
    {
        return get(key).toString();
    }

    public boolean has(String key)
    {
        return map.containsKey(key);
    }

    public boolean isNull(String key)
    {
    	return key == null;
//      return NULL.equals(opt(key));
    }

    public Iterator keys()
    {
        return map.keySet().iterator();
    }

    public int length()
    {
        return map.size();
    }

    public JSONArray names()
    {
        JSONArray ja = new JSONArray();
        for(Iterator keys = keys(); keys.hasNext(); ja.put(keys.next()));
        return ja.length() != 0 ? ja : null;
    }

    public static String numberToString(Number n)
        throws JSONException
    {
        if(n == null)
            throw new JSONException("Null pointer");
        testValidity(n);
        String s = n.toString();
        if(s.indexOf('.') > 0 && s.indexOf('e') < 0 && s.indexOf('E') < 0)
        {
            for(; s.endsWith("0"); s = s.substring(0, s.length() - 1));
            if(s.endsWith("."))
                s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public Object opt(String key)
    {
        return key != null ? map.get(key) : null;
    }

    public boolean optBoolean(String key)
    {
        return optBoolean(key, false);
    }

    public boolean optBoolean(String key, boolean defaultValue)
    {
        try
        {
            return getBoolean(key);
        }
        catch(Exception e)
        {
            return defaultValue;
        }
    }

    public JSONObject put(String key, Collection value)
        throws JSONException
    {
        put(key, new JSONArray(value));
        return this;
    }

    public double optDouble(String key)
    {
        return optDouble(key, (0.0D / 0.0D));
    }

    public double optDouble(String key, double defaultValue)
    {
        try
        {
            Object o = opt(key);
            return (o instanceof Number) ? ((Number)o).doubleValue() : (new Double((String)o)).doubleValue();
        }
        catch(Exception e)
        {
            return defaultValue;
        }
    }

    public int optInt(String key)
    {
        return optInt(key, 0);
    }

    public int optInt(String key, int defaultValue)
    {
        try
        {
            return getInt(key);
        }
        catch(Exception e)
        {
            return defaultValue;
        }
    }

    public JSONArray optJSONArray(String key)
    {
        Object o = opt(key);
        return (o instanceof JSONArray) ? (JSONArray)o : null;
    }

    public JSONObject optJSONObject(String key)
    {
        Object o = opt(key);
        return (o instanceof JSONObject) ? (JSONObject)o : null;
    }

    public long optLong(String key)
    {
        return optLong(key, 0L);
    }

    public long optLong(String key, long defaultValue)
    {
        try
        {
            return getLong(key);
        }
        catch(Exception e)
        {
            return defaultValue;
        }
    }

    public String optString(String key)
    {
        return optString(key, "");
    }

    public String optString(String key, String defaultValue)
    {
        Object o = opt(key);
        return o == null ? defaultValue : o.toString();
    }

    public JSONObject put(String key, boolean value)
        throws JSONException
    {
        put(key, value ? ((Object) (Boolean.TRUE)) : ((Object) (Boolean.FALSE)));
        return this;
    }

    public JSONObject put(String key, double value)
        throws JSONException
    {
        put(key, new Double(value));
        return this;
    }

    public JSONObject put(String key, int value)
        throws JSONException
    {
        put(key, new Integer(value));
        return this;
    }

    public JSONObject put(String key, long value)
        throws JSONException
    {
        put(key, new Long(value));
        return this;
    }

    public JSONObject put(String key, Map value)
        throws JSONException
    {
        put(key, new JSONObject(value));
        return this;
    }

    public JSONObject put(String key, Object value)
        throws JSONException
    {
        if(key == null)
            throw new JSONException("Null key.");
        if(value != null)
        {
            testValidity(value);
            map.put(key, value);
        } else
        {
            remove(key);
        }
        return this;
    }

    public JSONObject putOpt(String key, Object value)
        throws JSONException
    {
        if(key != null && value != null)
            put(key, value);
        return this;
    }

    public static String quote(String string)
    {
        if(string == null || string.length() == 0)
            return "\"\"";
        char c = '\0';
        int len = string.length();
        StringBuffer sb = new StringBuffer(len + 4);
        sb.append('"');
        for(int i = 0; i < len; i++)
        {
            char b = c;
            c = string.charAt(i);
            switch(c)
            {
            case 34: // '"'
            case 92: // '\\'
                sb.append('\\');
                sb.append(c);
                break;

            case 47: // '/'
                if(b == '<')
                    sb.append('\\');
                sb.append(c);
                break;

            case 8: // '\b'
                sb.append("\\b");
                break;

            case 9: // '\t'
                sb.append("\\t");
                break;

            case 10: // '\n'
                sb.append("\\n");
                break;

            case 12: // '\f'
                sb.append("\\f");
                break;

            case 13: // '\r'
                sb.append("\\r");
                break;

            default:
                if(c < ' ' || c >= '\200' && c < '\240' || c >= '\u2000' && c < '\u2100')
                {
                    String t = (new StringBuilder()).append("000").append(Integer.toHexString(c)).toString();
                    sb.append((new StringBuilder()).append("\\u").append(t.substring(t.length() - 4)).toString());
                } else
                {
                    sb.append(c);
                }
                break;
            }
        }

        sb.append('"');
        return sb.toString();
    }

    public Object remove(String key)
    {
        return map.remove(key);
    }

    public Iterator sortedKeys()
    {
        return (new TreeSet(map.keySet())).iterator();
    }

    static void testValidity(Object o)
        throws JSONException
    {
        if(o != null)
            if(o instanceof Double)
            {
                if(((Double)o).isInfinite() || ((Double)o).isNaN())
                    throw new JSONException("JSON does not allow non-finite numbers.");
            } else
            if((o instanceof Float) && (((Float)o).isInfinite() || ((Float)o).isNaN()))
                throw new JSONException("JSON does not allow non-finite numbers.");
    }

    public JSONArray toJSONArray(JSONArray names)
        throws JSONException
    {
        if(names == null || names.length() == 0)
            return null;
        JSONArray ja = new JSONArray();
        for(int i = 0; i < names.length(); i++)
            ja.put(opt(names.getString(i)));

        return ja;
    }

    public Map toMap() {
    	return map;
    }
    
    public String toString()
    {
        try
        {
            Iterator keys = keys();
            StringBuffer sb = new StringBuffer("{");
            Object o;
            for(; keys.hasNext(); sb.append(valueToString(map.get(o))))
            {
                if(sb.length() > 1)
                    sb.append(',');
                o = keys.next();
                sb.append(quote(o.toString()));
                sb.append(':');
            }

            sb.append('}');
            return sb.toString();
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String toString(int indentFactor)
        throws JSONException
    {
        return toString(indentFactor, 0);
    }

    String toString(int indentFactor, int indent)
        throws JSONException
    {
        int n = length();
        if(n == 0)
            return "{}";
        Iterator keys = sortedKeys();
        StringBuffer sb = new StringBuffer("{");
        int newindent = indent + indentFactor;
        if(n == 1)
        {
            Object o = keys.next();
            sb.append(quote(o.toString()));
            sb.append(": ");
            sb.append(valueToString(map.get(o), indentFactor, indent));
        } else
        {
            Object o;
            for(; keys.hasNext(); sb.append(valueToString(map.get(o), indentFactor, newindent)))
            {
                o = keys.next();
                if(sb.length() > 1)
                    sb.append(",\n");
                else
                    sb.append('\n');
                for(int j = 0; j < newindent; j++)
                    sb.append(' ');

                sb.append(quote(o.toString()));
                sb.append(": ");
            }

            if(sb.length() > 1)
            {
                sb.append('\n');
                for(int j = 0; j < indent; j++)
                    sb.append(' ');

            }
        }
        sb.append('}');
        return sb.toString();
    }

    static String valueToString(Object value)
        throws JSONException
    {
        if(value == null || value.equals(null))
            return "null";
        if(value instanceof JSONString)
        {
            Object o;
            try
            {
                o = ((JSONString)value).toJSONString();
            }
            catch(Exception e)
            {
                throw new JSONException(e);
            }
            if(o instanceof String)
                return (String)o;
            else
                throw new JSONException((new StringBuilder()).append("Bad value from toJSONString: ").append(o).toString());
        }
        if(value instanceof Number)
            return numberToString((Number)value);
        if((value instanceof Boolean) || (value instanceof JSONObject) || (value instanceof JSONArray))
            return value.toString();
        if(value instanceof Map)
            return (new JSONObject((Map)value)).toString();
        if(value instanceof Collection)
            return (new JSONArray((Collection)value)).toString();
        if(value.getClass().isArray())
            return (new JSONArray(value)).toString();
        else
            return quote(value.toString());
    }

    static String valueToString(Object value, int indentFactor, int indent)
        throws JSONException
    {
        if(value == null || value.equals(null))
            return "null";
        try
        {
            if(value instanceof JSONString)
            {
                Object o = ((JSONString)value).toJSONString();
                if(o instanceof String)
                    return (String)o;
            }
        }
        catch(Exception e) { }
        if(value instanceof Number)
            return numberToString((Number)value);
        if(value instanceof Boolean)
            return value.toString();
        if(value instanceof JSONObject)
            return ((JSONObject)value).toString(indentFactor, indent);
        if(value instanceof JSONArray)
            return ((JSONArray)value).toString(indentFactor, indent);
        if(value instanceof Map)
            return (new JSONObject((Map)value)).toString(indentFactor, indent);
        if(value instanceof Collection)
            return (new JSONArray((Collection)value)).toString(indentFactor, indent);
        if(value.getClass().isArray())
            return (new JSONArray(value)).toString(indentFactor, indent);
        else
            return quote(value.toString());
    }

    public Writer write(Writer writer)
        throws JSONException
    {
        try
        {
            boolean b = false;
            Iterator keys = keys();
            writer.write(123);
            for(; keys.hasNext(); b = true)
            {
                if(b)
                    writer.write(44);
                Object k = keys.next();
                writer.write(quote(k.toString()));
                writer.write(58);
                Object v = map.get(k);
                if(v instanceof JSONObject)
                {
                    ((JSONObject)v).write(writer);
                    continue;
                }
                if(v instanceof JSONArray)
                    ((JSONArray)v).write(writer);
                else
                    writer.write(valueToString(v));
            }

            writer.write(125);
            return writer;
        }
        catch(IOException e)
        {
            throw new JSONException(e);
        }
    }

    private Map map;
    public static final Object NULL = new Null();

}
