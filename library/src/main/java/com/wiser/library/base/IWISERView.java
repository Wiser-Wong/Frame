package com.wiser.library.base;

import android.support.v4.app.Fragment;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERView {

	/**
	 * 显示正在加载view
	 */
	void showLoading();

	/**
	 * 显示请求错误布局
	 */
	void showErrorView();

	/**
	 * 显示空布局
	 */
	void showEmptyView();

	/**
	 * 显示主布局
	 */
	void showContentView();

	/**
	 * 刷新完成
	 */
	void refreshComplete();
}
