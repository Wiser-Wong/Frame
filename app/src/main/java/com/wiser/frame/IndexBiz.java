package com.wiser.frame;

import android.os.Bundle;

import com.wiser.library.base.WISERBiz;

import java.util.ArrayList;
import java.util.List;

public class IndexBiz extends WISERBiz<IndexActivity> {

	@Override public void initBiz(Bundle savedInstanceState) {
		super.initBiz(savedInstanceState);
	}

	public void addAdapterData() {
		List<IndexModel> indexModels = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			IndexModel model = new IndexModel();
			model.age = "年龄：-->>" + i;
			indexModels.add(model);
		}
		ui().setItems(indexModels);
	}
}
