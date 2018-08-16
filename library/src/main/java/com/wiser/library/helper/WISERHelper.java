package com.wiser.library.helper;

import android.app.Application;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.config.IWISERConfig;
import com.wiser.library.manager.IWISERActivityManage;
import com.wiser.library.manager.IWISERBizManage;
import com.wiser.library.manager.WISERHandlerExecutor;
import com.wiser.library.manager.WISERLogManage;
import com.wiser.library.manager.WISERManage;
import com.wiser.library.manager.WISERToastMange;

/**
 * @author Wiser 帮助类
 */
public class WISERHelper {

	private static WISERManage mWiserManage = null;

	/**
	 * @return 返回值
	 */
	public static Bind newBind() {
		return new Bind();
	}

	public static class Bind {

		public void Inject(Application application, boolean SWITCH) {
			if (application == null) throw new RuntimeException("SKYLINE架构:Application没有设置");

			IWISERConfig.IS_DEBUG = SWITCH;
			mWiserManage = new WISERManage(application);
		}

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
	public static WISERToastMange toast() {
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
	 * @return
	 */
	public static IWISERDisplay display() {
		return mWiserManage.getDisplay();
	}

}
