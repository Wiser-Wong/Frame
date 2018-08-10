package com.wiser.library.manager;

import android.util.Log;

import com.wiser.library.config.IWISERConfig;


/**
 * @author Wiser 日志管理
 */
public class WISERLogManage {

	private String TAG = "WISER";

	public void v(String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.v(TAG, msg);
		}
	}

	public void v(String tag, String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.v(tag, msg);
		}
	}

	public void v(String tag, String msg, Throwable t) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.v(tag, msg, t);
		}
	}

	public void d(String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.d(TAG, msg);
		}
	}

	public void d(String tag, String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.d(tag, msg);
		}
	}

	public void d(String tag, String msg, Throwable t) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.d(tag, msg, t);
		}
	}

	public void i(String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.i(TAG, msg);
		}
	}

	public void i(String tag, String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.i(tag, msg);
		}
	}

	public void i(String tag, String msg, Throwable t) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.i(tag, msg, t);
		}
	}

	public void w(String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.w(TAG, msg);
		}
	}

	public void w(String tag, String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.w(tag, msg);
		}
	}

	public void w(String tag, String msg, Throwable t) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.w(tag, msg, t);
		}
	}

	public void e(String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.e(TAG, msg);
		}
	}

	public void e(String tag, String msg) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.e(tag, msg);
		}
	}

	public void e(String tag, String msg, Throwable t) {
		if (IWISERConfig.SWITCH_CONFIG) {
			Log.e(tag, msg, t);
		}
	}
}
