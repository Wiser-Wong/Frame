package com.wiser.library.helper;

import android.app.Application;
import android.os.Looper;

import com.wiser.library.base.IWISERBind;
import com.wiser.library.base.WISERBiz;
import com.wiser.library.config.IWISERConfig;
import com.wiser.library.manager.IWISERActivityManage;
import com.wiser.library.manager.IWISERBizManage;
import com.wiser.library.manager.WISERHandlerExecutor;
import com.wiser.library.manager.WISERLogManage;
import com.wiser.library.manager.WISERManage;
import com.wiser.library.manager.WISERThreadPoolManage;
import com.wiser.library.manager.WISERToastManage;

import java.io.IOException;

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
		 * @param application
		 * @param SWITCH
		 */
		public void Inject(Application application, boolean SWITCH) {
			if (application == null) throw new RuntimeException("WISER架构:Application没有设置");

			IWISERConfig.IS_DEBUG = SWITCH;
			if (this.iwiserBind == null) {
				this.iwiserBind = IWISERBind.IWISER_BIND;
			}
			mWiserManage = iwiserBind.getManage();
			if (mWiserManage == null) {
				throw new RuntimeException("WISER架构:WISERManage没有设置");
			}
			//注入Dagger
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
	 * MainLooper 主线程中执行
	 *
	 * @return 返回值
	 */
	public static WISERHandlerExecutor mainLooper() {
		return mWiserManage.getSynchronousExecutor();
	}

	public static Application getInstance() {
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
	public static <B extends WISERBiz> B biz(Class<B> clazz) {
		return getBizManage().biz(clazz);
	}

	/**
	 * 是否存在该biz对象
	 *
	 * @param clazz
	 * @param <B>
	 * @return
	 */
	public static <B extends WISERBiz> boolean isExistBiz(Class<B> clazz) {
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
		Call<D> skyCall;
		if (call.isExecuted()) {
			skyCall = call.clone();
		} else {
			skyCall = call;
		}

		try {
			Response<D> response = skyCall.execute();
			if (!response.isSuccessful()) {
				assert response.errorBody() != null;
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
