package com.wiser.library.manager;

import javax.inject.Inject;

import com.wiser.library.base.IWISERBind;
import com.wiser.library.helper.WISERDisplay;

import android.app.Application;

import retrofit2.Retrofit;

/**
 * @author Wiser
 *
 *         类管理器
 */
public class WISERManage {

	/**
	 * 日志管理类
	 */
	@Inject public WISERLogManage			logManger;

	/**
	 * Toast管理类
	 */
	@Inject public WISERToastManage			toastManger;

	/**
	 * 线程管理类
	 */
	@Inject public WISERHandlerExecutor		handlerExecutor;

	/**
	 * Biz管理类
	 */
	@Inject public WISERBizManage			bizManage;

	/**
	 * Activity管理类
	 */
	@Inject public WISERActivityManage		activityManage;

	@Inject public WISERDisplay				display;

	/**
	 * 网络配置
	 */
	private Retrofit						retrofit;

	/**
	 * 网络管理
	 */
	@Inject public WISERHttpManage			httpManage;

	/**
	 * 线程管理
	 */
	@Inject public WISERThreadPoolManage	threadPoolManage;

	/**
	 * 权限管理
	 */
	@Inject public WISERPermissionManage	permissionManage;

	private Application						application;

	public WISERManage() {}

	public void init(IWISERBind iwiserBind, Application application) {
		this.application = application;
		retrofit = iwiserBind.getRetrofit(new Retrofit.Builder());
	}

	public Application getApplication() {
		return application;
	}

	public WISERLogManage getLogManger() {
		if (logManger == null) {
			synchronized (WISERLogManage.class) {
				if (logManger == null) {
					logManger = new WISERLogManage();
				}
			}
		}
		return logManger;
	}

	public WISERToastManage getToastManger() {
		if (toastManger == null) {
			synchronized (WISERToastManage.class) {
				if (toastManger == null) {
					toastManger = new WISERToastManage();
				}
			}
		}
		return toastManger;
	}

	public WISERHandlerExecutor getSynchronousExecutor() {
		if (handlerExecutor == null) {
			synchronized (WISERHandlerExecutor.class) {
				if (handlerExecutor == null) {
					handlerExecutor = new WISERHandlerExecutor();
				}
			}
		}
		return handlerExecutor;
	}

	public WISERBizManage getBizManage() {
		if (bizManage == null) {
			synchronized (WISERBizManage.class) {
				if (bizManage == null) {
					bizManage = new WISERBizManage();
				}
			}
		}
		return bizManage;
	}

	public WISERActivityManage getActivityManage() {
		if (activityManage == null) {
			synchronized (WISERActivityManage.class) {
				if (activityManage == null) {
					activityManage = new WISERActivityManage();
				}
			}
		}
		return activityManage;
	}

	public WISERDisplay getDisplay() {
		if (display == null) {
			synchronized (WISERDisplay.class) {
				if (display == null) {
					display = new WISERDisplay();
				}
			}
		}
		return display;
	}

	public WISERHttpManage getHttpManage() {
		if (httpManage == null) {
			synchronized (WISERHttpManage.class) {
				if (httpManage == null) {
					httpManage = new WISERHttpManage();
				}
			}
		}
		return httpManage;
	}

	public WISERThreadPoolManage getThreadPoolManage() {
		if (threadPoolManage == null) synchronized (WISERThreadPoolManage.class) {
			if (threadPoolManage == null) threadPoolManage = new WISERThreadPoolManage();
		}
		return threadPoolManage;
	}

	public WISERPermissionManage getPermissionManage() {
		if (permissionManage == null) synchronized (WISERPermissionManage.class) {
			if (permissionManage == null) permissionManage = new WISERPermissionManage();
		}
		return permissionManage;
	}

	public Retrofit getRetrofit() {
		return retrofit;
	}
}
