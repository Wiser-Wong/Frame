package com.wiser.library.base;

import android.os.Bundle;

/**
 * @author Wiser
 */
public interface IWISERBiz {

	// 为Biz类获取绑定Activity实例
	void initActivity(WISERActivity activity);

	void initBiz(Bundle savedInstanceState);

}
