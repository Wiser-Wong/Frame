package com.wiser.frame;

import android.content.Intent;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.tab.top.SmartPageView;
import com.wiser.library.tab.top.SmartTabInfo;

import butterknife.BindView;

public class SmartActivity extends WISERActivity {

	@BindView(R.id.smart_page) SmartPageView smartPageView;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_smart);
		return builder;
	}

	@Override protected void initData(Intent intent) {
		smartPageView.setPage(new SmartTabInfo("测试1", IndexFragment.class, null), new SmartTabInfo("测试2", SecondFragment.class, null), new SmartTabInfo("测试3", ThreeFragment.class, null));
		smartPageView.setOffscreenPageLimit(3);
	}
}
