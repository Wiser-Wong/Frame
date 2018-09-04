package com.wiser.library.manager;

import java.util.Objects;

import com.wiser.library.helper.WISERHelper;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERInputManage {

	private InputMethodManager manager;

	WISERInputManage(Application application) {
		manager = (InputMethodManager) application.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	/**
	 * 隐藏软键盘
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT) public void hideSoftInput() {
		manager.hideSoftInputFromWindow(Objects.requireNonNull(WISERHelper.getActivityManage().getCurrentActivity().getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
