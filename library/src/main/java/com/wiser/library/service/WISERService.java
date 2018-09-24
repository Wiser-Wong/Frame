package com.wiser.library.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.model.WISERBizModel;
import com.wiser.library.util.WISERGenericSuperclass;

/**
 * @author Wiser
 * @param <B>
 * 
 *            服务
 */
@SuppressWarnings("unchecked")
public abstract class WISERService<B extends WISERBiz> extends Service {

	private B				b;

	private WISERBizModel	bizModel;

	/**
	 * 运行
	 *
	 * @param intent
	 *            参数
	 * @param flags
	 *            参数
	 * @param startId
	 *            参数
	 */
	protected abstract void running(Intent intent, int flags, int startId);

	protected abstract void initData();

	@Nullable @Override public IBinder onBind(Intent intent) {
		return null;
	}

	@Override public void onCreate() {
		super.onCreate();
		// 创建Biz储存对象
		bizModel = new WISERBizModel(biz());
		// 管理Biz
		WISERHelper.getBizManage().attach(bizModel);

		// 初始化
		initData();
	}

	@Override public void onDestroy() {
		// 销毁
		WISERHelper.getBizManage().detach(bizModel);
		super.onDestroy();
	}

	@Override public int onStartCommand(Intent intent, int flags, int startId) {
		running(intent, flags, startId);
		return START_NOT_STICKY;
	}

	/**
	 * 获取泛型实例
	 *
	 * @return
	 */
	public B biz() {
		try {
			if (b == null) b = (B) WISERGenericSuperclass.getActualTypeArgument(this.getClass()).newInstance();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public <D extends IWISERDisplay> D display() {
		return (D) WISERHelper.display();
	}
}
