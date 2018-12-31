package com.wiser.library.network;

import com.google.gson.JsonParseException;
import com.wiser.library.helper.WISERHelper;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * RxJava Observer封装抽象类
 * 
 * @param <T>
 */
public abstract class WISERRxJavaObserver<T> implements Observer<T> {

	private Disposable disposable;

	@Override public void onSubscribe(Disposable d) {
		this.disposable = d;
	}

	@Override public void onNext(T t) {
		onSuccess(t);
	}

	@Override public void onError(Throwable e) {
		detachDisposable();
		if (e instanceof HttpException) { // HTTP错误
			onException(ExceptionConstants.BAD_NETWORK);
		} else if (e instanceof ConnectException || e instanceof UnknownHostException) { // 连接错误
			onException(ExceptionConstants.CONNECT_ERROR);
		} else if (e instanceof InterruptedIOException) { // 连接超时
			onException(ExceptionConstants.CONNECT_TIMEOUT);
		} else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) { // 解析错误
			onException(ExceptionConstants.PARSE_ERROR);
		} else {
			onException(ExceptionConstants.RESPONSE_ERROR);
		}
		onFail(e);
	}

	@Override public void onComplete() {
		detachDisposable();
	}

	private void detachDisposable() {
		if (disposable != null && disposable.isDisposed()) {
			disposable.dispose();
			disposable = null;
		}
	}

	protected abstract void onSuccess(T t);

	protected void onFail(Throwable e) {}

	/**
	 * 请求异常
	 *
	 * @param reason
	 */
	private void onException(int reason) {
		switch (reason) {
			case ExceptionConstants.BAD_NETWORK:// 网络错误
				WISERHelper.toast().show("网络错误");
				break;
			case ExceptionConstants.CONNECT_ERROR:// 连接错误
				WISERHelper.toast().show("连接错误");
				break;
			case ExceptionConstants.CONNECT_TIMEOUT:// 连接超时
				WISERHelper.toast().show("连接超时");
				break;
			case ExceptionConstants.PARSE_ERROR:// 解析错误
				WISERHelper.toast().show("服务器响应失败");
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
