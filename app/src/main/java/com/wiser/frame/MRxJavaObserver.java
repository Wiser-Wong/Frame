package com.wiser.frame;

import com.wiser.library.helper.WISERHelper;
import com.wiser.library.network.WISERRxJavaObserver;

public class MRxJavaObserver<T> extends WISERRxJavaObserver<T> {

	@Override protected void onSuccess(T o) {

	}

	@Override public void onError(Throwable e) {
		WISERHelper.toast().show("错误");
	}
}
