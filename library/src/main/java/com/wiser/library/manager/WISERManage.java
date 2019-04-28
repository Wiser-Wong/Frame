package com.wiser.library.manager;

import javax.inject.Inject;

import com.wiser.library.base.IWISERBind;
import com.wiser.library.helper.WISERDisplay;
import com.wiser.library.manager.activity.WISERActivityManage;
import com.wiser.library.manager.biz.WISERBizManage;
import com.wiser.library.manager.downloader.WISERDownUploadManage;
import com.wiser.library.manager.file.WISERFileCacheManage;
import com.wiser.library.manager.handler.WISERHandlerExecutor;
import com.wiser.library.manager.http.WISERHttpManage;
import com.wiser.library.manager.job.WISERJobServiceManage;
import com.wiser.library.manager.log.WISERLogManage;
import com.wiser.library.manager.method.WISERMethodManage;
import com.wiser.library.manager.permission.WISERPermissionManage;
import com.wiser.library.manager.thread.WISERThreadPoolManage;
import com.wiser.library.manager.toast.WISERToastManage;
import com.wiser.library.manager.ui.WISERUIManage;

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
	@Inject WISERLogManage			logManger;

	/**
	 * Toast管理类
	 */
	@Inject WISERToastManage		toastManger;

	/**
	 * 线程管理类
	 */
	@Inject WISERHandlerExecutor	handlerExecutor;

	/**
	 * Biz管理类
	 */
	@Inject WISERBizManage			bizManage;

	/**
	 * Activity管理类
	 */
	@Inject WISERActivityManage		activityManage;

	@Inject public WISERDisplay		display;

	/**
	 * 网络配置
	 */
	private Retrofit				retrofit;

	/**
	 * 网络管理
	 */
	@Inject WISERHttpManage			httpManage;

	/**
	 * 线程管理
	 */
	@Inject WISERThreadPoolManage	threadPoolManage;

	/**
	 * 权限管理
	 */
	@Inject WISERPermissionManage	permissionManage;

	/**
	 * 文件管理
	 */
	@Inject WISERFileCacheManage	fileCacheManage;

	/**
	 * jobService服务管理
	 */
	@Inject WISERJobServiceManage	jobServiceManage;

	/**
	 * 下载上传管理
	 */
	@Inject WISERDownUploadManage	downUploadManage;

	/**
	 * 方法代理
	 */
	@Inject WISERMethodManage		methodManage;

	@Inject WISERUIManage			uiManage;

	private Application				application;

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

	public WISERHandlerExecutor getHandlerExecutor() {
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

	public WISERFileCacheManage getFileCacheManage() {
		if (fileCacheManage == null) synchronized (WISERFileCacheManage.class) {
			if (fileCacheManage == null) fileCacheManage = new WISERFileCacheManage();
		}
		return fileCacheManage;
	}

	public WISERJobServiceManage getJobServiceManage() {
		if (jobServiceManage == null) synchronized (WISERJobServiceManage.class) {
			if (jobServiceManage == null) jobServiceManage = new WISERJobServiceManage();
		}
		return jobServiceManage;
	}

	public WISERDownUploadManage getDownUploadManage() {
		if (downUploadManage == null) synchronized (WISERDownUploadManage.class) {
			if (downUploadManage == null) downUploadManage = new WISERDownUploadManage();
		}
		return downUploadManage;
	}

	public WISERMethodManage getMethodManage() {
		if (methodManage == null) synchronized (WISERMethodManage.class) {
			if (methodManage == null) methodManage = new WISERMethodManage();
		}
		return methodManage;
	}

	public WISERUIManage uiManage() {
		if (uiManage == null) synchronized (WISERUIManage.class) {
			if (uiManage == null) uiManage = new WISERUIManage();
		}
		return uiManage;
	}

	public Retrofit getRetrofit() {
		return retrofit;
	}
}
