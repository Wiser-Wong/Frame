package com.wiser.library.base;

import java.util.Vector;

import com.wiser.library.helper.WISERHelper;
import com.wiser.library.loading.LoadingDialogFragment;
import com.wiser.library.network.WISERRxJavaDisposableObserver;

import android.content.Intent;
import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 * @param <U>
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERBiz<U> implements IWISERBiz {

	private U u;

	public U ui() {
		return u;
	}

	private Vector<Call>				callVector;

	private Vector<DisposableObserver>	observerVector;

	@Override public void initUi(Object object) {
		this.u = (U) object;
		callVector = new Vector<>();
		observerVector = new Vector<>();
	}

	@Override public void initBiz(Intent intent) {

	}

	@Override public void initBiz(Bundle bundle) {

	}

	/**
	 * View 层是否存在
	 * 
	 * @return
	 */
	protected boolean isUi() {
		return u != null;
	}

	protected <H> H http(Class<H> hClass) {
		return WISERHelper.http(hClass);
	}

	/**
	 * Retrofit方式
	 * 
	 * @param call
	 *            参数
	 * @param <D>
	 *            参数
	 * @return 返回值
	 */
	protected <D> D httpBody(Call<D> call) {
		callVector.add(call);
		return WISERHelper.httpBody(call);
	}

	protected <D> WISERRxJavaDisposableObserver<D> httpDisposableObserver(WISERRxJavaDisposableObserver<D> observer) {
		observerVector.add(observer);
		return observer;
	}

	/**
	 * RxJava 方式
	 * 
	 * @param observable
	 *            参数
	 * @param <D>
	 *            参数
	 * @return 返回值
	 */
	protected <D> Observable<D> httpObservableIO(Observable<D> observable) {
		if (observable != null) {
			return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
		return null;
	}

	/**
	 * RxJava 方式
	 *
	 * @param observable
	 *            参数
	 * @param <D>
	 *            参数
	 * @return 返回值
	 */
	protected <D> Observable<D> httpObservableThread(Observable<D> observable) {
		if (observable != null) {
			return observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
		}
		return null;
	}

	/**
	 * 显示loading
	 * 
	 * @param isClose
	 *            是否可以关闭弹窗
	 */
	protected void showLoading(boolean... isClose) {
		if (isClose.length > 0) {
			LoadingDialogFragment.showLoadingDialog(isClose[0]);
		} else {
			LoadingDialogFragment.showLoadingDialog(false);
		}
	}

	/**
	 * 隐藏loading
	 */
	protected void hideLoading() {
		LoadingDialogFragment loadingDialogFragment = WISERHelper.display().findFragment(LoadingDialogFragment.class.getName());
		if (loadingDialogFragment != null) loadingDialogFragment.dismiss();
	}

	/**
	 * 是否正在显示loading
	 */
	protected boolean isRunningLoading() {
		LoadingDialogFragment loadingDialogFragment = WISERHelper.display().findFragment(LoadingDialogFragment.class.getName());
		if (loadingDialogFragment != null) {
			return loadingDialogFragment.isShowing();
		}
		return false;
	}

	/**
	 * 网络取消
	 */
	private void httpCallCancel() {
		if (callVector != null) {
			int count = callVector.size();
			if (count < 1) {
				return;
			}
			for (int i = 0; i < count; i++) {
				Call call = callVector.get(i);
				WISERHelper.httpCallCancel(call);
			}
			callVector.removeAllElements();
			callVector = null;
		}
	}

	/**
	 * 网络取消
	 */
	private void httpObserverCancel() {
		if (observerVector != null) {
			int count = observerVector.size();
			if (count < 1) {
				return;
			}
			for (int i = 0; i < count; i++) {
				DisposableObserver observer = observerVector.get(i);
				WISERHelper.httpObserverCancel(observer);
			}
			observerVector.removeAllElements();
			observerVector = null;
		}
	}

	public void detach() {
		u = null;
		hideLoading();
		httpCallCancel();
		httpObserverCancel();
	}
}
