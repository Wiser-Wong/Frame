package com.wiser.library.helper;

import android.app.Application;

import com.wiser.library.config.IWISERConfig;
import com.wiser.library.manager.IWISERActivityManage;
import com.wiser.library.manager.IWISERStructureManage;
import com.wiser.library.manager.WISERHandlerExecutor;
import com.wiser.library.manager.WISERLogManage;
import com.wiser.library.manager.WISERManage;
import com.wiser.library.manager.WISERToastMange;


/**
 * @author Wiser
 * 帮助类
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

            IWISERConfig.SWITCH_CONFIG = SWITCH;
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
    public static IWISERStructureManage getStructureManage() {
        return mWiserManage.getStructureManage();
    }

    /**
     * 获取Activity管理类对象
     *
     * @return
     */
    public static IWISERActivityManage getActivityManage() {
        return mWiserManage.getActivityManage();
    }

}
