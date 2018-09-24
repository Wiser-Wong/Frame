package com.wiser.library.manager.downloader;

import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;

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

}
