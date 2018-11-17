package com.wiser.library.base;

import android.content.Intent;
import android.os.Bundle;

import com.wiser.library.helper.WISERHelper;

import java.util.Vector;

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

	private Vector<Call> callVector;

	@Override public void initUi(Object object) {
		this.u = (U) object;
		callVector = new Vector<>();
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

	/**
	 * 网络取消
	 */
	protected void httpCancel() {
		if (callVector != null) {
			int count = callVector.size();
			if (count < 1) {
				return;
			}
			for (int i = 0; i < count; i++) {
				Call call = callVector.get(i);
				WISERHelper.httpCancel(call);
			}
			callVector.removeAllElements();
			callVector = null;
		}
	}

	public void detach() {
		u = null;
		httpCancel();
	}
}
