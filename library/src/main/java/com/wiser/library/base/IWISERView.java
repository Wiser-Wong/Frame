package com.wiser.library.base;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERView {

	int	STATE_CONTENT		= 1;

	int	STATE_LOADING		= 2;

	int	STATE_EMPTY			= 3;

	int	STATE_HTTP_ERROR	= 4;

	/**
	 * 显示正在加载view
	 */
	void showLoading();

	/**
	 * 隐藏正在加载View
	 */
	void hideLoading();

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
}
