package com.wiser.library.base;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author Wiser
 */
public interface IWISERBiz {

	// 为Biz类获取绑定视图实例
	void initUi(Object object);

	void initBiz(Intent intent);

	void initBiz(Bundle bundle);

}
