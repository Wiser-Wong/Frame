package com.wiser.library.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Wiser
 * 
 *         WebView网页
 */
public abstract class WISERWebActivity extends WISERActivity {

	protected abstract WISERBuilder buildWeb(WISERBuilder builder, WISERWebActivity webActivity);

	protected abstract void initWebData(Bundle savedInstanceState);

	protected abstract WebChromeClient setWebChromeClient();

	protected abstract WebViewClient setWebViewClient();

	protected abstract String loadUrl();

	private WebView	webView;

	private boolean	isHandleBack	= false;// 是否处理返回

	@Override protected WISERBuilder build(WISERBuilder builder) {
		webView = createWebView();
		builder.layoutView(webView);
		return buildWeb(builder, this);
	}

	@Override protected void initData(Bundle savedInstanceState) {
		initWebData(savedInstanceState);
	}

	// 创建WebView
	@SuppressLint({ "SetJavaScriptEnabled" }) private WebView createWebView() {
		WebView webView = new WebView(this);
		webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		// 设置内核是原生的
		webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + " APP_Android");
		// 使用缓存WebSettings.LOAD_CACHE_ELSE_NETWORK（WebSettings.LOAD_NO_CACHE不是用缓存）
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		// 启用支持javascript
		webView.getSettings().setJavaScriptEnabled(true);
		// webView.addJavascriptInterface(new NativeClass(), "nativeClass");
		// 解决加载图片不显示
		webView.getSettings().setBlockNetworkImage(false);
		// 设置可以支持缩放
		webView.getSettings().setSupportZoom(true);
		// 防止页面加载不全或者空白页
		webView.getSettings().setDomStorageEnabled(true);
		// 设置出现缩放工具
		webView.getSettings().setBuiltInZoomControls(true);
		// 扩大比例的缩放
		webView.getSettings().setUseWideViewPort(true);
		// 设置支持多窗口
		webView.getSettings().setSupportMultipleWindows(true);
		// 允许读取本地文件
		webView.getSettings().setAllowFileAccess(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			webView.getSettings().setAllowFileAccessFromFileURLs(true);
		}
		// 自适应屏幕
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);
		// 将图片调整到适合webview的大小
		webView.getSettings().setUseWideViewPort(false);
		// 支持通过JS打开新窗口
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		// 支持自动加载图片
		webView.getSettings().setLoadsImagesAutomatically(true);
		// 解决系统版本不同图片显示问题
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		// 支持内容重新布局
		webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		// 取消缩放控制条
		webView.getSettings().setDisplayZoomControls(false);
		// 加载网络链接
		webView.loadUrl(loadUrl());

		webView.setWebChromeClient(setWebChromeClient());
		webView.setWebViewClient(setWebViewClient());
		return webView;
	}

	// WebView实例
	public WebView webView() {
		return webView;
	}

	/**
	 * 
	 * @param isHandleBack
	 *            是否处理返回
	 */
	public void isHandleBack(boolean isHandleBack) {
		this.isHandleBack = isHandleBack;
	}

	/**
	 * 返回处理
	 */
	private boolean backHandle() {
		if (isHandleBack) if (webView.canGoBack()) {
			webView.goBack();
			return true;
		} else {
			return false;
		}
		return false;
	}

	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return backHandle() || super.onKeyUp(keyCode, event);
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override protected void onPause() {
		webView.onPause();
		webView.pauseTimers();
		super.onPause();
	}

	@Override protected void onResume() {
		webView.onResume();
		webView.resumeTimers();
		super.onResume();
	}

	@Override protected void onDestroy() {
		if (webView != null) {
			webView.destroy();
			webView = null;
		}
		super.onDestroy();
	}
}
