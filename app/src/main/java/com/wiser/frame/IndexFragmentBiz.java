package com.wiser.frame;

import com.wiser.library.base.WISERBiz;

import java.util.ArrayList;
import java.util.List;

public class IndexFragmentBiz extends WISERBiz<IndexFragment> {

	public List<IndexModel> indexModels;

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
