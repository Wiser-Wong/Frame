package com.wiser.library.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.wiser.library.annotation.impl.Impl;
import com.wiser.library.annotation.inter.Interface;

import android.support.annotation.NonNull;

/**
 * @author Wiser
 * 
 *         类
 */
@SuppressWarnings("unchecked")
public class WISERClass {

	/**
	 * 获取泛型类型
	 *
	 * @param clazz
	 *            参数
	 * @param index
	 *            参数
	 * @return 返回值
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {
		Type type = clazz.getGenericSuperclass();

		if (!(type instanceof ParameterizedType)) {
			return null;
		}
		// 强制类型转换
		ParameterizedType pType = (ParameterizedType) type;

		Type[] tArgs = pType.getActualTypeArguments();

		if (tArgs.length < 1) {
			return null;
		}

		return (Class) tArgs[index];
	}

	/**
	 * 获取实现类
	 *
	 * @param service
	 * @param <D>
	 * @return
	 */
	public static <D> Object getImplClass(@NonNull Class<D> service) {
		WISERCheck.validateServiceInterface(service);
		try {
			// 获取注解
			Impl impl = service.getAnnotation(Impl.class);
			WISERCheck.checkNotNull(impl, "该接口没有指定实现类～");
			/** 加载类 **/
			Class clazz = Class.forName(impl.value().getName());
			Constructor c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			WISERCheck.checkNotNull(clazz, "业务类为空～");
			/** 创建类 **/
			return c.newInstance();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(service + "，没有找到业务类！" + e.getMessage());
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(service + "，实例化异常！" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(service + "，访问权限异常！" + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(service + "，没有找到构造方法！" + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(service + "，反射异常！" + e.getMessage());
		}
	}

	public static <D> Object getImplClassNotInf(@NonNull Class<D> service) {
		try {
			/** 加载类 **/
			Constructor<D> c = service.getDeclaredConstructor();
			c.setAccessible(true);
			WISERCheck.checkNotNull(service, "业务类为空～");
			/** 创建类 **/
			return c.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(service + "，实例化异常！" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(service + "，访问权限异常！" + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(service + "，没有找到构造方法！" + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(service + "，反射异常！" + e.getMessage());
		}
	}

	public static <D> Object getImplClass(@NonNull Class<D> service, Class argsClass, Object argsParam) {
		WISERCheck.validateServiceInterface(service);
		try {
			// 获取注解
			Impl impl = service.getAnnotation(Impl.class);
			WISERCheck.checkNotNull(impl, "该接口没有指定实现类～");
			/** 加载类 **/
			Class clazz = Class.forName(impl.value().getName());
			Constructor c = clazz.getConstructor(argsClass);
			c.setAccessible(true);
			WISERCheck.checkNotNull(clazz, "业务类为空～");
			/** 创建类 **/
			return c.newInstance(argsParam);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(service + "，没有找到业务类！" + e.getMessage());
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(service + "，实例化异常！" + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(service + "，访问权限异常！" + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(service + "，没有找到构造方法！" + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(service + "，反射异常！" + e.getMessage());
		}
	}

	/**
	 * 获取实现类
	 *
	 * @param <D>
	 * @param service
	 * @return
	 */
	public static <D> Class getInterfaceClass(@NonNull Class<D> service) {
		WISERCheck.validateServiceImlp(service);
		try {
			// 获取注解
			Interface inter = service.getAnnotation(Interface.class);
			if (inter == null) return null;
			/** 加载类 **/
			return Class.forName(inter.value().getName());
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(service + "，没有找到接口类！" + e.getMessage());
		}
	}

}
