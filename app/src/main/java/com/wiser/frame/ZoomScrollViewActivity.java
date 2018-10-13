package com.wiser.frame;

import android.content.Intent;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;

public class ZoomScrollViewActivity extends WISERActivity {

	@Override protected WISERBuilder build(WISERBuilder builder) {
	    builder.layoutId(R.layout.activity_zoom_scroll);
		return builder;
	}

	@Override protected void initData(Intent intent) {

	}
}
