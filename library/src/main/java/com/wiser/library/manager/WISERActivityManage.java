package com.wiser.library.manager;

import android.support.v4.app.FragmentActivity;

import com.wiser.library.config.IWISERConfig;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.model.WISERActivityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Wiser activity的管理类
 */
@SuppressWarnings("unchecked")
public class WISERActivityManage implements IWISERActivityManage {

	/**
	 * 打开的activity
	 **/
	private final List<WISERActivityModel> activities = new ArrayList<>();

	/**
	 * 新建了一个activity
	 *
	 * @param model
	 */
	@Override public void addActivity(WISERActivityModel model) {
		activities.add(model);
	}

	/**
	 * 结束指定的Activity
	 *
	 * @param model
	 */
	@Override public void finishActivity(WISERActivityModel model) {

		if (model != null) {
			if (!model.getActivity().isFinishing()) {
				model.finish();
			} else {
				model.destroyLog();
			}
			this.activities.remove(model);
			if (model.getBizModel() != null) WISERHelper.getBizManage().detach(model.getBizModel());
		}
	}

	/**
	 * 结束所有的Activity
	 */
	@Override public void finishAllActivity() {
		if (activities.size() == 0) return;
		for (int i = 0; i < activities.size(); i++) {
			finishActivity(this.activities.get(i));
		}
	}

	/**
	 * 应用退出，结束所有的activity
	 */
	@Override public void exitFinishAllActivity() {

		for (WISERActivityModel model : activities) {
			if (model != null) {
				finishActivity(model);
			}
		}
		System.exit(0);

	}

	/**
	 * 结束指定类名的Activity
	 */
	@Override public void finishActivityClass(Class<?> cls) {
		for (WISERActivityModel model : activities) {
			if (model != null) {
				if (model.getActivity().getClass().equals(cls)) {
					finishActivity(model);
					break;
				}
			}
		}
	}

	/**
	 * 结束除了某一个activity的所有activity
	 *
	 * @param cls
	 */
	@Override public void finishAllActivityExceptClass(Class<?> cls) {
		int len = activities.size();
		for (int i = 0; i < len; i++) {
			if (activities.get(i) != null) {
				if (activities.get(i).getActivity().getClass().equals(cls)) {
				} else {
					finishActivity(activities.get(i));
					--len;
					--i;
				}
			}
		}
	}

	/**
	 * 获取当前正在显示的Activity
	 * 
	 * @param <T>
	 * @return
	 */
	@Override public <T extends FragmentActivity> T getCurrentActivity() {
		if (activities.size() > 0) return (T) activities.get(activities.size() - 1).getActivity();
		return null;
	}

	/**
	 * 返回当前活动活动
	 *
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	public <T extends FragmentActivity> T getCurrentIsRunningActivity() {
		if (activities.size() > 0) {
			synchronized (activities) {
				for (int i = 0; i < activities.size(); i++) {
					if (activities.get(i).isRunning()) {
						return (T) activities.get(i).getActivity();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 改变给定活动的状态，因为它不再运行了。#这 方法只是保持跟踪对给定的活动没有影响
	 *
	 * @param activity
	 *            参数
	 */
	@Override public void onPause(FragmentActivity activity) {
		synchronized (activities) {
			for (int i = activities.size() - 1; i >= 0; i--) {
				if (activities.get(i).getActivity().equals(activity)) {
					activities.get(i).pause();
					break;
				}
			}
		}
	}

	/**
	 * 运行状态
	 * 
	 * @param activity
	 */
	@Override public void onResume(FragmentActivity activity) {
		synchronized (activities) {
			for (int i = activities.size() - 1; i >= 0; i--) {
				if (activities.get(i).getActivity().equals(activity)) {
					activities.get(i).resume();
					break;
				}
			}
		}
	}

	@Override public int size() {
		return activities.size();
	}

	@Override public List<WISERActivityModel> getActivityModels() {
		return activities;
	}

	/**
	 * 打印存在的Activity
	 */
	@Override public void logActivityList() {
		if (IWISERConfig.IS_DEBUG) {
			Map<Integer, Object> bizList = WISERHelper.getBizManage().bizList();
			if (bizList == null) {
				return;
			}
			Set<Map.Entry<Integer, Object>> entries = bizList.entrySet();
			StringBuilder activityListString = new StringBuilder();
			StringBuilder bizListString = new StringBuilder();
			for (Map.Entry<Integer, Object> entry : entries) {
				bizListString.append(entry.getValue()).append("--->>");
			}
			for (int i = 0; i < activities.size(); i++) {
				if (i == activities.size() - 1) {
					activityListString.append(activities.get(i).getActivity());
				} else {
					activityListString.append(activities.get(i).getActivity()).append("--->>");
				}
			}
			WISERHelper.log().e("activityList---:" + activityListString.toString());
			WISERHelper.log().e("bizList---:" + bizListString.toString());
		}
	}

}