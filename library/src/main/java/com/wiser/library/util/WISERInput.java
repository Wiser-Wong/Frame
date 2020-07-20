package com.wiser.library.util;

import java.util.Objects;

import com.wiser.library.helper.WISERHelper;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERInput {

	private static WISERInput	input;

	private InputMethodManager	manager;

	private WISERInput() {
		if (manager == null) manager = (InputMethodManager) WISERHelper.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	public static WISERInput getInstance() {
		synchronized (WISERInput.class) {
			if (input == null) input = new WISERInput();
		}
		return input;
	}

	/**
	 * 隐藏软键盘
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT) public void hideSoftInput() {
		manager.hideSoftInputFromWindow(Objects.requireNonNull(WISERHelper.getActivityManage().getCurrentActivity().getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftInput(View view) {
		if (view == null) return;
		manager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public void hideSoftInput(EditText editText) {
		manager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 显示软键盘
	 */
	public void showSoftInput(EditText editText) {
		manager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
	}
}
