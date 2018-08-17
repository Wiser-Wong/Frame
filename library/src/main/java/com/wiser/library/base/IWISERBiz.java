package com.wiser.library.base;

import android.os.Bundle;

/**
 * @author Wiser
 */
public interface IWISERBiz {

	// 为Biz类获取绑定视图实例
	void initUi(Object object);

	void initBiz(Bundle savedInstanceState);

}
