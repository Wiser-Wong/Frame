package com.wiser.library.loading;

import com.wiser.library.R;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERDialogFragment;
import com.wiser.library.helper.WISERHelper;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

/**
 * @author Wiser
 * 
 *         loading
 */
public class LoadingDialogFragment extends WISERDialogFragment {

	private boolean isClose;

	public static LoadingDialogFragment showLoadingDialog(boolean isClose) {
		LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean("isClose", isClose);
		loadingDialogFragment.setArguments(bundle);
		loadingDialogFragment.show(WISERHelper.getActivityManage().getCurrentActivity().getSupportFragmentManager(), LoadingDialogFragment.class.getName());
		return loadingDialogFragment;
	}

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.dialog_loading);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {
		if (getDialog() != null && getDialog().getWindow() != null) getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		if (savedInstanceState != null) {
			isClose = savedInstanceState.getBoolean("isClose", false);
		}
	}

	@Override protected int dialogWeight() {
		return CENTER;
	}

	@Override protected int dialogTheme() {
		return R.style.LoadingDialogTheme;
	}

	@Override protected boolean isWidthFullScreen() {
		return false;
	}

	@Override protected boolean isCloseOnTouchOutside() {
		return isClose;
	}

	@Override protected boolean isCloseOnTouchBack() {
		return isClose;
	}
}
