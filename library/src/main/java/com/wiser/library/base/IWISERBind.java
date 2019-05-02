package com.wiser.library.base;

import com.wiser.library.manager.WISERManage;

import retrofit2.Retrofit;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERBind {

	/**
	 * 获取网络适配器
	 *
	 * @param builder
	 *            参数
	 * @return 返回值 返回值
	 */
	Retrofit getRetrofit(Retrofit.Builder builder);

	/**
	 * 获取配置管理器
	 *
	 * @return 返回值
	 */
	WISERManage getManage();

	/**
	 * 默认方法
	 */
	IWISERBind IWISER_BIND = new IWISERBind() {

		/**
		 * @param builder
		 *            参数
		 * @return 返回值
		 */
		@Override public Retrofit getRetrofit(Retrofit.Builder builder) {
			builder.baseUrl("http://www.wiser.com");
			return builder.build();
		}

		/**
		 * @return 返回值
		 */
		@Override public WISERManage getManage() {
			return new WISERManage();
		}

	};
}
