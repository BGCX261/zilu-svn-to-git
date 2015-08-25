/**
 * 
 */
package com.zilu.util;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;




/**
 * @author dell
 * 
 */
public class ReflectUtil {
	
	

	public static Object getPropertyValue(Object obj, String name) {
		try {
			Field f = obj.getClass().getDeclaredField(name);
			return get(f, obj);
		} catch (Exception ex) {
			Method  m = getGetterMethod(obj.getClass(), name);
			return invokeAndWrap(m, obj, null);
		}
	}

	public static void setPropertyValue(Object obj, String name, Object value) {
		try {
			Field f = obj.getClass().getDeclaredField(name);
			set(f, obj, value);
				
		} catch (Exception ex) {
			Method  m = getSetterMethod(obj.getClass(), name);
			invokeAndWrap(m, obj, value);
		}
	}

	/**
	 * 返回对象所实现的泛型的类对象
	 * 
	 * @param obj
	 * @return
	 */
	public static Class getGenericClass(Object obj) {
		return getGenericClass(obj, 0);
	}

	public static Class getGenericClass(Object obj, int pos) {
		Class entityClass = null;
		Type type = obj.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			entityClass = (Class) paramType.getActualTypeArguments()[pos];
		} else {
			throw new IllegalArgumentException(
					"Could not guess entity class by reflection");
		}
		return entityClass;
	}

	public static Object invokeMethod(Object obj, String methodName,
			Object... params) {
		try {
			if (params !=null&& params.length > 0) {
				Class[] paramClass = new Class[params.length];
				for (int i = 0; i < params.length; i++) {
					paramClass[i] = params[i].getClass();
				}
				Method m = obj.getClass().getMethod(methodName, paramClass);
				return m.invoke(obj, params);
			} else {
				Method m = obj.getClass().getMethod(methodName);
				return m.invoke(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static boolean isInstanceOf(Class clazz, String name) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		for (Class c = clazz; c != Object.class; c = c.getSuperclass()) {
			if (name.equals(c.getName())) {
				return true;
			}
		}
		for (Class c : clazz.getInterfaces()) {
			if (name.equals(c.getName())) {
				return true;
			}
		}
		return false;
	}

	public static Object invoke(Method method, Object target, Object... args)
			throws Exception {
		try {
			return method.invoke(target, args);
		} catch (IllegalArgumentException iae) {
			String message = "Could not invoke method by reflection: "
					+ toString(method);
			if (args != null && args.length > 0) {
				message += " with parameters: ("
						+ Strings.toClassNameString(", ", args) + ')';
			}
			message += " on: " + target.getClass().getName();
			throw new IllegalArgumentException(message, iae);
		} catch (InvocationTargetException ite) {
			if (ite.getCause() instanceof Exception) {
				throw (Exception) ite.getCause();
			} else {
				throw ite;
			}
		}
	}

	public static Object get(Field field, Object target) {
		try {
			return field.get(target);
		} catch (IllegalAccessException e) {
			try {
				field.setAccessible(true);
				return get(field, target);
			} finally {
				field.setAccessible(false);
			}
		} catch (IllegalArgumentException iae) {
			String message = "Could not get field value by reflection: "
					+ toString(field) + " on: " + target.getClass().getName();
			throw new IllegalArgumentException(message, iae);
		}
	}

	public static void set(Field field, Object target, Object value)
			throws Exception {
		try {
			field.set(target, value);
		} catch (IllegalAccessException e) {
			try {
				field.setAccessible(true);
				set(field, target, value);
			} finally {
				field.setAccessible(false);
			}
		} catch (IllegalArgumentException iae) {
			// target may be null if field is static so use
			// field.getDeclaringClass() instead
			String message = "Could not set field value by reflection: "
					+ toString(field) + " on: "
					+ field.getDeclaringClass().getName();
			if (value == null) {
				message += " with null value";
			} else {
				message += " with value: " + value.getClass();
			}
			throw new IllegalArgumentException(message, iae);
		}
	}
	
	public static boolean isStatic (Field field) {
		return (field.getModifiers() & Modifier.STATIC) != 0;
	}

	public static Object getAndWrap(Field field, Object target) {
		try {
			return get(field, target);
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new IllegalArgumentException("exception setting: "
						+ field.getName(), e);
			}
		}
	}

	public static void setAndWrap(Field field, Object target, Object value) {
		try {
			set(field, target, value);
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new IllegalArgumentException("exception setting: "
						+ field.getName(), e);
			}
		}
	}

	public static Object invokeAndWrap(Method method, Object target,
			Object... args) {
		try {
			return invoke(method, target, args);
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException("exception invoking: "
						+ method.getName(), e);
			}
		}
	}

	private static String toString(Method method) {
		return Strings.unqualify(method.getDeclaringClass().getName()) + '.'
				+ method.getName() + '('
				+ Strings.toString(", ", method.getParameterTypes()) + ')';
	}

	private static String toString(Field field) {
		return Strings.unqualify(field.getDeclaringClass().getName()) + '.'
				+ field.getName();
	}

	public static Class classForName(String name) throws ClassNotFoundException {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(
					name);
		} catch (Exception e) {
			return Class.forName(name);
		}
	}

	/**
	 * Return's true if the class can be loaded using Reflections.classForName()
	 */
	public static boolean isClassAvailable(String name) {
		try {
			classForName(name);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	public static Class getCollectionElementType(Type collectionType) {
		if (!(collectionType instanceof ParameterizedType)) {
			throw new IllegalArgumentException(
					"collection type not parameterized");
		}
		Type[] typeArguments = ((ParameterizedType) collectionType)
				.getActualTypeArguments();
		if (typeArguments.length == 0) {
			throw new IllegalArgumentException(
					"no type arguments for collection type");
		}
		Type typeArgument = typeArguments.length == 1 ? typeArguments[0]
				: typeArguments[1]; // handle Maps
		if (!(typeArgument instanceof Class)) {
			throw new IllegalArgumentException("type argument not a class");
		}
		return (Class) typeArgument;
	}

	public static Class getMapKeyType(Type collectionType) {
		if (!(collectionType instanceof ParameterizedType)) {
			throw new IllegalArgumentException(
					"collection type not parameterized");
		}
		Type[] typeArguments = ((ParameterizedType) collectionType)
				.getActualTypeArguments();
		if (typeArguments.length == 0) {
			throw new IllegalArgumentException(
					"no type arguments for collection type");
		}
		Type typeArgument = typeArguments[0];
		if (!(typeArgument instanceof Class)) {
			throw new IllegalArgumentException("type argument not a class");
		}
		return (Class) typeArgument;
	}

	public static Method getSetterMethod(Class clazz, String name) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set")
					&& method.getParameterTypes().length == 1) {
				if (Introspector.decapitalize(methodName.substring(3)).equals(
						name)) {
					return method;
				}
			}
		}
		throw new IllegalArgumentException("no such setter method: "
				+ clazz.getName() + '.' + name);
	}

	public static Method getGetterMethod(Class clazz, String name) {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.matches("^(get|is).*")
					&& method.getParameterTypes().length == 0) {
				if (Introspector.decapitalize(methodName.substring(3)).equals(
						name)) {
					return method;
				}
			}
		}
		throw new IllegalArgumentException("no such getter method: "
				+ clazz.getName() + '.' + name);
	}

	/**
	 * Get all the getter methods annotated with the given annotation. Returns
	 * an empty list if none are found
	 */
	public static List<Method> getGetterMethods(Class clazz, Class annotation) {
		List<Method> methods = new ArrayList<Method>();
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				methods.add(method);
			}
		}
		return methods;
	}

	public static Field getField(Class clazz, String name) {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(name);
			} catch (NoSuchFieldException nsfe) {
			}
		}
		throw new IllegalArgumentException("no such field: " + clazz.getName()
				+ '.' + name);
	}
	
	public static Class getFieldClass(Class clazz, String name) {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(name).getType();
			} catch (NoSuchFieldException nsfe) {
			}
		}
		return null;
	}

	/**
	 * Get all the fields which are annotated with the given annotation. Returns
	 * an empty list if none are found
	 */
	public static List<Field> getFields(Class clazz, Class annotation) {
		List<Field> fields = new ArrayList<Field>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			for (Field field : superClass.getDeclaredFields()) {
				if (field.isAnnotationPresent(annotation)) {
					fields.add(field);
				}
			}
		}
		return fields;
	}

	public static Method getMethod(Annotation annotation, String name) {
		try {
			return annotation.annotationType().getMethod(name);
		} catch (NoSuchMethodException nsme) {
			return null;
		}
	}
	
	public static Class[] getPackageClass(Package p) {
		String pName = p.getName();
		File f = new File(p.getClass().getResource("").getFile());
		File[] fs = f.listFiles();
		Class[] classes = new Class[fs.length];
		for (int i = 0; i < fs.length; i++) {
			try {
				String fileName = fs[i].getName();
				classes[i] = Class.forName(pName + "." + fileName.substring(0, fileName.lastIndexOf(".")));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classes;
	}
	
	public static <T> T instance(Class<T> tClass) {
		try {
			return tClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	public static String getInvokeMethodName(int level) {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();  
        return stack[level].getMethodName();  
	}
	
	public static String getInvokeClassName(int level) {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();  
        return stack[level].getClassName();
	}
	
	public static String getShortName(Class clazz) {
		String className = clazz.getName();
		int index = className.lastIndexOf(".");
		String realName;
		if (index == -1) {
			realName = className;
		}
		else {
			realName = className.substring(index + 1);
		}
		return Strings.uncapfirstChar(realName);
	}

	public static void main(String[] args) {
		int i = 0;
		
	}

	public static Object converPrimitiveOrString(Class objClass, String value) {
		
		if (int.class.equals(objClass)|| Integer.class.equals(objClass)) {
			return Integer.parseInt(value);
		}
		else if (boolean.class.equals(objClass)|| Boolean.class.equals(objClass)) {
			return Boolean.parseBoolean(value);
		}
		else if (float.class.equals(objClass)|| Float.class.equals(objClass)) {
			return Float.parseFloat(value);
		}
		else if (double.class.equals(objClass)|| Double.class.equals(objClass)) {
			return Double.parseDouble(value);
		}
		else if (long.class.equals(objClass)|| Long.class.equals(objClass)) {
			return Long.parseLong(value);
		}
		else if (short.class.equals(objClass)|| Short.class.equals(objClass)) {
			return Short.parseShort(value);
		}
		else if (char.class.equals(objClass)|| Character.class.equals(objClass)) {
			if (value.length()== 1) {
				return value.toCharArray()[0];
			}
		}
		return value;
	}
}
