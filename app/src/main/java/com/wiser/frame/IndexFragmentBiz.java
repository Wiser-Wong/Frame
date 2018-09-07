package com.wiser.frame;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.wiser.library.base.WISERBiz;
import com.wiser.library.util.WISERApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IndexFragmentBiz extends WISERBiz<IndexFragment> {

	public List<IndexModel> indexModels;

	public void addAdapterData() {
		indexModels = new ArrayList<>();
		// for (int i = 0; i < 20; i++) {
		// IndexModel model = new IndexModel();
		// model.age = "年龄：-->>" + i;
		// model.height = (int) (300+Math.random()*400);
		// indexModels.add(model);
		// }
		IndexModel indexModel1 = new IndexModel();
		indexModel1.age = "年龄1";
		indexModel1.photoUrl = "http://img07.tooopen.com/images/20170316/tooopen_sy_201956178977.jpg";
		indexModel1.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel1.density = new Random().nextInt(3) % 3 + 1;
		indexModel1.height = (int) (indexModel1.width * indexModel1.density);
		IndexModel indexModel2 = new IndexModel();
		indexModel2.age = "年龄2";
		indexModel2.photoUrl = "http://img03.tooopen.com/uploadfile/downs/images/20110714/sy_20110714135215645030.jpg";
		indexModel2.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel2.density = new Random().nextInt(3) % 3 + 1;
		indexModel2.height = (int) (indexModel2.width * indexModel2.density);
		IndexModel indexModel3 = new IndexModel();
		indexModel3.age = "年龄3";
		indexModel3.photoUrl = "http://img.sccnn.com/bimg/338/27467.jpg";
		indexModel3.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel3.density = new Random().nextInt(3) % 3 + 1;
		indexModel3.height = (int) (indexModel3.width * indexModel3.density);
		IndexModel indexModel4 = new IndexModel();
		indexModel4.age = "年龄4";
		indexModel4.photoUrl = "http://img3.imgtn.bdimg.com/it/u=3033257042,4077893909&fm=26&gp=0.jpg";
		indexModel4.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel4.density = new Random().nextInt(3) % 3 + 1;
		indexModel4.height = (int) (indexModel4.width * indexModel4.density);
		IndexModel indexModel5 = new IndexModel();
		indexModel5.age = "年龄5";
		indexModel5.photoUrl = "http://img.liuxue86.com/2015/0906/20150906043546913.jpg";
		indexModel5.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel5.density = new Random().nextInt(3) % 3 + 1;
		indexModel5.height = (int) (indexModel5.width * indexModel5.density);
		IndexModel indexModel6 = new IndexModel();
		indexModel6.age = "年龄6";
		indexModel6.photoUrl = "http://img.zcool.cn/community/01edc555bcbc4132f87528a1d8e5f2.jpg";
		indexModel6.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel6.density = new Random().nextInt(3) % 3 + 1;
		indexModel6.height = (int) (indexModel6.width * indexModel6.density);
		IndexModel indexModel7 = new IndexModel();
		indexModel7.age = "年龄7";
		indexModel7.photoUrl = "http://img1.imgtn.bdimg.com/it/u=1285223677,2206130472&fm=26&gp=0.jpg";
		indexModel7.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel7.density = new Random().nextInt(3) % 3 + 1;
		indexModel7.height = (int) (indexModel7.width * indexModel7.density);
		IndexModel indexModel8 = new IndexModel();
		indexModel8.age = "年龄8";
		indexModel8.photoUrl = "http://img1.imgtn.bdimg.com/it/u=1285223677,2206130472&fm=26&gp=0.jpg";
		indexModel8.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel8.density = new Random().nextInt(3) % 3 + 1;
		indexModel8.height = (int) (indexModel8.width * indexModel8.density);
		IndexModel indexModel9 = new IndexModel();
		indexModel9.age = "年龄9";
		indexModel9.photoUrl = "http://img1.imgtn.bdimg.com/it/u=1285223677,2206130472&fm=26&gp=0.jpg";
		indexModel9.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel9.density = new Random().nextInt(3) % 3 + 1;
		indexModel9.height = (int) (indexModel9.width * indexModel9.density);
		IndexModel indexModel10 = new IndexModel();
		indexModel10.age = "年龄10";
		indexModel10.photoUrl = "http://img1.imgtn.bdimg.com/it/u=1285223677,2206130472&fm=26&gp=0.jpg";
		indexModel10.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel10.density = new Random().nextInt(3) % 3 + 1;
		indexModel10.height = (int) (indexModel10.width * indexModel10.density);
		IndexModel indexModel11 = new IndexModel();
		indexModel11.age = "年龄11";
		indexModel11.photoUrl = "http://img1.imgtn.bdimg.com/it/u=1285223677,2206130472&fm=26&gp=0.jpg";
		indexModel11.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel11.density = new Random().nextInt(3) % 3 + 1;
		indexModel11.height = (int) (indexModel11.width * indexModel11.density);
		IndexModel indexModel12 = new IndexModel();
		indexModel12.age = "年龄12";
		indexModel12.width = WISERApp.getScreenWidth() / 2 - WISERApp.px2dip(20);
		indexModel12.photoUrl = "http://img1.imgtn.bdimg.com/it/u=1285223677,2206130472&fm=26&gp=0.jpg";
		indexModel12.density = new Random().nextInt(3) % 3 + 1;
		indexModel12.height = (int) (indexModel12.width * indexModel12.density);
		indexModels.add(indexModel1);
		indexModels.add(indexModel2);
		indexModels.add(indexModel3);
		indexModels.add(indexModel4);
		indexModels.add(indexModel5);
		indexModels.add(indexModel6);
		indexModels.add(indexModel7);
		indexModels.add(indexModel8);
		indexModels.add(indexModel9);
		indexModels.add(indexModel10);
		indexModels.add(indexModel11);
		indexModels.add(indexModel12);
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
