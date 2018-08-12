package com.wiser.library.manager;

import android.app.Application;

import com.wiser.library.helper.WISERDisplay;

/**
 * @author Wiser 类管理器
 */
public class WISERManage {

	/**
	 * 日志管理类
	 */
	private WISERLogManage			logManger;

	/**
	 * Toast管理类
	 */
	private WISERToastMange			toastManger;

	/**
	 * 线程管理类
	 */
	private WISERHandlerExecutor	synchronousExecutor;

	/**
	 * Biz管理类
	 */
	private WISERBizManage			bizManage;

	/**
	 * Activity管理类
	 */
	private WISERActivityManage		activityManage;

	/**
	 * 
	 */
	private WISERDisplay			display;

	private Application				application;

	public WISERManage(Application application) {
		this.application = application;
		logManger = new WISERLogManage();
		toastManger = new WISERToastMange();
		synchronousExecutor = new WISERHandlerExecutor();
		bizManage = new WISERBizManage();
		activityManage = new WISERActivityManage();
		display = new WISERDisplay();
	}

	public Application getApplication() {
		return application;
	}

	public WISERLogManage getLogManger() {
		return logManger;
	}

	public WISERToastMange getToastManger() {
		return toastManger;
	}

	public WISERHandlerExecutor getSynchronousExecutor() {
		return synchronousExecutor;
	}

	public WISERBizManage getBizManage() {
		return bizManage;
	}

	public WISERActivityManage getActivityManage() {
		return activityManage;
	}

    public WISERDisplay getDisplay() {
		return display;
    }
}
