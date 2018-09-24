package com.wiser.library.manager.log;

import android.util.Log;

import com.wiser.library.config.IWISERConfig;

import javax.inject.Inject;

/**
 * @author Wiser
 * 
 *         日志管理
 */
public class WISERLogManage {

	private String TAG = "WISER";

	@Inject public WISERLogManage() {}

	public void v(String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.v(TAG, msg);
		}
	}

	public void v(String tag, String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.v(tag, msg);
		}
	}

	public void v(String tag, String msg, Throwable t) {
		if (IWISERConfig.IS_DEBUG) {
			Log.v(tag, msg, t);
		}
	}

	public void d(String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.d(TAG, msg);
		}
	}

	public void d(String tag, String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.d(tag, msg);
		}
	}

	public void d(String tag, String msg, Throwable t) {
		if (IWISERConfig.IS_DEBUG) {
			Log.d(tag, msg, t);
		}
	}

	public void i(String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.i(TAG, msg);
		}
	}

	public void i(String tag, String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.i(tag, msg);
		}
	}

	public void i(String tag, String msg, Throwable t) {
		if (IWISERConfig.IS_DEBUG) {
			Log.i(tag, msg, t);
		}
	}

	public void w(String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.w(TAG, msg);
		}
	}

	public void w(String tag, String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.w(tag, msg);
		}
	}

	public void w(String tag, String msg, Throwable t) {
		if (IWISERConfig.IS_DEBUG) {
			Log.w(tag, msg, t);
		}
	}

	public void e(String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.e(TAG, msg);
		}
	}

	public void e(String tag, String msg) {
		if (IWISERConfig.IS_DEBUG) {
			Log.e(tag, msg);
		}
	}

	public void e(String tag, String msg, Throwable t) {
		if (IWISERConfig.IS_DEBUG) {
			Log.e(tag, msg, t);
		}
	}
}
