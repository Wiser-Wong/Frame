package com.wiser.frame;

import android.os.Bundle;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.helper.WISERHelper;

public class ThirdActivity extends WISERActivity {

	public static void intent() {
		WISERHelper.display().intent(ThirdActivity.class);
	}

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.tintFitsSystem(true);
		builder.layoutId(R.layout.activity_third);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {
		WISERHelper.display().commitReplace(R.id.fl_third, new ThirdFragment());
		if (WISERHelper.isExistBiz(IHomeBiz.class)) WISERHelper.biz(IHomeBiz.class).bizMethod("哈哈哈");
	}
}
