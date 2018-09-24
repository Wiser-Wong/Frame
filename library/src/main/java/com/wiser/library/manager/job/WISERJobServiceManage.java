package com.wiser.library.manager.job;

import javax.inject.Inject;

import com.wiser.library.helper.WISERHelper;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author Wiser
 * @see android.app.job.JobService
 */
public class WISERJobServiceManage implements IWISERJobServiceManage {

	@Inject public WISERJobServiceManage() {}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override public void schedule(JobInfo.Builder builder) {
		JobScheduler jobScheduler = (JobScheduler) WISERHelper.getInstance().getSystemService(Context.JOB_SCHEDULER_SERVICE);
		if (jobScheduler == null) {
			return;
		}
		jobScheduler.schedule(builder.build());
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override public void cancel(int id) {
		JobScheduler jobScheduler = (JobScheduler) WISERHelper.getInstance().getSystemService(Context.JOB_SCHEDULER_SERVICE);
		if (jobScheduler == null) {
			return;
		}
		jobScheduler.cancel(id);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override public void cancelAll() {
		JobScheduler jobScheduler = (JobScheduler) WISERHelper.getInstance().getSystemService(Context.JOB_SCHEDULER_SERVICE);
		if (jobScheduler == null) {
			return;
		}
		jobScheduler.cancelAll();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override public JobInfo.Builder builder(int id, Class clazz) {
		return new JobInfo.Builder(id, new ComponentName(WISERHelper.getInstance().getPackageName(), clazz.getName()));
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override public JobInfo.Builder builder(int id, Class clazz, long delay, long deadline, int network, boolean isCharging) {
		JobInfo.Builder builder = new JobInfo.Builder(id, new ComponentName(WISERHelper.getInstance(), clazz));
		// 设置JobService执行的最小延时时间
		builder.setMinimumLatency(delay);
		// 设置JobService执行的最晚时间
		builder.setOverrideDeadline(deadline);
		// 执行的网络状态
		builder.setRequiredNetworkType(network);
		// 是否要设备为充电状态
		builder.setRequiresCharging(isCharging);
		return builder;
	}
}
