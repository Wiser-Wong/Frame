package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.helper.WISERHelper;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class TestFragment extends WISERFragment {

	@BindView(R.id.tv_title) TextView tvTitle;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.content_frg);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {}

	@OnClick(value = { R.id.tv_title }) public void onClickView(View view) {
		switch (view.getId()) {
			case R.id.tv_title:
				// WISERHelper.display().commitBackStack(R.id.fragment2, new
				// ThreeFragment(),R.anim.slide_in_right);
				WISERHelper.display().commitBackStackAddAnim(R.id.fragment2, new ThreeFragment(), null, R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out);
				break;
		}
	}

}
