package com.wiser.library.base;

import android.content.Context;

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
     * 隐藏正在加载View
     */
    void hideLoading();

    /**
     * 显示请求错误提示
     */
    void showErr();

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Context getContext();

}
