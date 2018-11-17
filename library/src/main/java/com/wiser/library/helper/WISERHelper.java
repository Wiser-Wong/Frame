package com.wiser.library.helper;

import java.io.IOException;

import com.wiser.library.base.IWISERBind;
import com.wiser.library.base.IWISERBiz;
import com.wiser.library.config.IWISERConfig;
import com.wiser.library.manager.WISERManage;
import com.wiser.library.manager.activity.IWISERActivityManage;
import com.wiser.library.manager.biz.IWISERBizManage;
import com.wiser.library.manager.downloader.WISERDownUploadManage;
import com.wiser.library.manager.file.WISERFileCacheManage;
import com.wiser.library.manager.handler.WISERHandlerExecutor;
import com.wiser.library.manager.job.WISERJobServiceManage;
import com.wiser.library.manager.log.WISERLogManage;
import com.wiser.library.manager.method.WISERMethodManage;
import com.wiser.library.manager.permission.IWISERPermissionManage;
import com.wiser.library.manager.thread.WISERThreadPoolManage;
import com.wiser.library.manager.toast.WISERToastManage;
import com.wiser.library.util.WISERCrashHandler;

import android.app.Application;
import android.os.Looper;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * @author Wiser 帮助类
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERHelper {

	private static WISERManage mWiserManage = null;

	/**
	 * @return 返回值
	 */
	public static Bind newBind() {
		return new Bind();
	}

	public static class Bind {

		IWISERBind iwiserBind;

		/**
		 * @param iwiserBind
		 *            参数
		 * @return 返回值
		 */
		public Bind setWiserBind(IWISERBind iwiserBind) {
			this.iwiserBind = iwiserBind;
			return this;
		}

		/**
		 * 注入架构
		 * 
		 * @param application
		 * @param IS_DEBUG
		 */
		public void Inject(Application application, boolean IS_DEBUG) {
			if (application == null) throw new RuntimeException("WISER架构:Application没有设置");

			IWISERConfig.IS_DEBUG = IS_DEBUG;
			if (this.iwiserBind == null) {
				this.iwiserBind = IWISERBind.IWISER_BIND;
			}
			mWiserManage = iwiserBind.getManage();
			if (mWiserManage == null) {
				throw new RuntimeException("WISER架构:WISERManage没有设置");
			}
			// 注入Dagger
			DaggerIWISERComponent.builder().build().inject(mWiserManage);
			mWiserManage.init(iwiserBind, application);
		}

	}

	/**
	 * 获取管理
	 *
	 * @param <M>
	 *            参数
	 * @return 返回值
	 */
	protected static <M> M getManage() {
		return (M) mWiserManage;
	}

	/**
	 * 捕获全局异常
	 * 
	 * @param application
	 * @param logFile
	 *            log名称或者log路径 取决于 isCustomPath
	 * @param isCustomPath
	 *            是否定制路径
	 * @param isOpen
	 *            是否打开
	 */
	public static void setCrashHandler(Application application, String logFile, boolean isCustomPath, boolean isOpen) {
		WISERCrashHandler.getInstance().init(application, logFile, isCustomPath, isOpen);
	}

	/**
	 * MainLooper 主线程中执行
	 *
	 * @return 返回值
	 */
	public static WISERHandlerExecutor mainLooper() {
		return mWiserManage.getHandlerExecutor();
	}

	public static Application getInstance() {
		if (mWiserManage.getApplication() == null) throw new RuntimeException("WISER架构:没有初始化，不能使用其功能。");
		return mWiserManage.getApplication();
	}

	/**
	 * Toast对象
	 *
	 * @return
	 */
	public static WISERToastManage toast() {
		return mWiserManage.getToastManger();
	}

	/**
	 * 日志对象
	 *
	 * @return
	 */
	public static WISERLogManage log() {
		return mWiserManage.getLogManger();
	}

	/**
	 * 获取Biz管理类对象
	 *
	 * @return
	 */
	public static IWISERBizManage getBizManage() {
		return mWiserManage.getBizManage();
	}

	/**
	 * 获取biz对象
	 *
	 * @param clazz
	 * @param <B>
	 * @return
	 */
	public static <B extends IWISERBiz> B biz(Class<B> clazz) {
		return getBizManage().biz(clazz);
	}

	/**
	 * 是否存在该biz对象
	 *
	 * @param clazz
	 * @param <B>
	 * @return
	 */
	public static <B extends IWISERBiz> boolean isExistBiz(Class<B> clazz) {
		return getBizManage().isExist(clazz);
	}

	/**
	 * 获取Activity管理类对象
	 *
	 * @return
	 */
	public static IWISERActivityManage getActivityManage() {
		return mWiserManage.getActivityManage();
	}

	/**
	 * 获取线程管理
	 * 
	 * @return
	 */
	public static WISERThreadPoolManage threadPoolManage() {
		return mWiserManage.getThreadPoolManage();
	}

	/**
	 * 获取权限管理
	 * 
	 * @return
	 */
	public static IWISERPermissionManage permissionManage() {
		return mWiserManage.getPermissionManage();
	}

	/**
	 * 获取文件管理
	 * 
	 * @return
	 */
	public static WISERFileCacheManage fileCacheManage() {
		return mWiserManage.getFileCacheManage();
	}

	/**
	 * 获取jobService服务管理
	 * 
	 * @return
	 */
	public static WISERJobServiceManage jobServiceManage() {
		return mWiserManage.getJobServiceManage();
	}

	/**
	 * 获取下载上传管理
	 * 
	 * @return
	 */
	public static WISERDownUploadManage downUploadManage() {
		return mWiserManage.getDownUploadManage();
	}

	/**
	 * 获取方法代理
	 * 
	 * @return
	 */
	public static WISERMethodManage methodManage() {
		return mWiserManage.getMethodManage();
	}

	/**
	 * @return
	 */
	public static IWISERDisplay display() {
		return mWiserManage.getDisplay();
	}

	public static <H> H http(Class<H> hClass) {
		return mWiserManage.getHttpManage().http(hClass);
	}

	/**
	 * 获取网络数据同步
	 *
	 * @param call
	 *            参数
	 * @param <D>
	 *            参数
	 * @return 返回值
	 */
	public static <D> D httpBody(Call<D> call) {
		if (call == null) {
			return null;
		}
		Call<D> wiserCall;
		if (call.isExecuted()) {
			wiserCall = call.clone();
		} else {
			wiserCall = call;
		}

		try {
			Response<D> response = wiserCall.execute();
			if (!response.isSuccessful()) {
				String stringBuilder = "code:" + response.code() + " " + "message:" + response.message() + " " + "errorBody:" + response.errorBody().string();
				throw new RuntimeException(stringBuilder);
			}

			return response.body();
		} catch (IOException e) {
			throw new RuntimeException("网络异常", e.getCause());
		}
	}

	/**
	 * 取消网络请求
	 *
	 * @param call
	 *            参数
	 */
	public static void httpCancel(Call call) {
		if (call == null) {
			return;
		}

		if (call.isExecuted()) {
			call.cancel();
		}
	}

	public static Retrofit retrofit() {
		return mWiserManage.getRetrofit();
	}

	/**
	 * 判断是否是主线程
	 *
	 * @return true 子线程 false 主线程
	 */
	public static boolean isMainLooperThread() {
		return Looper.getMainLooper().getThread() != Thread.currentThread();
	}

}
