package com.wiser.library.config.sharepre;

import com.wiser.library.helper.WISERHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author Wiser
 * 
 *         共享参数
 */
public abstract class WISERSharePreference {

	public abstract String SP_NAME();

	private final String		SP_NAME	= SP_NAME();

	private SharedPreferences	sp;

	/**
	 * 创建共享参数
	 *
	 * @return sp
	 */
	private SharedPreferences getPreferences() {

		if (sp == null) {
			sp = WISERHelper.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp;
	}

	/**
	 * 是否包含当前key
	 *
	 * @param key
	 * @return
	 */
	public boolean isContains(String key) {

		return getPreferences().contains(key);
	}

	/**
	 * 保存boolean类型
	 *
	 * @param key
	 * @param value
	 */
	public void saveBoolean(String key, boolean value) {

		getPreferences().edit().putBoolean(key, value).apply();

	}

	/**
	 * 保存String类型
	 *
	 * @param key
	 * @param value
	 */
	public void saveString(String key, String value) {

		getPreferences().edit().putString(key, value).apply();

	}

	/**
	 * 保存int整型
	 *
	 * @param key
	 * @param value
	 */
	public void saveInt(String key, int value) {

		getPreferences().edit().putInt(key, value).apply();

	}

	/**
	 * 保存float类型
	 *
	 * @param key
	 * @param value
	 */
	public void saveFload(String key, float value) {

		getPreferences().edit().putFloat(key, value).apply();

	}

	/**
	 * 保存long类型
	 *
	 * @param key
	 * @param value
	 */
	public void saveLong(String key, long value) {

		getPreferences().edit().putLong(key, value).apply();

	}

	/**
	 * 获得boolean值
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defValue) {

		return getPreferences().getBoolean(key, defValue);

	}

	/**
	 * 获得String值
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getString(String key, String defValue) {

		return getPreferences().getString(key, defValue);

	}

	/**
	 * 获得整型int值
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int getInt(String key, int defValue) {

		return getPreferences().getInt(key, defValue);

	}

	/**
	 * 获得浮点型float值
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public float getFloat(String key, float defValue) {

		return getPreferences().getFloat(key, defValue);

	}

	/**
	 * 获得长整型long值
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public long getLong(String key, long defValue) {

		return getPreferences().getLong(key, defValue);

	}

	/**
	 * 读取int
	 *
	 * @param context
	 * @param key
	 * @param defaultValue
	 */
	public int readIntPreferences(Context context, String key, int defaultValue) {
		if (context != null) {
			return getPreferences().getInt(key, defaultValue);
		} else {
			return 0;
		}
	}

	/**
	 * 写入Int
	 *
	 * @param key
	 * @param value
	 */
	public void writeIntPreferences(String key, int value) {
		Editor edit = getPreferences().edit();
		edit.putInt(key, value);
		edit.apply();
	}

	/**
	 * 清空数据
	 */
	public void clearAllPreferences() {
		getPreferences().edit().clear().apply();
	}

	/**
	 * 根据key删除
	 *
	 * @param context
	 * @param key
	 */
	public void removePreferences(Context context, String key) {
		if (context != null) {
			Editor edit = getPreferences().edit();
			edit.remove(key);
			edit.apply();
		}
	}
}
