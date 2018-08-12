package com.wiser.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.wiser.library.helper.WISERHelper;
import com.wiser.library.model.WISERActivityModel;
import com.wiser.library.model.WISERStructureModel;
import com.wiser.library.util.WISERGenericSuperclass;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public abstract class WISERActivity<B extends IWISERBiz> extends AppCompatActivity implements IWISERView {

	private B					b;

	private WISERBuilder		mWiserBuilder;

	private WISERStructureModel	structureModel;

	private WISERActivityModel	activityModel;

	protected abstract WISERBuilder build(WISERBuilder builder);

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater mInflater = LayoutInflater.from(this);
		// 创建构建类
		mWiserBuilder = new WISERBuilder(this, mInflater);
		// 创建Biz储存对象
		structureModel = new WISERStructureModel(biz());
		// Activity管理model
		activityModel = new WISERActivityModel(this, false);
		// 管理Activity
		WISERHelper.getActivityManage().addActivity(activityModel);
		// 管理Biz
		WISERHelper.getBizManage().attach(structureModel);
		// 填充视图
		setContentView(build(mWiserBuilder).createView());

		// 状态栏高度
		mWiserBuilder.systemBarWindow();
		// 状态栏颜色
		mWiserBuilder.systemBarColor();

		// 初始化所有组件
		ButterKnife.bind(this);
		// 将Activity对应的实例传给biz
		biz().initActivity(this);
		// 初始化Biz数据
		biz().initBiz(savedInstanceState);
		// 初始化数据
		initData(savedInstanceState);
	}

	public abstract void initData(Bundle savedInstanceState);

	/**
	 * 通过对应类获取实例
	 *
	 * @param classB
	 * @return
	 */
	public B biz(Class classB) {
		try {
			return (B) classB.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("WISER架构:获取biz对象失败");
		}
	}

	/**
	 * 获取泛型实例
	 *
	 * @return
	 */
	public B biz() {
		try {
			if (b == null) b = (B) WISERGenericSuperclass.getActualTypeArgument(this.getClass()).newInstance();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("WISER架构:获取biz对象失败");
		}
	}

	// 显示加载动画
	@Override public void showLoading() {

	}

	// 隐藏加载动画
	@Override public void hideLoading() {

	}

	// 显示错误布局
	@Override public void showErr() {

	}

	// 获取上下文
	@Override public Context getContext() {
		return WISERActivity.this;
	}

	@Override protected void onResume() {
		super.onResume();
		// 更改Activity为运行状态
		WISERHelper.getActivityManage().onResume(this);
		// 打印存在Activity的日志
		WISERHelper.getActivityManage().logActivityList();
	}

	@Override protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			// 结束当前Activity
			WISERHelper.getActivityManage().finishActivity(activityModel);
			// 销毁Activity对应的Biz实例
			WISERHelper.getBizManage().detach(structureModel);
		}
		// 更改Activity为暂停状态
		WISERHelper.getActivityManage().onPause(this);
	}

	@Override protected void onDestroy() {
		super.onDestroy();
	}
}
