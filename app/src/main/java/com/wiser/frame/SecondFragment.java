package com.wiser.frame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class SecondFragment extends WISERFragment<ISecondFragmentBiz> {

	@BindView(R.id.btn_resetUi) Button btn_resetUi;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_second);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {

	}

	@OnClick(value = { R.id.btn_resetUi, R.id.btn_skip }) public void onViewClick(View view) {
		switch (view.getId()) {
			case R.id.btn_skip:
				biz().skip();
				break;
			case R.id.btn_resetUi:
				biz().resetUi();
				break;
		}
	}

}
