package com.wiser.frame.slidingmenu;

import com.wiser.frame.R;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

import android.os.Bundle;

public class RightMenuFragment extends WISERFragment {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.right_menu_frg);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {

	}

}
