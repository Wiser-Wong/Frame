package com.wiser.library.base;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.helper.WISERHelper;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERView {

	/**
	 * 常量
	 */
	public static final int	STATE_ACTIVITY	= 99999;

	public static final int	STATE_FRAGMENT	= 88888;

	/** 类型 **/
	private int				state;

	private WISERActivity	mWiserActivity;

	private Context			context;

	private WISERFragment	mWiserFragment;

	/**
	 * 初始化
	 *
	 * @param mWiserActivity
	 *            参数
	 **/
	public void initUI(WISERActivity mWiserActivity) {
		this.state = STATE_ACTIVITY;
		this.mWiserActivity = mWiserActivity;
		this.context = mWiserActivity;
	}

	public void initUI(WISERFragment mWiserFragment) {
		initUI((WISERActivity) mWiserFragment.getActivity());
		this.state = STATE_FRAGMENT;
		this.mWiserFragment = mWiserFragment;
	}

	public Context context() {
		return context;
	}

	public <A extends WISERActivity> A activity() {
		return (A) mWiserActivity;
	}

	public FragmentManager manager() {
		return WISERHelper.getActivityManage().getCurrentActivity().getSupportFragmentManager();
	}

	public <F extends WISERFragment> F fragment() {
		return (F) mWiserFragment;
	}

	public <B extends WISERBiz> B biz() {
		B b = null;
		switch (state) {
			case STATE_ACTIVITY:
				b = (B) mWiserActivity.biz();
				break;
			case STATE_FRAGMENT:
				b = (B) mWiserFragment.biz();
				break;
		}
		return b;
	}

	public <E extends IWISERDisplay> E display() {
		E e = null;
		switch (state) {
			case STATE_ACTIVITY:
				e = (E) mWiserActivity.display();
				break;
			case STATE_FRAGMENT:
				e = (E) mWiserFragment.display();
				break;
		}
		return e;
	}

	/**
	 * 消除引用
	 */
	public void detach() {
		this.state = 0;
		this.mWiserActivity = null;
		this.mWiserFragment = null;
		this.context = null;
	}
}
