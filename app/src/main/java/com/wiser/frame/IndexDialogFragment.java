package com.wiser.frame;

import com.wiser.library.base.WISERDialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

public class IndexDialogFragment extends WISERDialogFragment {

	@BindView(R.id.tv_dialog) TextView textView;

	public static WISERDialogFragment newInstance() {
		return new IndexDialogFragment();
	}

	@Override protected View createDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_dialog, container, false);
	}

	@Override protected void initData(Bundle savedInstanceState) {}

	@Override protected int dialogWeight() {
		return CENTER;
	}

	@Override protected int dialogTheme() {
		return 0;
	}

	@Override protected boolean isWidthFullScreen() {
		return false;
	}

	@Override protected boolean isCloseOnTouchOutside() {
		return true;
	}

}
