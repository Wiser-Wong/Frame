package com.wiser.frame;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.helper.WISERHelper;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class SecondActivity extends WISERActivity<ISecondBiz> {

	public static void intent() {
		WISERHelper.display().intent(SecondActivity.class);
	}

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_second);
		builder.swipeBack(true);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {

	}

	public void back(View view) {
		biz().back();
	}

	public void resetUi(View view) {
		biz().resetUi();
	}
}
