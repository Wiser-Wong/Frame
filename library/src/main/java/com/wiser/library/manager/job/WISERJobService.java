package com.wiser.library.manager.job;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.model.WISERBizModel;
import com.wiser.library.util.WISERGenericSuperclass;

import android.annotation.TargetApi;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author Wiser
 * @param <B>
 * 
 *            jobService
 */
@SuppressWarnings("unchecked")
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public abstract class WISERJobService<B extends WISERBiz> extends JobService {

	private B				b;

	private WISERBizModel	bizModel;

	@Override public void onCreate() {
		super.onCreate();
		// 创建Biz储存对象
		bizModel = new WISERBizModel(this);
		// 管理Biz
		WISERHelper.getBizManage().attach(bizModel);
	}

	@Override public void onDestroy() {
		// 销毁
		WISERHelper.getBizManage().detach(bizModel);
		super.onDestroy();
	}

	/**
	 * 获取泛型实例
	 *
	 * @return
	 */
	public B biz() {
		if (bizModel != null) return (B) bizModel.biz();
		return null;
	}

	public <D extends IWISERDisplay> D display() {
		return (D) WISERHelper.display();
	}
}
