package com.wiser.library.manager.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.reflect.TypeToken;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERCheck;
import com.wiser.library.util.WISERGson;

import android.content.Context;
import android.os.Environment;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERFileCacheManage extends WISERFile {

	private final String ENCODING = "utf8";

	@Inject public WISERFileCacheManage() {}

	/**
	 * @param path
	 */
	public void initConfigureFile(String path) {
		if (!WISERCheck.isEmpty(path)) {
			createFolder(path);
		}
	}

	/**
	 * @param path
	 */
	public void initConfigureCache(String path) {
		if (!WISERCheck.isEmpty(path)) {
			createFolder(path);
		}
	}

	/**
	 * @param path
	 * @param folderName
	 */
	public void initStorageDirectoryFolder(String path, String folderName) {
		if (!WISERCheck.isEmpty(path)) {
			createFolder(path + File.separator + folderName);
		}
	}

	/**
	 * 获取/storage/emulated/0/Android/data/com.wiser.frame/files路径
	 * 
	 * @param context
	 * @return
	 */
	public String configureFileDir(Context context) {
		// 文件初始化
		File filesDir;
		if (isMounted()) {
			// We can read and write the media
			filesDir = context.getExternalFilesDir(null);
		} else {
			// Load another directory, probably local memory
			filesDir = context.getFilesDir();
		}
		if (filesDir != null) {
			return filesDir.getAbsolutePath();
		}
		return null;
	}

	/**
	 * 获取/storage/emulated/0/Android/data/com.wiser.frame/cache路径
	 *
	 * @param context
	 * @return
	 */
	public String configureCacheDir(Context context) {
		// 文件初始化
		File filesDir;
		if (isMounted()) {
			// We can read and write the media
			filesDir = context.getExternalCacheDir();
		} else {
			// Load another directory, probably local memory
			filesDir = context.getCacheDir();
		}
		if (filesDir != null) {
			return filesDir.getAbsolutePath();
		}
		return null;
	}

	/**
	 * 获取/storage/emulated/0文件夹 就是 我的文件/内部存储 的路径
	 *
	 * @return
	 */
	public String configureStorageDir() {
		// 文件初始化
		File filesDir = null;
		if (isMounted()) {
			// We can read and write the media
			filesDir = Environment.getExternalStorageDirectory();
		}
		if (filesDir != null) {
			return filesDir.getAbsolutePath();
		}
		return null;
	}

	/**
	 * @param filePath
	 * @param nameSuffix
	 *            参数
	 * @return 返回值
	 */
	private String pathForCacheEntry(String filePath, String nameSuffix) {
		return filePath + File.separator + nameSuffix;
	}

	/**
	 * @param dataString
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	private <T> List<Map<String, T>> dataMapsFromJson(String dataString) {
		if (StringUtils.isEmpty(dataString)) return new ArrayList<>();

		try {
			Type listType = new TypeToken<List<Map<String, T>>>() {}.getType();
			return WISERGson.buildGson().fromJson(dataString, listType);
		} catch (Exception e) {
			WISERHelper.log().e("failed to read json" + e.toString());
			return new ArrayList<>();
		}
	}

	/**
	 * @param dataMaps
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	private <T> String dataMapsToJson(List<Map<String, T>> dataMaps) {
		try {
			return WISERGson.buildGson().toJson(dataMaps);
		} catch (Exception e) {
			WISERHelper.log().e("failed to write json" + e.toString());
			return "[]";
		}
	}

	/**
	 * @param filePath
	 * @param nameSuffix
	 *            the nameSuffix of the file
	 * @return the content of the file, null if there is no such file
	 */
	public String readFile(String filePath, String nameSuffix) {
		try {
			return IOUtils.toString(new FileInputStream(pathForCacheEntry(filePath, nameSuffix)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("read cache file failure" + e.toString());
			return null;
		}
	}

	/**
	 * @param filePath
	 * @param fileName
	 *            the name of the file
	 * @param fileContent
	 *            the content of the file
	 */
	public void writeFile(String filePath, String fileName, String fileContent) {
		try {
			IOUtils.write(fileContent, new FileOutputStream(pathForCacheEntry(filePath, fileName)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("write cache file failure" + e.toString());
		}
	}

	/**
	 * @param <T>
	 *            参数
	 * @param filePath
	 * @param fileName
	 *            the name of the file
	 * @param dataMaps
	 *            the map list you want to store
	 */
	public <T> void writeDataMapsFile(String filePath, String fileName, List<Map<String, T>> dataMaps) {
		writeFile(filePath, fileName, dataMapsToJson(dataMaps));
	}

	/**
	 * @param <T>
	 * @param filePath
	 *            参数
	 * @param fileName
	 *            the name of the file
	 * @return the map list you previous stored, an empty {@link List} will be
	 *         returned if there is no such file
	 */
	public <T> List<Map<String, T>> readDataMapsFile(String filePath, String fileName) {
		return dataMapsFromJson(readFile(filePath, fileName));
	}

	/**
	 * @param <T>
	 *            参数
	 * @param dataString
	 *            参数
	 * @param t
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	private <T> T objectFromJson(String dataString, Type t) {
		try {
			return WISERGson.buildGson().fromJson(dataString, t);
		} catch (Exception e) {
			WISERHelper.log().e("failed to read json" + e.toString());
			return null;
		}
	}

	/**
	 * @param dataString
	 *            参数
	 * @param t
	 *            番薯
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	private <T> T objectFromJson(String dataString, Class<T> t) {
		try {
			return WISERGson.buildGson().fromJson(dataString, t);
		} catch (Exception e) {
			WISERHelper.log().e("failed to read json" + e.toString());
			return null;
		}
	}

	/**
	 * @param o
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	private <T> String objectToJson(T o) {
		try {
			return WISERGson.buildGson().toJson(o);
		} catch (Exception e) {
			WISERHelper.log().e("failed to write json" + e.toString());
			return null;
		}
	}

	/**
	 * @param filePath
	 * @param fileName
	 *            the name of the file
	 * @param object
	 *            the object you want to store
	 * @param <T>
	 *            a class extends from {@link Object}
	 */
	public <T> void writeObjectFile(String filePath, String fileName, T object) {
		writeFile(filePath, fileName, objectToJson(object));
	}

	/**
	 * @param filePath
	 * @param fileName
	 *            the name of the file
	 * @param type
	 *            参数
	 * @param <T>
	 *            参数
	 * @return the {@link T} type object you previous stored
	 */
	public <T> T readObjectFile(String filePath, String fileName, Type type) {
		return objectFromJson(readFile(filePath, fileName), type);
	}

	/**
	 * @param filePath
	 * @param fileName
	 *            参数
	 * @param clazz
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	public <T> T readObjectFile(String filePath, String fileName, Class<T> clazz) {
		return objectFromJson(readFile(filePath, fileName), clazz);
	}

	/**
	 * @param dataString
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	private <T> Map<String, T> dataMapFromJson(String dataString) {
		if (StringUtils.isEmpty(dataString)) return new HashMap<String, T>();

		try {
			Type t = new TypeToken<Map<String, T>>() {}.getType();
			return WISERGson.buildGson().fromJson(dataString, t);
		} catch (Exception e) {
			WISERHelper.log().e("failed to read json" + e.toString());
			return new HashMap<>();
		}
	}

	/**
	 * @param dataMap
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	private <T> String dataMaptoJson(Map<String, T> dataMap) {
		try {
			return WISERGson.buildGson().toJson(dataMap);
		} catch (Exception e) {
			WISERHelper.log().e("failed to write json" + e.toString());
			return "{}";
		}
	}

	/**
	 * @param filePath
	 * @param fileName
	 *            the name of the file
	 * @param dataMap
	 *            the map data you want to store
	 * @param <T>
	 *            参数
	 */
	public <T> void writeDataMapFile(String filePath, String fileName, Map<String, T> dataMap) {
		writeFile(filePath, fileName, dataMaptoJson(dataMap));
	}

	/**
	 * @param fileName
	 *            the name of the file
	 * @param <T>
	 *            参数
	 * @retur * @param filePath n the map data you previous stored
	 */
	public <T> Map<String, T> readDataMapFile(String filePath, String fileName) {
		return dataMapFromJson(readFile(filePath, fileName));
	}

	/**
	 * delete the file with fileName
	 *
	 * @param filePath
	 * @param fileName
	 *            the name of the file
	 */
	public void deleteFile(String filePath, String fileName) {
		try {
			FileUtils.forceDelete(new File(pathForCacheEntry(filePath, fileName)));
		} catch (IOException e) {
			WISERHelper.log().e("not delete " + e.toString());
		}
	}

	/**
	 * check if there is a cache file with fileName
	 *
	 * @param filePath
	 * @param fileName
	 *            the name of the file
	 * @return true if the file exits, false otherwise
	 */
	public boolean hasCache(String filePath, String fileName) {
		return new File(pathForCacheEntry(filePath, fileName)).exists();
	}
}
