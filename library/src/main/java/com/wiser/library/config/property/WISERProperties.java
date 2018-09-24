package com.wiser.library.config.property;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.wiser.library.annotation.Property;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERCheck;

/**
 * @author Wiser
 * @version 版本
 */
public abstract class WISERProperties {

	/**
	 * 从那里打开文件*
	 *
	 * @return 返回值
	 */
	public abstract int initType();

	/**
	 * 获取文件路径 *
	 */
	private File						propertyFilePath;

	/**
	 * 编码格式
	 */
	private static final String			DEFAULT_CODE				= "utf-8";

	private static final String			DEFAULT_ANNOTATION_VALUE	= "";

	/**
	 * 默认文件名 *
	 */
	private String						mPropertiesFileName;

	/**
	 * 默认文件后缀名 *
	 */
	private static final String			EXTENSION					= ".properties";

	/**
	 * 配置文件工具类 *
	 */
	private final java.util.Properties	mProperties					= new java.util.Properties();

	/**
	 * 类型 *
	 */
	public static final int				OPEN_TYPE_ASSETS			= 1;							// 打开asset文件夹下的文件

	public static final int				OPEN_TYPE_DATA				= 2;							// 打开应用程序data文件夹下的文件

	/**
	 * 成功回调
	 */
	private WISERPropertyCallback		propertyCallback;

	private Context						context;

	private WISERProperties() {}

	/**
	 * 构造函数
	 *
	 * @param context
	 *            参数
	 */
	public WISERProperties(@NonNull Context context) {
		this(context, "config");
	}

	public WISERProperties(@NonNull Context context, @NonNull String propertiesFileName) {
		this.context = context;
		propertyFilePath = getPropertyFilePath();
		mPropertiesFileName = propertiesFileName;
		switch (initType()) {
			case OPEN_TYPE_ASSETS:
				Resources mResources = context.getResources();
				openAssetProperties(mResources);
				break;
			case OPEN_TYPE_DATA:
				openDataProperties();
				break;
		}
	}

	/**
	 * 获取存储路径
	 * 
	 * @return 返回值
	 */
	public File getPropertyFilePath() {
		return context.getFilesDir();// 存储到/DATA/DATA/
	}

	/**
	 * asset文件夹下的文件 *
	 * 
	 * @param resources
	 *            参数
	 */
	private void openAssetProperties(Resources resources) {
		synchronized (mProperties) {
			try {
				InputStream inputStream = resources.getAssets().open(mPropertiesFileName + EXTENSION);
				WISERHelper.log().i("openProperties() 路径:" + mPropertiesFileName + EXTENSION);
				mProperties.load(inputStream);
				loadPropertiesValues();
			} catch (IOException e) {
				WISERHelper.log().e("openAssetProperties失败:" + e.toString());
			}
		}
	}

	/**
	 * data文件夹下的文件 *
	 */
	private void openDataProperties() {
		synchronized (mProperties) {
			InputStream in = null;
			try {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(mPropertiesFileName);
				stringBuilder.append(EXTENSION);
				File file = new File(propertyFilePath, stringBuilder.toString());
				/** 处理配置文件的变化 **/
				if (!file.exists()) {
					WISERHelper.log().i("SKYProperties create file ");
				}
				in = new BufferedInputStream(new FileInputStream(file));
				WISERHelper.log().i("openDataProperties() 路径:" + propertyFilePath + "/" + stringBuilder.toString());
				mProperties.load(in);
			} catch (Exception e) {
				WISERHelper.log().e("openDataProperties失败:" + e.toString());
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException ex) {
						WISERHelper.log().e("" + ex);
					}
				}
			}

			loadPropertiesValues();
		}
	}

	public void setPropertyCallback(WISERPropertyCallback propertyCallback) {
		this.propertyCallback = propertyCallback;
	}

	/**
	 * 返回类型 int
	 * 
	 * @param key
	 *            参数
	 * @return 返回值
	 */
	private int getInt(String key) {
		String value;
		try {
			value = mProperties.getProperty(key);
			if (WISERCheck.isEmpty(value)) {
				return 0;
			}
			return Integer.parseInt(mProperties.getProperty(key));
		} catch (Exception e) {
			WISERHelper.log().e("解析失败Int:" + e.toString());
			return 0;
		}
	}

	/**
	 * 返回类型 long
	 *
	 * @param key
	 *            参数
	 * @return 返回值
	 */
	private long getLong(String key) {
		String value;
		try {
			value = mProperties.getProperty(key);
			if (WISERCheck.isEmpty(value)) {
				return 0;
			}
			return Long.parseLong(mProperties.getProperty(key));
		} catch (Exception e) {
			WISERHelper.log().e("解析失败Long:" + e.toString());
			return (long) 0;
		}
	}

	/**
	 * 返回类型 float
	 *
	 * @param key
	 *            参数
	 * @return 返回值
	 */
	private float getFloat(String key) {
		String value;
		try {
			value = mProperties.getProperty(key);
			if (WISERCheck.isEmpty(value)) {
				return 0;
			}
			return Float.parseFloat(mProperties.getProperty(key));
		} catch (Exception e) {
			WISERHelper.log().e("解析失败Float:" + e.toString());
			return (float) 0;
		}
	}

	/**
	 * 返回类型 double
	 *
	 * @param key
	 *            参数
	 * @return 返回值
	 */
	private double getDouble(String key) {
		String value;
		try {
			value = mProperties.getProperty(key);
			if (WISERCheck.isEmpty(value)) {
				return 0;
			}
			return Double.parseDouble(mProperties.getProperty(key));
		} catch (Exception e) {
			WISERHelper.log().e("解析失败Double:" + e.toString());
			return (double) 0;
		}
	}

	/**
	 * 返回类型 boolean
	 *
	 * @param key
	 *            参数
	 * @return 返回值
	 */
	private boolean getBoolean(String key, boolean defaultValue) {
		String value = null;
		try {
			value = mProperties.getProperty(key);
			if (WISERCheck.isEmpty(value)) {
				return false;
			}
			return Boolean.parseBoolean(mProperties.getProperty(key));
		} catch (Exception e) {
			WISERHelper.log().e("解析失败Boolean:" + e.toString());
			return defaultValue;
		}
	}

	/**
	 * 返回类型 string
	 *
	 * @param key
	 *            参数
	 * @return 返回值
	 */
	private String getString(String key) {
		String result = null;

		switch (initType()) {
			case OPEN_TYPE_ASSETS:
				try {
					result = new String(mProperties.getProperty(key, "").getBytes("ISO-8859-1"), DEFAULT_CODE);
				} catch (UnsupportedEncodingException e) {
					WISERHelper.log().e("解析失败String:" + e.toString());
					return "";
				}
				break;
			case OPEN_TYPE_DATA:
				result = mProperties.getProperty(key, "");
				break;
		}

		return result;
	}

	/**
	 * 解析文件 - 赋值 * 获取所有的属性名 并赋值
	 */
	private void loadPropertiesValues() {
		synchronized (mProperties) {
			WISERHelper.log().i("loadPropertiesValues()-加载所有的值");
			Class<? extends WISERProperties> thisClass = this.getClass();
			Field[] fields = thisClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Property.class)) {
					field.setAccessible(true);
					String fieldName = field.getName();
					Property annotation = field.getAnnotation(Property.class);

					if (annotation.value().equals(DEFAULT_ANNOTATION_VALUE)) {
						setFieldValue(field, fieldName);
					} else {
						setFieldValue(field, annotation.value());
					}
				}
			}
		}
	}

	/**
	 * 所有属性写入到properties里
	 */
	private void writePropertiesValues() {
		synchronized (mProperties) {
			WISERHelper.log().i("writePropertiesValues()-写入所有的值");
			Class<? extends WISERProperties> thisClass = this.getClass();
			Field[] fields = thisClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Property.class)) {
					field.setAccessible(true);
					String fieldName = field.getName();
					Property annotation = field.getAnnotation(Property.class);
					if (annotation.value().equals(DEFAULT_ANNOTATION_VALUE)) {
						try {
							mProperties.put(fieldName, field.get(this) == null ? "" : String.valueOf(field.get(this)));
						} catch (IllegalAccessException e) {
							WISERHelper.log().e("Properties写入错误:" + e.toString());
						}
					} else {
						try {

							mProperties.put(annotation.value(), field.get(this) == null ? "" : String.valueOf(field.get(this)));
						} catch (IllegalAccessException e) {
							WISERHelper.log().e("Properties写入错误:" + e.toString());
						}
					}
				}
			}
		}
	}

	/**
	 * 所有属性写入到properties里
	 */
	private void writeDefaultPropertiesValues() {
		synchronized (mProperties) {
			WISERHelper.log().i("writePropertiesValues()-写入所有的值");
			Class<? extends WISERProperties> thisClass = this.getClass();
			Field[] fields = thisClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(Property.class)) {
					field.setAccessible(true);
					String fieldName = field.getName();
					Property annotation = field.getAnnotation(Property.class);
					if (annotation.value().equals(DEFAULT_ANNOTATION_VALUE)) {
						try {
							mProperties.put(fieldName, "");
							setFieldDefaultValue(field, fieldName);
						} catch (Exception e) {
							WISERHelper.log().e("Properties写入错误:" + e.toString());
						}
					} else {
						try {
							mProperties.put(annotation.value(), "");
							setFieldDefaultValue(field, annotation.value());
						} catch (Exception e) {
							WISERHelper.log().e("Properties写入错误:" + e.toString());
						}
					}
				}
			}
		}
	}

	/**
	 * 设置属性值 *
	 * 
	 * @param field
	 *            参数
	 * @param propertiesName
	 *            参数
	 */
	private void setFieldValue(Field field, String propertiesName) {
		Object value = getPropertyValue(field.getType(), propertiesName);
		if (value == null) {
			return;
		}
		try {
			field.set(this, value);
		} catch (Exception e) {
			WISERHelper.log().e("setFieldValue失败-->> fieldName:" + field.getName() + "--->>propertiesName:--->>" + propertiesName);
		}
	}

	/**
	 * 设置属性值 *
	 * 
	 * @param field
	 *            参数
	 * @param propertiesName
	 *            参数
	 */
	private void setFieldDefaultValue(Field field, String propertiesName) {
		Object value = getPropertyDefaultValue(field.getType());
		if (value == null) {
			return;
		}
		try {
			field.set(this, value);
		} catch (Exception e) {
			WISERHelper.log().e("setFieldValue失败-->> fieldName:" + field.getName() + "--->>propertiesName:--->>" + propertiesName);
		}
	}

	/**
	 * 获取类型 *
	 * 
	 * @param clazz
	 *            参数
	 * @param key
	 *            参数
	 * @return 返回值
	 */
	private Object getPropertyValue(Class<?> clazz, String key) {
		if (clazz == String.class) {
			return getString(key);
		} else if (clazz == float.class || clazz == Float.class) {
			return getFloat(key);
		} else if (clazz == double.class || clazz == Double.class) {
			return getDouble(key);
		} else if (clazz == boolean.class || clazz == Boolean.class) {
			return getBoolean(key, false);
		} else if (clazz == int.class || clazz == Integer.class) {
			return getInt(key);
		} else if (clazz == long.class || clazz == Long.class) {
			return getLong(key);
		} else {
			return null;
		}
	}

	/**
	 * 获取类型 *
	 * 
	 * @param clazz
	 *            参数
	 * @return 返回值
	 */
	private Object getPropertyDefaultValue(Class<?> clazz) {
		if (clazz == String.class) {
			return "";
		} else if (clazz == float.class || clazz == Float.class) {
			return 0;
		} else if (clazz == double.class || clazz == Double.class) {
			return 0;
		} else if (clazz == boolean.class || clazz == Boolean.class) {
			return false;
		} else if (clazz == int.class || clazz == Integer.class) {
			return 0;
		} else if (clazz == long.class || clazz == Long.class) {
			return 0;
		} else {
			return null;
		}
	}

	/**
	 * 提交
	 */
	public void commit() {
		synchronized (mProperties) {
			OutputStream out = null;
			try {
				String stringBuilder = mPropertiesFileName + EXTENSION;
				File file = new File(propertyFilePath, stringBuilder);
				if (!file.exists()) {
					file.createNewFile();
				}
				synchronized (mProperties) {
					out = new BufferedOutputStream(new FileOutputStream(file));
					writePropertiesValues();
					mProperties.store(out, "");
				}
				if (propertyCallback != null) {
					propertyCallback.onPropertySuccess();
				}
			} catch (FileNotFoundException ex) {
				WISERHelper.log().e(ex + "");
			} catch (IOException ex) {
				WISERHelper.log().e(ex + "");
			} finally {
				if (null != out) {
					try {
						out.close();
					} catch (IOException ex) {
						WISERHelper.log().e(ex + "");
					}
				}
			}
		}
	}

	/**
	 * 提交
	 * 
	 * @param callback
	 *            参数
	 */
	public void commit(WISERPropertyCallback callback) {
		synchronized (mProperties) {
			OutputStream out = null;
			try {
				String stringBuilder = mPropertiesFileName + EXTENSION;
				File file = new File(propertyFilePath, stringBuilder);
				if (!file.exists()) {
					file.createNewFile();
				}
				synchronized (mProperties) {
					out = new BufferedOutputStream(new FileOutputStream(file));
					writePropertiesValues();
					mProperties.store(out, "");
				}
				if (callback != null) {
					callback.onPropertySuccess();
				}

			} catch (FileNotFoundException ex) {
				WISERHelper.log().e(ex + "");
			} catch (IOException ex) {
				WISERHelper.log().e(ex + "");
			} finally {
				if (null != out) {
					try {
						out.close();
					} catch (IOException ex) {
						WISERHelper.log().e(ex + "");
					}
				}
			}
		}
	}

	/**
	 * 删除
	 */
	public void delete() {
		synchronized (mProperties) {
			String stringBuilder = mPropertiesFileName + EXTENSION;
			File file = new File(propertyFilePath, stringBuilder);
			if (!file.exists()) {
				return;
			}
			// 删除
			file.delete();
		}
	}

	/**
	 * 清空文件内容
	 */
	public void clear() {
		synchronized (mProperties) {
			OutputStream out = null;
			try {
				String stringBuilder = mPropertiesFileName + EXTENSION;
				File file = new File(propertyFilePath, stringBuilder);
				if (!file.exists()) {
					return;
				}
				synchronized (mProperties) {
					out = new BufferedOutputStream(new FileOutputStream(file));
					writeDefaultPropertiesValues();
					mProperties.store(out, "");
				}
			} catch (FileNotFoundException ex) {
				WISERHelper.log().e(ex + "");
			} catch (IOException ex) {
				WISERHelper.log().e(ex + "");
			} finally {
				if (null != out) {
					try {
						out.close();
					} catch (IOException ex) {
						WISERHelper.log().e(ex + "");
					}
				}
			}
		}
	}
}
