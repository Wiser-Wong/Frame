package com.wiser.library.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERGson {

	public static <T> List<T> getDataList(String json, Class classT) {
		Gson gson = new Gson();
		return gson.fromJson(json, new ParameterizedTypeImpl(classT));
	}

	public static Object getObject(String json, Class classT) {
		Gson gson = new Gson();
		return gson.fromJson(json, classT);
	}

	private static class ParameterizedTypeImpl implements ParameterizedType {

		Class clazz;

		public ParameterizedTypeImpl(Class clz) {
			clazz = clz;
		}

		@Override public Type[] getActualTypeArguments() {
			return new Type[] { clazz };
		}

		@Override public Type getRawType() {
			return List.class;
		}

		@Override public Type getOwnerType() {
			return null;
		}
	}
}
