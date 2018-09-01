package com.wiser.frame;

import java.util.ArrayList;
import java.util.List;

import com.wiser.library.base.WISERBiz;

import android.os.Bundle;

public class IndexBiz extends WISERBiz<IndexActivity> {

	public List<IndexModel> indexModels;

	@Override public void initBiz(Bundle savedInstanceState) {
		super.initBiz(savedInstanceState);
	}

	public void addAdapterData() {
		indexModels = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			IndexModel model = new IndexModel();
			model.age = "年龄：-->>" + i;
			indexModels.add(model);
		}
		ui().setItems(indexModels);
	}

	public List<IndexModel> addNewData() {
		List<IndexModel> indexModels = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			IndexModel model = new IndexModel();
			model.age = "年龄：-->>" + i;
			indexModels.add(model);
		}
		return indexModels;
	}

}
