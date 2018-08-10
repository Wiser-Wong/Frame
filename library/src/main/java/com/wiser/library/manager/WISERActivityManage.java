package com.wiser.library.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 * activity的管理类
 */
public class WISERActivityManage implements IWISERActivityManage {

    /**
     * 打开的activity
     **/
    private List<Activity> activities = new ArrayList<Activity>();


    /**
     * 新建了一个activity
     *
     * @param activity
     */
    @Override
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    @Override
    public void finishActivity(Activity activity) {

        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束所有的Activity
     */
    @Override
    public void finishAllActivity() {
        if (activities == null || activities.size() == 0) return;
        for (int i = 0; i < activities.size(); i++) {
            this.activities.get(i).finish();
        }
        this.activities.clear();
    }

    /**
     * 应用退出，结束所有的activity
     */
    @Override
    public void exitFinishAllActivity() {

        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);

    }

    /**
     * 结束指定类名的Activity
     */
    @Override
    public void finishActivityclass(Class<?> cls) {
        if (activities != null) {
            for (Activity activity : activities) {
                if (activity.getClass().equals(cls)) {
                    this.activities.remove(activity);
                    finishActivity(activity);
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
    @Override
    public void finishAllActivityExceptClass(Class<?> cls) {
        if (activities != null) {
            int len = activities.size();
            for (int i = 0; i < len; i++) {
                if (activities.get(i).getClass().equals(cls)) {
                } else {
                    finishActivity(activities.get(i));
                    --len;
                    --i;
                }
            }
        }
    }

}