package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

import android.os.Bundle;

public class ThreeFragment extends WISERFragment {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_three);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {

	}
}
