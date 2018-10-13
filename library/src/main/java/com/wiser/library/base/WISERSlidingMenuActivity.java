package com.wiser.library.base;

import com.wiser.library.R;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.helper.WISERSlidingMenuHelper;
import com.wiser.library.view.slidingmenu.SlidingMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * @author Wiser
 * @version 版本
 */
public abstract class WISERSlidingMenuActivity extends WISERActivity implements IWISERSlidingMenuActivity {

	private WISERSlidingMenuHelper mHelper;

	/**
	 * Build Activity属性
	 *
	 * @param builder
	 * @return
	 */
	protected abstract WISERBuilder buildSlidingMenu(WISERBuilder builder);

	/**
	 * 初始化数据
	 *
	 * @param intent
	 */
	protected abstract void initSlidingMenuData(Intent intent);

	/**
	 * slidingMenu 配置属性
	 *
	 * @param slidingMenu
	 */
	protected abstract void slidingMenuSetting(SlidingMenu slidingMenu);

	/**
	 * slidingMenu mode
	 * 
	 * @return
	 */
	protected abstract int slidingMenuMode();

	/**
	 * 左侧Fragment
	 */
	protected abstract void setLeftMenuFragment(boolean isModeLeft);

	/**
	 * 主内容Fragment
	 */
	protected abstract void setContentMenuFragment();

	/**
	 * 右侧Fragment
	 *
	 * @param isModeRight
	 */
	protected abstract void setRightMenuFragment(boolean isModeRight);

	@Override protected WISERBuilder build(WISERBuilder builder) {
		mHelper = new WISERSlidingMenuHelper(this);
		mHelper.initSlidingMenuView();
		return buildSlidingMenu(builder);
	}

	@Override protected void initData(Intent intent) {
		mHelper.registerAboveContentView(mInflater.inflate(builder().getLayoutId(), null));
		slidingMenuSetting(slidingMenu());
		slidingMenu().setMode(slidingMenuMode());
		if (slidingMenu().getMode() == SlidingMenu.LEFT || slidingMenu().getMode() == SlidingMenu.LEFT_RIGHT) {
			setLeftMenuViewLayoutId(R.layout.left_frame);
			setLeftMenuFragment(slidingMenu().getMode() == SlidingMenu.LEFT || slidingMenu().getMode() == SlidingMenu.LEFT_RIGHT);
		}
		setContentMenuFragment();
		if (slidingMenu().getMode() == SlidingMenu.RIGHT || slidingMenu().getMode() == SlidingMenu.LEFT_RIGHT) {
			slidingMenu().setSecondaryMenu(R.layout.right_frame);
			setRightMenuFragment(slidingMenu().getMode() == SlidingMenu.RIGHT || slidingMenu().getMode() == SlidingMenu.LEFT_RIGHT);
		}
		initSlidingMenuData(intent);
	}

	/**
	 * 设置左右 主内容Fragment Menu
	 *
	 * @param layoutId
	 * @param fragment
	 */
	protected void setMenuFragment(int layoutId, Fragment fragment) {
		WISERHelper.display().commitReplace(layoutId, fragment);
	}

	/**
	 * 设置左右 主内容Fragment Menu
	 *
	 * @param layoutId
	 * @param fragment
	 * @param tag
	 */
	protected void setMenuFragment(int layoutId, Fragment fragment, String tag) {
		WISERHelper.display().commitReplace(layoutId, fragment, tag);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	@Override public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#findViewById(int)
	 */
	@Override public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null) return v;
		return mHelper.findViewById(id);
	}

	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.
	 * Bundle)
	 */
	@Override protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mHelper.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#setContentView(int)
	 */
	@Override public void setContentView(int id) {
		setContentView(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#setContentView(android.view.View)
	 */
	@Override public void setContentView(View v) {
		setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#setContentView(android.view.View,
	 * android.view.ViewGroup.LayoutParams)
	 */
	@Override public void setContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
		mHelper.registerAboveContentView(v);
	}

	/*
	 * (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setBehindContentView(int)
	 */
	public void setLeftMenuViewLayoutId(int id) {
		setLeftMenuViewLayoutId(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setBehindContentView(android.view.View)
	 */
	public void setLeftMenuViewLayoutId(View v) {
		setLeftMenuViewLayoutId(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setBehindContentView(android.view.View, android.view.ViewGroup.LayoutParams)
	 */
	public void setLeftMenuViewLayoutId(View v, LayoutParams params) {
		mHelper.setLeftMenuViewLayoutId(v, params);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#getSlidingMenu()
	 */
	public SlidingMenu slidingMenu() {
		return mHelper.getSlidingMenu();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#toggle()
	 */
	public void toggle() {
		mHelper.toggle();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showAbove()
	 */
	public void showContent() {
		mHelper.showContent();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showBehind()
	 */
	public void showMenu() {
		mHelper.showMenu();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#showSecondaryMenu
	 * ()
	 */
	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase#
	 * setSlidingActionBarEnabled(boolean)
	 */
	public void setSlidingActionBarEnabled(boolean b) {
		mHelper.setSlidingActionBarEnabled(b);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b) return b;
		return super.onKeyUp(keyCode, event);
	}

}
