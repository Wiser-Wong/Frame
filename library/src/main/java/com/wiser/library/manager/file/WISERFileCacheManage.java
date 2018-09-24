package com.wiser.library.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.reflect.TypeToken;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.manager.file.WISERFile;
import com.wiser.library.util.WISERGson;

import android.content.Context;
import android.os.Environment;

import javax.inject.Inject;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERFileCacheManage extends WISERFile {

	private final String	ENCODING	= "utf8";

	private final String	FILE_SUFFIX	= ".txt";

	public String			CACHE_PATH;

	@Inject public WISERFileCacheManage() {}

	/**
	 * 初始化文件 创建Android/data/包名/files 文件夹
	 * /storage/emulated/0/Android/data/com.wiser.frame/files
	 * 
	 * @param context
	 */
	public void initConfigureFile(Context context) {
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
			configureCustomerCache(filesDir);
		}
	}

	/**
	 * 初始化文件 创建Android/data/包名/cache 文件夹
	 * /storage/emulated/0/Android/data/com.wiser.frame/cache
	 *
	 * @param context
	 */
	public void initConfigureCache(Context context) {
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
			configureCustomerCache(filesDir);
		}
	}

	/**
	 * 初始化文件夹 创建 /storage/emulated/0文件夹 就是 我的文件/内部存储 的路径
	 * 
	 * @param folderName
	 *            文件夹名称
	 */
	public void initConfigureStorageDirectoryFolder(String folderName) {
		// 文件初始化
		File filesDir = null;
		if (isMounted()) {
			// We can read and write the media
			filesDir = Environment.getExternalStorageDirectory();
		}
		if (filesDir != null) {
			configureCustomerCache(filesDir.getAbsolutePath() + "/" + folderName);
		}
	}

	/**
	 * @param file
	 *            文件
	 */
	public void configureCustomerCache(File file) {
		CACHE_PATH = file.getAbsolutePath();
		if (!file.exists()) file.mkdirs();
	}

	/**
	 * @param filePath
	 *            文件路径
	 */
	public void configureCustomerCache(String filePath) {
		CACHE_PATH = filePath;
		File file = new File(CACHE_PATH);
		if (!file.exists()) file.mkdirs();
	}

	/**
	 * @param context
	 *            参数
	 */
	public void configurePhoneCache(Context context) {
		CACHE_PATH = context.getCacheDir().getAbsolutePath();
	}

	/**
	 * @param name
	 *            参数
	 * @return 返回值
	 */
	private String pathForCacheEntry(String name) {
		return CACHE_PATH + File.separator + name + FILE_SUFFIX;
	}

	/**
	 * @param name
	 *            参数
	 * @param fileSuffix
	 *            文件后缀
	 * @return 返回值
	 */
	private String pathForCacheEntry(String name, String fileSuffix) {
		return CACHE_PATH + File.separator + name + fileSuffix;
	}

	/**
	 * @param nameSuffix
	 *            参数
	 * @return 返回值
	 */
	private String cachePath(String nameSuffix) {
		return CACHE_PATH + File.separator + nameSuffix;
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
	 * @param fileName
	 *            the name of the file
	 * @return the content of the file, null if there is no such file
	 */
	public String readFile(String fileName) {
		try {
			return IOUtils.toString(new FileInputStream(pathForCacheEntry(fileName)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("read cache file failure" + e.toString());
			return null;
		}
	}

	/**
	 * @param fileName
	 *            the name of the file
	 * @param fileContent
	 *            the content of the file
	 */
	public void writeFile(String fileName, String fileContent) {
		try {
			IOUtils.write(fileContent, new FileOutputStream(pathForCacheEntry(fileName)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("write cache file failure" + e.toString());
		}
	}

	/**
	 * @param fileName
	 *            the name of the file
	 * @param fileSuffix
	 *            this suffix of the file
	 * @return the content of the file, null if there is no such file
	 */
	public String readFile(String fileName, String fileSuffix) {
		try {
			return IOUtils.toString(new FileInputStream(pathForCacheEntry(fileName, fileSuffix)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("read cache file failure" + e.toString());
			return null;
		}
	}

	/**
	 * @param fileName
	 *            the name of the file
	 * @param fileSuffix
	 *            this suffix of the file
	 * @param fileContent
	 *            the content of the file
	 */
	public void writeFile(String fileName, String fileSuffix, String fileContent) {
		try {
			IOUtils.write(fileContent, new FileOutputStream(pathForCacheEntry(fileName, fileSuffix)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("write cache file failure" + e.toString());
		}
	}

	/**
	 * @param fileNameSuffix
	 *            the name of the file
	 * @return the content of the file, null if there is no such file
	 */
	public String readFileContent(String fileNameSuffix) {
		try {
			return IOUtils.toString(new FileInputStream(cachePath(fileNameSuffix)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("read cache file failure" + e.toString());
			return null;
		}
	}

	/**
	 * @param fileNameSuffix
	 *            the name of the file
	 * @param fileContent
	 *            the content of the file
	 */
	public void writeFileContent(String fileNameSuffix, String fileContent) {
		try {
			IOUtils.write(fileContent, new FileOutputStream(cachePath(fileNameSuffix)), ENCODING);
		} catch (IOException e) {
			WISERHelper.log().e("write cache file failure" + e.toString());
		}
	}

	/**
	 * @param <T>
	 *            参数
	 * @param fileName
	 *            the name of the file
	 * @param dataMaps
	 *            the map list you want to store
	 */
	public <T> void writeDataMapsFile(String fileName, List<Map<String, T>> dataMaps) {
		writeFile(fileName, dataMapsToJson(dataMaps));
	}

	/**
	 * @param <T>
	 *            参数
	 * @param fileName
	 *            the name of the file
	 * @return the map list you previous stored, an empty {@link List} will be
	 *         returned if there is no such file
	 */
	public <T> List<Map<String, T>> readDataMapsFile(String fileName) {
		return dataMapsFromJson(readFile(fileName));
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
	 * @param fileName
	 *            the name of the file
	 * @param object
	 *            the object you want to store
	 * @param <T>
	 *            a class extends from {@link Object}
	 */
	public <T> void writeObjectFile(String fileName, T object) {
		writeFile(fileName, objectToJson(object));
	}

	/**
	 * @param fileName
	 *            the name of the file
	 * @param type
	 *            参数
	 * @param <T>
	 *            参数
	 * @return the {@link T} type object you previous stored
	 */
	public <T> T readObjectFile(String fileName, Type type) {
		return objectFromJson(readFile(fileName), type);
	}

	/**
	 * @param fileName
	 *            参数
	 * @param clazz
	 *            参数
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	public <T> T readObjectFile(String fileName, Class<T> clazz) {
		return objectFromJson(readFile(fileName), clazz);
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
	 * @param fileName
	 *            the name of the file
	 * @param dataMap
	 *            the map data you want to store
	 * @param <T>
	 *            参数
	 */
	public <T> void writeDataMapFile(String fileName, Map<String, T> dataMap) {
		writeFile(fileName, dataMaptoJson(dataMap));
	}

	/**
	 * @param fileName
	 *            the name of the file
	 * @param <T>
	 *            参数
	 * @return the map data you previous stored
	 */
	public <T> Map<String, T> readDataMapFile(String fileName) {
		return dataMapFromJson(readFile(fileName));
	}

	/**
	 * delete the file with fileName
	 *
	 * @param fileName
	 *            the name of the file
	 */
	public void deleteFile(String fileName) {
		try {
			FileUtils.forceDelete(new File(pathForCacheEntry(fileName)));
		} catch (IOException e) {
			WISERHelper.log().e("not delete " + e.toString());
		}
	}

	/**
	 * check if there is a cache file with fileName
	 *
	 * @param fileName
	 *            the name of the file
	 * @return true if the file exits, false otherwise
	 */
	public boolean hasCache(String fileName) {
		return new File(pathForCacheEntry(fileName)).exists();
	}
}
