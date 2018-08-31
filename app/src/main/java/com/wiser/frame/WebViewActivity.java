package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERWebActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends WISERWebActivity {

	@Override protected WISERBuilder buildWeb(WISERBuilder builder, WISERWebActivity activity) {
		builder.layoutBarId(R.layout.title_layout);
		builder.isRootLayoutRefresh(true, false);
		builder.swipeBack(true);
		builder.tintFitsSystem(true);
		builder.layoutLoadingId(R.layout.view_loading);
		activity.isHandleBack(true);
		return builder;
	}

	@Override protected void initWebData(Bundle savedInstanceState) {}

	@Override protected WebChromeClient setWebChromeClient() {
		return new WebChromeClient() {

			@Override public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					showContentView();
				} else {
					showLoading();
				}
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
