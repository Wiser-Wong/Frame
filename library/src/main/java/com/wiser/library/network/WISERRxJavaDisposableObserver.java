package com.wiser.library.network;

import com.google.gson.JsonParseException;
import com.wiser.library.helper.WISERHelper;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 封装DisposableObserver
 * 
 * @param <T>
 */
public abstract class WISERRxJavaDisposableObserver<T> extends DisposableObserver<T> {

	private boolean isDfException = false;

	@Override public void onNext(T t) {
		try {
			onSuccess(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override public void onError(Throwable e) {
		if (isDfException(isDfException)) {
			if (e instanceof HttpException) { // HTTP错误
				onException(WISERRxJavaDisposableObserver.ExceptionConstants.BAD_NETWORK);
			} else if (e instanceof ConnectException || e instanceof UnknownHostException) { // 连接错误
				onException(WISERRxJavaDisposableObserver.ExceptionConstants.CONNECT_ERROR);
			} else if (e instanceof InterruptedIOException) { // 连接超时
				onException(WISERRxJavaDisposableObserver.ExceptionConstants.CONNECT_TIMEOUT);
			} else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) { // 解析错误
				onException(WISERRxJavaDisposableObserver.ExceptionConstants.PARSE_ERROR);
			} else {
				onException(WISERRxJavaDisposableObserver.ExceptionConstants.RESPONSE_ERROR);
			}
		}
		try {
			onFail(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override public void onComplete() {

	}

	protected abstract void onSuccess(T t) throws Exception;

	protected boolean isDfException(boolean isDfException) {
		this.isDfException = isDfException;
		return isDfException;
	}

	protected void onFail(Throwable e) throws Exception{}

	/**
	 * 请求异常
	 *
	 * @param reason
	 */
	private void onException(int reason) {
		switch (reason) {
			case WISERRxJavaDisposableObserver.ExceptionConstants.BAD_NETWORK:// 网络错误
				WISERHelper.toast().show("网络错误");
				break;
			case WISERRxJavaDisposableObserver.ExceptionConstants.CONNECT_ERROR:// 连接错误
				WISERHelper.toast().show("连接错误");
				break;
			case WISERRxJavaDisposableObserver.ExceptionConstants.CONNECT_TIMEOUT:// 连接超时
				WISERHelper.toast().show("连接超时");
				break;
			case WISERRxJavaDisposableObserver.ExceptionConstants.PARSE_ERROR:// 解析错误
				WISERHelper.toast().show("解析失败");
				break;
			default:
				WISERHelper.toast().show("服务器响应失败");
				break;
		}
	}

	private interface ExceptionConstants {

		int	CONNECT_ERROR	= 0X1111;	// 连接错误

		int	CONNECT_TIMEOUT	= 0X1112;	// 连接超时

		int	PARSE_ERROR		= 0X1113;	// 解析错误

		int	BAD_NETWORK		= 0X1114;	// 网络错误

		int	RESPONSE_ERROR	= 0X1115;	// 响应服务器错误
	}
}
