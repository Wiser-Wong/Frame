package com.wiser.frame;

import java.util.ArrayList;
import java.util.List;

import com.wiser.library.base.WISERBiz;

import android.os.Bundle;

public class IndexBiz extends WISERBiz<IndexActivity> {

	public List<IndexModel> indexModels;

	public void addAdapterData() {
		indexModels = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			IndexModel model = new IndexModel();
			model.age = "年龄：-->>" + i;
			model.photoUrl = "http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg";
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
