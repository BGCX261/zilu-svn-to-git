package com.zilu.util;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PrimitiveUtil {
	
	private static final Map primitiveWrapperTypeMap = new HashMap(8);

	/**
	 * Map with primitive type name as key and corresponding primitive
	 * type as value, for example: "int" -> "int.class".
	 */
	private static final Map primitiveTypeNameMap = new HashMap(16);
	
	
	private static final Map primitiveTypeMap = new HashMap(8);


	static {
		primitiveTypeMap.put(boolean.class, Boolean.class);
		primitiveTypeMap.put(byte.class, Byte.class);
		primitiveTypeMap.put(char.class, Character.class);
		primitiveTypeMap.put(double.class, Double.class);
		primitiveTypeMap.put(float.class, Float.class);
		primitiveTypeMap.put(int.class, Integer.class);
		primitiveTypeMap.put(long.class, Long.class);
		primitiveTypeMap.put(short.class, Short.class);
		
		primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
		primitiveWrapperTypeMap.put(Byte.class, byte.class);
		primitiveWrapperTypeMap.put(Character.class, char.class);
		primitiveWrapperTypeMap.put(Double.class, double.class);
		primitiveWrapperTypeMap.put(Float.class, float.class);
		primitiveWrapperTypeMap.put(Integer.class, int.class);
		primitiveWrapperTypeMap.put(Long.class, long.class);
		primitiveWrapperTypeMap.put(Short.class, short.class);

		Set primitiveTypeNames = new HashSet(16);
		primitiveTypeNames.addAll(primitiveWrapperTypeMap.values());
		primitiveTypeNames.addAll(Arrays.asList(new Class[] {
				boolean[].class, byte[].class, char[].class, double[].class,
				float[].class, int[].class, long[].class, short[].class}));
		for (Iterator it = primitiveTypeNames.iterator(); it.hasNext();) {
			Class primitiveClass = (Class) it.next();
			primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
		}
	}

	
	public static Object convert(String value, Class clazz) {
		if (clazz.equals(String.class)) {
			return value;
		}
		else if (clazz.equals(int.class)|| clazz.equals(Integer.class)) {
			return Integer.parseInt(value);
		}
		else if (clazz.equals(long.class)|| clazz.equals(Long.class)) {
			return Long.parseLong(value);
		}
		else if (clazz.equals(boolean.class)|| clazz.equals(Boolean.class)) {
			return Boolean.parseBoolean(value);
		}
		else if (clazz.equals(float.class)|| clazz.equals(Float.class)) {
			return Float.parseFloat(value);
		}
		else if (clazz.equals(double.class)|| clazz.equals(Double.class)) {
			return Double.parseDouble(value);
		}
		else if (Date.class.isAssignableFrom(clazz)) {
			return DateUtil.getMatchedDate(value);
		}
		else {
			return null;
		}
	}
	
	public static Object convert(String[] values, Class clazz) {
		if (clazz.isArray() && clazz.getComponentType().isPrimitive()) {
			Class arrayType = clazz.getComponentType();
			if (arrayType.equals(String.class)) {
				return values;
			}
			else if (arrayType.equals(int.class)|| arrayType.equals(Integer.class)) {
				Integer[] io = new Integer[values.length];
				for (int i = 0; i < values.length; i++) {
					io[i] = Integer.parseInt(values[i]);
				}
				return io;
			}
			else if (arrayType.equals(long.class)|| arrayType.equals(Long.class)) {
				Long[] io = new Long[values.length];
				for (int i = 0; i < values.length; i++) {
					io[i] = Long.parseLong(values[i]);
				}
				return io;
			}
			else if (arrayType.equals(boolean.class)|| arrayType.equals(Boolean.class)) {
				Boolean[] io = new Boolean[values.length];
				for (int i = 0; i < values.length; i++) {
					io[i] = Boolean.parseBoolean(values[i]);
				}
				return io;
			}
			else if (arrayType.equals(float.class)|| arrayType.equals(Float.class)) {
				Float[] io = new Float[values.length];
				for (int i = 0; i < values.length; i++) {
					io[i] = Float.parseFloat(values[i]);
				}
				return io;
			}
			else if (arrayType.equals(double.class)|| arrayType.equals(Double.class)) {
				Double[] io = new Double[values.length];
				for (int i = 0; i < values.length; i++) {
					io[i] = Double.parseDouble(values[i]);
				}
				return io;
			}
			else if (arrayType.equals(Date.class)) {
				Date[] io = new Date[values.length];
				for (int i = 0; i < values.length; i++) {
					io[i] = DateUtil.getMatchedDate(values[i]);
				}
				return io;
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}
	
	public static boolean isPrimitiveWrapper(Class clazz) {
		return primitiveWrapperTypeMap.containsKey(clazz);
	}

	public static boolean isPrimitiveOrWrapper(Class clazz) {
		return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
	}

	public static boolean isPrimitiveArray(Class clazz) {
		return (clazz.isArray() && clazz.getComponentType().isPrimitive());
	}
	
	public static Class getWapper(Class clazz) {
		return (Class) primitiveTypeMap.get(clazz);
	}
	
}
