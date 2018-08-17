package com.wiser.frame;

import android.os.Bundle;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

public class IndexFragment extends WISERFragment<IndexFragmentBiz> {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_index);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {

	}
}
