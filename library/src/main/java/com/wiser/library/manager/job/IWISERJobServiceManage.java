package com.wiser.library.manager.job;

import android.app.job.JobInfo;

/**
 * @author Wiser
 * @see android.app.job.JobService
 */
public interface IWISERJobServiceManage {

	/**
	 * 开始调度
	 *
	 * @param builder
	 *            参数 setPersisted(true) 时 manifest注册
	 *            android.permission.RECEIVE_BOOT_COMPLETED
	 */
	void schedule(JobInfo.Builder builder);

	/**
	 * 取消指定任务
	 *
	 * @param id
	 *            参数
	 */
	void cancel(int id);

	/**
	 * 取消所有任务
	 */
	void cancelAll();

	/**
	 * 获取job
	 *
	 * @param id
	 *            工作标识
	 * @param clazz
	 *            类文件
	 * @return 返回值
	 */
	JobInfo.Builder builder(int id, Class clazz);

	/**
	 * 获取job
	 *
	 * @param id
	 *            工作标识
	 * @param clazz
	 *            类文件
	 * @param delay
	 *            设置JobService执行的最小延时时间
	 * @param deadline
	 *            设置JobService执行的最晚时间
	 * @param network
	 *            执行的网络状态
	 * @param isCharging
	 *            是否要设备为充电状态
	 * @return 返回值
	 */
	JobInfo.Builder builder(int id, Class clazz, long delay, long deadline, int network, boolean isCharging);
}
