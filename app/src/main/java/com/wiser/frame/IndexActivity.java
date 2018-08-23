package com.wiser.frame;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.pager.banner.BannerPagerView;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import butterknife.BindView;

public class IndexActivity extends WISERActivity<IndexBiz> {

	// @BindView(R.id.id_viewpager) BannerPagerView mViewPager;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_index);
		builder.layoutEmptyId(R.layout.view_empty);
		builder.layoutErrorId(R.layout.view_error);
		builder.layoutLoadingId(R.layout.view_loading);
		builder.recycleView().recycleViewId(R.id.home_rlv);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.tintFitsSystem(true);
		builder.tintIs(true);
		builder.tintColor(getResources().getColor(R.color.colorPrimaryDark));
		builder.swipeBack(true);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {
		biz().addAdapterData();
	}

	// public void a() {
	//
	// List<BannerModel> models = new ArrayList<>();
	// BannerModel model = new BannerModel();
	// model.photoId =
	// "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2506796592,812786931&fm=26&gp=0.jpg";
	// BannerModel model1 = new BannerModel();
	// model1.photoId =
	// "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535004894940&di=216fb3ff6d3a4051440489d5d1f39791&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F011a5859ac137ea8012028a92fc78a.jpg%401280w_1l_2o_100sh.jpg";
	// BannerModel model2 = new BannerModel();
	// model2.photoId =
	// "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535004894940&di=e65a8b516acd36fe00b2da30b6acac3c&imgtype=0&src=http%3A%2F%2Fwww.qqma.com%2Fimgpic2%2Fcpimagenew%2F2018%2F4%2F5%2F6e1de60ce43d4bf4b9671d7661024e7a.jpg";
	// BannerModel model3 = new BannerModel();
	// model3.photoId =
	// "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3293842903,1040789920&fm=26&gp=0.jpg";
	// BannerModel model4 = new BannerModel();
	// model4.photoId =
	// "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535004894941&di=4a86f134316e4f37d7c3ef87dc9ba516&imgtype=0&src=http%3A%2F%2Fimg03.tooopen.com%2Fuploadfile%2Fdowns%2Fimages%2F20110714%2Fsy_20110714135215645030.jpg";
	// models.add(model);
	// models.add(model1);
	// models.add(model2);
	// models.add(model3);
	// models.add(model4);
	// // mViewPager.setPageTransformer(new ScaleInTransformer());
	// // BannerManage.setBanner(mViewPager,models,new BannerHolder());
	// mViewPager.setPages(this, new
	// com.wiser.library.pager.banner.BannerHolder<BannerModel>() {
	//
	// ImageView ivPhoto;
	//
	// @Override public View createView(Context context, LayoutInflater inflater,
	// ViewGroup container) {
	// ivPhoto = (ImageView) inflater.inflate(R.layout.banner_item, container,
	// false);
	// return ivPhoto;
	// }
	//
	// @Override public void bindData(Context context, int position, BannerModel
	// data) {
	// Glide.with(IndexActivity.this).load(data.photoId).asBitmap().into(ivPhoto);
	// }
	// },
	// models).startTurning(2000).setOffsetPageLimit(3).setOnItemClickListener(this).isDotRes(true,
	// R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, BannerPagerView.LEFT_DOT);
	//
	// }
	//
	// @Override public void setOnItemListener(View view, int position) {
	// WISERHelper.toast().show(position);
	// }
}
