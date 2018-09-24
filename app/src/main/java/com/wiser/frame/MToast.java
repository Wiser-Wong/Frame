package com.wiser.frame;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wiser.library.manager.toast.WISERCustomToast;

public class MToast extends WISERCustomToast {

	@Override protected int toastLayoutId() {
		return R.layout.frame_toast;
	}

	@Override protected int toastDrawableBackgroundId() {
		return -1;
	}

	@Override protected void initView(View v, String msg) {
        TextView textView = v.findViewById(R.id.tv_content);
        textView.setText(msg);
	}

	@Override protected int gravity() {
		return Gravity.TOP;
	}
}
