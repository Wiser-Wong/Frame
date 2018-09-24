package com.wiser.library.util;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonToken;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERGson {

	/**
	 * 构建Gson对象
	 * 
	 * @return
	 */
	public static Gson buildGson() {
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapter(Date.class, new TypeAdapter<Date>() {

			@Override public void write(com.google.gson.stream.JsonWriter writer, Date value) throws IOException {
				if (value == null) {
					writer.nullValue();
					return;
				}

				long num = value.getTime();
				num /= 1000;
				writer.value(num);
			}

			@Override public Date read(com.google.gson.stream.JsonReader reader) throws IOException {
				if (reader.peek() == JsonToken.NULL) {
					reader.nextNull();
					return null;
				}

				long value = reader.nextLong();
				return new Date(value * 1000);
			}

		});
		return b.create();
	}

	/**
	 * 根据 Json 获取 集合
	 * 
	 * @param json
	 * @param classT
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getDataList(String json, Class classT) {
		Gson gson = new Gson();
		return gson.fromJson(json, new ParameterizedTypeImpl(classT));
	}

	/**
	 * 根据json 获取 model
	 * 
	 * @param json
	 * @param classT
	 * @param <T>
	 * @return
	 */
	public static <T> T getData(String json, Class classT) {
		Gson gson = new Gson();
		return (T) gson.fromJson(json, classT);
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
