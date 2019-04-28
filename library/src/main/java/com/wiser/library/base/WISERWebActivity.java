package com.wiser.library.base;

import com.wiser.library.R;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERApp;
import com.wiser.library.util.WISERCheck;
import com.wiser.library.util.WISERWebChromeClient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * @author Wiser
 * 
 *         WebView网页
 */
public abstract class WISERWebActivity<B extends IWISERBiz> extends WISERActivity<B> {

	private boolean					isHandleBack	= false;				// 是否处理返回

	private boolean					isHaveProgress	= false;				// 是否有进度条

	private WebView					webView;

	private LinearLayout			rootLayout;

	private ProgressBar				progressView;

	private @DrawableRes int		progressDrawableColorId;

	private WISERWebChromeClient	webChromeClient	= setWebChromeClient();

	private WebViewClient			webViewClient	= setWebViewClient();

	protected abstract WISERBuilder buildWeb(WISERBuilder builder);

	protected abstract void initWebData(Intent intent);

	protected abstract WISERWebChromeClient setWebChromeClient();

	protected abstract WebViewClient setWebViewClient();

	protected abstract String loadUrl();

	protected abstract boolean isCustomWebSetting();// 是否自定义WebSetting 设置

	protected abstract WebView customWebSetting(WebView webView);

	@Override protected WISERBuilder build(WISERBuilder builder) {
		WISERBuilder wiserBuilder = buildWeb(builder);
		wiserBuilder.layoutView(createView());
		return wiserBuilder;
	}

	@Override protected void initData(Intent intent) {
		initWebData(intent);
		settingWeb();
	}

	/**
	 * 创建布局
	 *
	 * @return
	 */
	private View createView() {
		// 创建WebView
		createWebView();
		// 是否有进度条
		if (isHaveProgress) {
			// 创建父布局
			createLayout();
			// 创建ProgressBar进度条
			createProgressView();

			rootLayout.addView(progressView);

			rootLayout.addView(webView);

			return rootLayout;
		} else {
			return webView;
		}
	}

	/**
	 * 创建WebView rootLayout
	 *
	 * @return
	 */
	private void createLayout() {
		rootLayout = new LinearLayout(this);
		rootLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		// 纵向
		rootLayout.setOrientation(LinearLayout.VERTICAL);
	}

	/**
	 * 创建ProgressBar
	 *
	 * @return
	 */
	private void createProgressView() {
		progressView = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
		progressView.setIndeterminate(false);
		progressView.setMax(100);
		if (progressDrawableColorId != 0) {
			progressView.setProgressDrawable(ContextCompat.getDrawable(this, progressDrawableColorId));
		} else {
			progressView.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.progress_line));
		}
		progressView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, WISERApp.dip2px(2)));
	}

	// 创建WebView
	private void createWebView() {
		webView = new WebView(this);
		webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}

	// 配置Web
	@SuppressLint("SetJavaScriptEnabled") private void settingWeb() {
		if (isCustomWebSetting()) {
			this.webView = customWebSetting(webView);
		} else {
			// 设置内核是原生的
			webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + " APP_Android");
			// 使用缓存WebSettings.LOAD_CACHE_ELSE_NETWORK（WebSettings.LOAD_NO_CACHE不是用缓存）
			webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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
			// 关闭密码保存
			webView.getSettings().setSavePassword(false);
			// 允许读取本地文件
			webView.getSettings().setAllowFileAccess(true);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				webView.getSettings().setAllowFileAccessFromFileURLs(false);
				webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
			}
			// 启用支持javascript
			if (!WISERCheck.isEmpty(loadUrl())) {
				if (loadUrl().startsWith("file://")) {
					webView.getSettings().setJavaScriptEnabled(false);
				} else {
					webView.getSettings().setJavaScriptEnabled(true);
				}
			}
			// webView.addJavascriptInterface(new NativeClass(), "nativeClass");
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
			// cookie
			CookieManager cookieManager = CookieManager.getInstance();
			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				cookieManager.setAcceptThirdPartyCookies(webView, true);
			} else {
				cookieManager.setAcceptCookie(true);
			}
		}

		webView.setWebChromeClient(webChromeClient);
		webView.setWebViewClient(webViewClient);
		// 加载网络链接
		webView.loadUrl(loadUrl());
	}

	// 进度条显示隐藏
	public void isHideProgress(boolean isHide) {
		if (isHaveProgress) {
			if (isHide) {
				if (progressView.getVisibility() == View.VISIBLE) progressView.setVisibility(View.INVISIBLE);
			} else {
				if (progressView.getVisibility() == View.INVISIBLE) progressView.setVisibility(View.VISIBLE);
			}
		}
	}

	public void progressDrawable(@DrawableRes int drawableColorId) {
		this.progressDrawableColorId = drawableColorId;
		if (progressView != null) progressView.setProgressDrawable(ContextCompat.getDrawable(this, drawableColorId));
	}

	// 判断进度条进度显示 #(当子类WebActivity需要扩展WebChromeClient的时候
	// 如果需要加入默认进度条，那么需要在onProgressChanged（）方法中调用该方法)
	public void setWebProgress(int newProgress) {
		if (newProgress == 100) {
			isHideProgress(true);
		} else {
			isHideProgress(false);
			if (isHaveProgress) progressView.setProgress(newProgress);
		}
	}

	// WebView实例
	public WebView webView() {
		return webView;
	}

	/**
	 * @param isHandleBack
	 *            是否处理返回
	 */
	public void isHandleBack(boolean isHandleBack) {
		this.isHandleBack = isHandleBack;
	}

	/**
	 * @param isHaveProgress
	 *            是否有进度条
	 */
	public void isHaveProgress(boolean isHaveProgress) {
		this.isHaveProgress = isHaveProgress;
	}

	/**
	 * 返回处理
	 */
	protected boolean backHandle() {
		if (isHandleBack) if (webView != null && webView.canGoBack()) {
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
		if (webView != null) {
			webView.onPause();
			webView.pauseTimers();
		}
		super.onPause();
		if (isFinishing()) {
			if (webView != null) {
				destroyWebView();
				webView = null;
			}
			rootLayout = null;
			progressView = null;
			webViewClient = null;
			if (webChromeClient != null) {
				webChromeClient.detach();
				webChromeClient = null;
			}
		}
	}

	@Override protected void onResume() {
		if (webView != null) {
			webView.onResume();
			webView.resumeTimers();
		}
		super.onResume();
	}

	/**
	 * 销毁WebView
	 */
	private void destroyWebView() {
		// webview引起的内存泄漏主要是因为org.chromium.android_webview.AwContents 类中注册了component
		// callbacks，但是未正常反注册而导致的。
		// org.chromium.android_webview.AwContents 类中有这两个方法 onAttachedToWindow 和
		// onDetachedFromWindow；系统会在attach和detach处进行注册和反注册component callback；
		// 在onDetachedFromWindow() 方法的第一行中：
		// if (isDestroyed()) return;
		// 如果 isDestroyed() 返回 true
		// 的话，那么后续的逻辑就不能正常走到，所以就不会执行unregister的操作；我们的activity退出的时候，都会主动调用
		// WebView.destroy() 方法，这会导致 isDestroyed() 返回
		// true；destroy()的执行时间又在onDetachedFromWindow之前，所以就会导致不能正常进行unregister()。
		// 然后解决方法就是：让onDetachedFromWindow先走，在主动调用destroy()之前，把webview从它的parent上面移除掉。
		if (webView == null) return;
		ViewParent parent = webView.getParent();
		if (parent != null) {
			((ViewGroup) parent).removeView(webView);
		}
		webView.stopLoading();
		webView.getSettings().setJavaScriptEnabled(false);
		webView.clearHistory();
		webView.clearView();
		webView.removeAllViews();
		webView.destroy();
		webView = null;
	}
}
