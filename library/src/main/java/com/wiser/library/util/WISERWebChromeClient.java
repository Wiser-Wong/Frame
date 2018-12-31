package com.wiser.library.util;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERWebActivity;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERWebChromeClient extends WebChromeClient {

	private WISERWebActivity activity;

	public WISERWebChromeClient(WISERActivity activity) {
		super();
		this.activity = (WISERWebActivity) activity;
	}

	@Override public void onProgressChanged(WebView view, int newProgress) {
		super.onProgressChanged(view, newProgress);
		if (activity != null) activity.setWebProgress(newProgress);
	}

	public void detach() {
		this.activity = null;
	}
}
