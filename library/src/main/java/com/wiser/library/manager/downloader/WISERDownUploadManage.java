package com.wiser.library.manager.downloader;

import javax.inject.Inject;

import com.liulishuo.filedownloader.FileDownloader;
import com.wiser.library.helper.WISERHelper;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * @author Wiser
 * 
 *         文件下载器
 */
public class WISERDownUploadManage implements IWISERDownUploadManage {

	@Inject public WISERDownUploadManage() {}

	/**
	 * 初始化FileDownloader
	 *
	 * @param context
	 */
	@Override public void initFileDownloader(Context context) {
		FileDownloader.setup(context);
	}

	/**
	 * 获取FileDownloader管理
	 *
	 * @return
	 */
	@Override public FileDownloader fileDownloader() {
		return FileDownloader.getImpl();
	}

	/**
	 * 为FileDownloader 设置OKHTTP
	 * 
	 * @param builder
	 */
	@Override public void initFileDownloaderForOkhttpConnection(OkHttpClient.Builder builder) {
		FileDownloader.setupOnApplicationOnCreate(WISERHelper.getInstance()).connectionCreator(new FileDownloaderOkHttpConnection.Creator(builder)).commit();
	}
}
