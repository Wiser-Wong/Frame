package com.wiser.library.base;

import android.os.Bundle;

/**
 * @author Wiser
 * @version 版本
 * @param <U>
 */
@SuppressWarnings("unchecked")
public class WISERBiz<U> implements IWISERBiz {

	private U u;

	protected U ui() {
		return u;
	}

	@Override public void initActivity(WISERActivity activity) {
		this.u = (U) activity;
	}

	@Override public void initBiz(Bundle savedInstanceState) {

	}
}
