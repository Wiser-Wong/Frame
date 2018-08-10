package com.wiser.library.manager;

import android.app.Activity;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERActivityManage {

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    void addActivity(Activity activity);

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    void finishActivity(Activity activity);

    /**
     * 结束所有的Activity
     */
    void finishAllActivity();

    /**
     * 应用退出，结束所有的activity
     */
    void exitFinishAllActivity();

    /**
     * 结束指定类名的Activity
     */
    void finishActivityclass(Class<?> cls);

    /**
     * 结束除了某一个activity的所有activity
     *
     * @param cls
     */
    void finishAllActivityExceptClass(Class<?> cls);

}
