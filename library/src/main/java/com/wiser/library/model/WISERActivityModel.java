package com.wiser.library.model;

import android.support.v4.app.FragmentActivity;

import com.wiser.library.helper.WISERHelper;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERActivityModel {

	private FragmentActivity	activity;

	private boolean				isLanding	= false;

	private boolean				isRunning	= true;

	private String				activityName;

	public WISERActivityModel(FragmentActivity activity, boolean isLanding) {
		this.activity = activity;
		this.activityName = activity.getClass().getSimpleName();
		this.isLanding = isLanding;
		WISERHelper.log().e(this.activityName + " 创建.");
	}

	public void pause() {
		this.isRunning = false;
		WISERHelper.log().e(this.activityName + " 暂停.");
	}

	public void resume() {
		this.isRunning = true;
		WISERHelper.log().e(this.activityName + " 运行.");
	}

	public void result() {
		this.isRunning = true;
	}

	public FragmentActivity getActivity() {
		return activity;
	}

	public boolean isLanding() {
		return isLanding;
	}

	public void setLanding(boolean isLanding) {
		this.isLanding = isLanding;
		WISERHelper.log().e(isLanding ? " 定位!" : " 没有定位!");
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void finish() {
		activity.finish();
		WISERHelper.log().e(this.activityName + " 销毁.");
	}

	public void destroyLog() {
		WISERHelper.log().e(this.activityName + " 销毁.");
	}

}
