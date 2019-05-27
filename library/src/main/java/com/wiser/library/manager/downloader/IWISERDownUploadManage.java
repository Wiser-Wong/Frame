package com.wiser.library.manager.downloader;

import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;

import okhttp3.OkHttpClient;

/**
 * @author Wiser
 * 
 *         下载上传管理接口
 */
public interface IWISERDownUploadManage {

	/**
	 * 初始化FileDownloader
	 * 
	 * @param context
	 */
	void initFileDownloader(Context context);

	/**
	 * 获取FileDownloader管理
	 * 
	 * @return
	 */
	FileDownloader fileDownloader();

	/**
	 * 自定义FileDownloader OkhttpConnection 设置自签证书可以使用
	 * 
	 * @param builder
	 */
	void initFileDownloaderForOkhttpConnection(OkHttpClient.Builder builder);

}
