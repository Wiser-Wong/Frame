package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERWebActivity;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERWebChromeClient;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends WISERWebActivity {

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

	@Override protected void initWebData(Bundle savedInstanceState) {}

	@Override protected WISERWebChromeClient setWebChromeClient() {
		return new WISERWebChromeClient(this) {

			@Override public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					// showContentView();
					WISERHelper.toast().show("已完成");
				} else {
					// showLoading();
					WISERHelper.toast().show("未完成");
				}
			}

			@Override public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				WISERHelper.toast().show(title);
			}
		};
	}

	@Override protected WebViewClient setWebViewClient() {
		return null;
	}

	@Override protected String loadUrl() {
		return "https://www.baidu.com";
	}
}
