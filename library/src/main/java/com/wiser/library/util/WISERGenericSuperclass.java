package com.wiser.library.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERGenericSuperclass {

	/**
	 * 获取泛型类Class对象，不是泛型类则返回null
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?> getActualTypeArgument(Class<?> clazz) {
		Class<?> entityClass = null;
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
			if (actualTypeArguments != null && actualTypeArguments.length > 0) {
				entityClass = (Class<?>) actualTypeArguments[0];
			}
		}

		return entityClass;
	}
}
