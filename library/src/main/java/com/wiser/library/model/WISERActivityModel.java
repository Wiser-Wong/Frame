package com.wiser.library.model;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.helper.WISERHelper;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERActivityModel {

	private WISERActivity	activity;

	private boolean			isLanding;

	private boolean			isRunning	= true;

	private String			activityName;

	private WISERBizModel	bizModel;

	public WISERActivityModel(WISERActivity activity, WISERBizModel bizModel, boolean isLanding) {
		this.activity = activity;
		this.bizModel = bizModel;
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

	public WISERActivity getActivity() {
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

	public WISERBizModel getBizModel() {
		return bizModel;
	}

	public void finish() {
		activity.finish();
		WISERHelper.log().e(this.activityName + " 销毁.");
		activity = null;
	}

	public void destroyLog() {
		WISERHelper.log().e(this.activityName + " 销毁.");
	}

}
