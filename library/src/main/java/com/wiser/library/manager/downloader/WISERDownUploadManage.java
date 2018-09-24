package com.wiser.library.manager.downloader;

import javax.inject.Inject;

import com.liulishuo.filedownloader.FileDownloader;

import android.content.Context;

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
}
