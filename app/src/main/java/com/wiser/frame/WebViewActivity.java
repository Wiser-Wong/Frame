package com.wiser.frame;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERWebActivity;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERWebChromeClient;

public class WebViewActivity extends WISERWebActivity<IWebBiz> {

	@Override protected WISERBuilder buildWeb(WISERBuilder builder) {
		builder.layoutBarId(R.layout.title_layout);
		builder.isRootLayoutRefresh(true, false);
		builder.swipeBack(true);
		builder.tintFitsSystem(true);
		builder.layoutLoadingId(R.layout.view_loading);
		isHandleBack(true);
		isHaveProgress(true);
		return builder;
	}

	@MainThread public void show(String a) {
		WISERHelper.toast().show(a);
	}

	@Override protected void initWebData(Intent intent) {
		// WISERHelper.toast().show(new MShareConfig().getString("name", ""));
		// new MToast().show(new MShareConfig().getString("name", ""));
		// WISERHelper.toast().show(new MConfig(this).name+"");

		biz().netMethod();
	}

	@Override protected WISERWebChromeClient setWebChromeClient() {
		return new WISERWebChromeClient(this) {

			@Override public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					// showContentView();
					// WISERHelper.toast().show("已完成");
				} else {
					// showLoading();
					// WISERHelper.toast().show("未完成");
				}
			}

			@Override public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				// WISERHelper.toast().show(title);
			}
		};
	}

	@Override protected WebViewClient setWebViewClient() {
		return null;
	}

	@Override protected String loadUrl() {
		return "https://www.baidu.com";
	}

	@Override protected boolean isCustomWebSetting() {
		return false;
	}

	@Override protected WebView customWebSetting(WebView webView) {
		return webView;
	}
}
