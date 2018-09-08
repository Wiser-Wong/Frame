package com.wiser.library.base;

import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.model.WISERFooterModel;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERRecycleView {

	/**
	 * RecycleView
	 */
	private WISERRVAdapter				mAdapter;									// RecycleView适配器

	private RecyclerView				mRecycleView;								// RecycleView实例

	private int							mRecycleViewId;								// RecycleView布局id

	private boolean						isFooter;									// 是否有加载更多

	private RecyclerView.LayoutManager	mLayoutManager;								// 布局管理器

	private RecyclerView.ItemAnimator	mItemAnimator;								// 动画

	public final static int				NULL_DECORATION			= 999;				// 空线

	public final static int				DEFAULT_DECORATION		= 1000;				// 默认分割线搞2px

	public final static int				PHOTO_DECORATION		= 1001;				// 图片分割线

	public final static int				CUSTOM_DECORATION		= 1002;				// 自定义高度分割线

	public final static int				STAGGERED_DECORATION	= 1003;				// 瀑布流分割线

	private int							decorationType			= NULL_DECORATION;	// 分割线类型

	private int							mOrientation;								// 列表方向

	private int							decorationPhoto;							// 分割线图片ID

	private int							decorationHeight;							// 分割线自定义高度

	private int							decorationColor;							// 分割线自定义颜色

	private RecyclerView.ItemDecoration	itemDecoration;								// 分割线

	private WISERActivity				activity;

	private WISERFragment				fragment;

	private WISERDialogFragment			dialogFragment;

	private WISERView					wiserView;

	private int							state;

	WISERRecycleView(WISERView wiserView, int state) {
		this.state = state;
		this.wiserView = wiserView;
		this.activity = wiserView.activity();
		this.fragment = wiserView.fragment();
		this.dialogFragment = wiserView.dialogFragment();
	}

	public void recycleViewId(int recycleViewId) {
		this.mRecycleViewId = recycleViewId;
	}

	public int getRecycleViewId() {
		return mRecycleViewId;
	}

	public void recycleAdapter(WISERRVAdapter adapter) {
		this.mAdapter = adapter;
	}

	public void isFooter(boolean isFooter) {
		this.isFooter = isFooter;
	}

	public void footerLayoutId(int footerLayoutId) {
		if (wiserView != null) {
			if (wiserView.getFooterModel() == null) {
				wiserView.setFooterModel(new WISERFooterModel());
			}
			wiserView.getFooterModel().footerLayoutId = footerLayoutId;
		}
	}

	public void setFooterStyle(int backgroundColor, int barColor, int textColor) {
		if (wiserView != null) {
			if (wiserView.getFooterModel() == null) {
				wiserView.setFooterModel(new WISERFooterModel());
			}
			wiserView.getFooterModel().backgroundColor = backgroundColor;
			wiserView.getFooterModel().barColor = barColor;
			wiserView.getFooterModel().textColor = textColor;
		}
	}

	public void setFooterPadding(int leftPadding, int topPadding, int rightPadding, int bottomPadding) {
		if (wiserView != null) {
			if (wiserView.getFooterModel() == null) {
				wiserView.setFooterModel(new WISERFooterModel());
			}
			wiserView.getFooterModel().leftPadding = leftPadding;
			wiserView.getFooterModel().topPadding = topPadding;
			wiserView.getFooterModel().rightPadding = rightPadding;
			wiserView.getFooterModel().bottomPadding = bottomPadding;
		}
	}

	/**
	 * 不设置分割线
	 *
	 * @param spanCount
	 *            列数
	 * @param orientation
	 *            方向
	 * @param itemAnimator
	 *            动画
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewGridManager(int spanCount, int orientation, RecyclerView.ItemAnimator itemAnimator, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new GridLayoutManager(activity, spanCount, orientation, reverse);
		this.mItemAnimator = itemAnimator == null ? new DefaultItemAnimator() : itemAnimator;
	}

	/**
	 * 可设置分割线
	 *
	 * @param orientation
	 *            方向
	 * @param isHasDefaultDivider
	 *            是否有默认的分割线
	 * @param itemAnimator
	 *            动画
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewGridManager(int spanCount, int orientation, boolean isHasDefaultDivider, RecyclerView.ItemAnimator itemAnimator, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new GridLayoutManager(activity, spanCount, orientation, reverse);
		if (isHasDefaultDivider) this.decorationType = DEFAULT_DECORATION;
		this.mItemAnimator = itemAnimator == null ? new DefaultItemAnimator() : itemAnimator;
	}

	/**
	 * 可设置分割线图片
	 *
	 * @param orientation
	 *            方向
	 * @param itemAnimator
	 *            动画
	 * @param decorationPhotoId
	 *            分割线图片
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewGridManager(int spanCount, int orientation, RecyclerView.ItemAnimator itemAnimator, int decorationPhotoId, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new GridLayoutManager(activity, spanCount, orientation, reverse);
		this.decorationType = PHOTO_DECORATION;
		this.mItemAnimator = itemAnimator == null ? new DefaultItemAnimator() : itemAnimator;
		this.decorationPhoto = decorationPhotoId;
	}

	/**
	 * 可设置分割线图片
	 *
	 * @param orientation
	 *            方向
	 * @param itemAnimator
	 *            动画
	 * @param decorationHeight
	 *            分割线高度
	 * @param decorationColor
	 *            分割线颜色
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewGridManager(int spanCount, int orientation, RecyclerView.ItemAnimator itemAnimator, int decorationHeight, int decorationColor, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new GridLayoutManager(activity, spanCount, orientation, reverse);
		this.decorationType = CUSTOM_DECORATION;
		this.mItemAnimator = itemAnimator == null ? new DefaultItemAnimator() : itemAnimator;
		this.decorationHeight = decorationHeight;
		this.decorationColor = decorationColor;
	}

	/**
	 * 不可设置分割线默认没有分割线
	 *
	 * @param orientation
	 *            方向
	 * @param itemAnimator
	 *            动画
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewLinearManager(int orientation, RecyclerView.ItemAnimator itemAnimator, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new LinearLayoutManager(activity, orientation, reverse);
		this.mItemAnimator = itemAnimator;
	}

	/**
	 * 可设置分割线
	 *
	 * @param orientation
	 *            方向
	 * @param itemAnimator
	 *            动画
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewLinearManager(int orientation, boolean isHasDefaultDivider, RecyclerView.ItemAnimator itemAnimator, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new LinearLayoutManager(activity, orientation, reverse);
		if (isHasDefaultDivider) this.decorationType = DEFAULT_DECORATION;
		this.mItemAnimator = itemAnimator;
	}

	/**
	 * 可设置分割线图片
	 *
	 * @param orientation
	 *            方向
	 * @param itemAnimator
	 *            动画
	 * @param decorationPhotoId
	 *            分割线图片
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewLinearManager(int orientation, RecyclerView.ItemAnimator itemAnimator, int decorationPhotoId, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new LinearLayoutManager(activity, orientation, reverse);
		this.decorationType = PHOTO_DECORATION;
		this.mItemAnimator = itemAnimator;
		this.decorationPhoto = decorationPhotoId;
	}

	/**
	 * 可设置分割线自定义高度和颜色
	 *
	 * @param orientation
	 *            方向
	 * @param itemAnimator
	 *            动画
	 * @param decorationHeight
	 *            分割线高度
	 * @param decorationColor
	 *            分割线颜色
	 * @param reverseLayout
	 *            从倒叙填充数据（true倒叙填充数据 false正序填充数据）
	 */
	public void recycleViewLinearManager(int orientation, RecyclerView.ItemAnimator itemAnimator, int decorationHeight, int decorationColor, boolean... reverseLayout) {
		boolean reverse = false;
		if (reverseLayout != null && reverseLayout.length > 0) {
			reverse = reverseLayout[0];
		}
		this.mOrientation = orientation;
		this.mLayoutManager = new LinearLayoutManager(activity, orientation, reverse);
		this.decorationType = CUSTOM_DECORATION;
		this.mItemAnimator = itemAnimator;
		this.decorationHeight = decorationHeight;
		this.decorationColor = decorationColor;
	}

	/**
	 * 瀑布流
	 * 
	 * @param spanCount
	 *            列
	 * @param orientation
	 *            方向
	 * @param inversion
	 *            是否反向
	 */
	public void recycleViewStaggeredGridManager(int spanCount, int orientation, boolean inversion) {
		this.decorationType = STAGGERED_DECORATION;
		this.mLayoutManager = new StaggeredGridLayoutManager(spanCount, orientation);
		// 通过布局管理器控制条目排列的顺序 true:反向显示 false:正向显示
		((StaggeredGridLayoutManager) this.mLayoutManager).setReverseLayout(inversion);
		// 滑动中，不处理 gap 防止滑动移位
		((StaggeredGridLayoutManager) this.mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
	}

	/**
	 * 瀑布流
	 *
	 * @param spanCount
	 *            列
	 * @param orientation
	 *            方向
	 */
	public void recycleViewStaggeredGridManager(int spanCount, int orientation) {
		this.decorationType = STAGGERED_DECORATION;
		this.mLayoutManager = new StaggeredGridLayoutManager(spanCount, orientation);
		// 滑动中，不处理 gap 防止滑动移位
		((StaggeredGridLayoutManager) this.mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
	}

	/**
	 * 瀑布流
	 *
	 * @param spanCount
	 *            列
	 * @param orientation
	 *            方向
	 * @param itemDecoration
	 *            分割线
	 * @param itemAnimator
	 *            动画
	 */
	public void recycleViewStaggeredGridManager(int spanCount, int orientation, RecyclerView.ItemDecoration itemDecoration, RecyclerView.ItemAnimator itemAnimator) {
		this.decorationType = STAGGERED_DECORATION;
		this.itemDecoration = itemDecoration;
		this.mItemAnimator = itemAnimator;
		this.mLayoutManager = new StaggeredGridLayoutManager(spanCount, orientation);
		// 滑动中，不处理 gap 防止滑动移位
		((StaggeredGridLayoutManager) this.mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
	}

	/**
	 * 瀑布流
	 *
	 * @param spanCount
	 *            列
	 * @param orientation
	 *            方向
	 * @param itemDecoration
	 *            分割线
	 * @param itemAnimator
	 *            动画
	 */
	public void recycleViewStaggeredGridManager(int spanCount, int orientation, RecyclerView.ItemDecoration itemDecoration, RecyclerView.ItemAnimator itemAnimator, boolean inversion) {
		this.decorationType = STAGGERED_DECORATION;
		this.itemDecoration = itemDecoration;
		this.mItemAnimator = itemAnimator;
		this.mLayoutManager = new StaggeredGridLayoutManager(spanCount, orientation);
		// 通过布局管理器控制条目排列的顺序 true:反向显示 false:正向显示
		((StaggeredGridLayoutManager) this.mLayoutManager).setReverseLayout(inversion);
		// 滑动中，不处理 gap 防止滑动移位
		((StaggeredGridLayoutManager) this.mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
	}

	WISERRVAdapter adapter() {
		return mAdapter;
	}

	RecyclerView recyclerView() {
		return mRecycleView;
	}

	/**
	 * 创建RecycleView
	 */
	void createRecycleView(View view) {
		if (getRecycleViewId() > 0) {
			mRecycleView = ButterKnife.findById(view, getRecycleViewId());
			// WISERCheckUtil.checkNotNull(mRecycleView, "无法根据布局文件ID,获取recyclerView");
			// WISERCheckUtil.checkNotNull(mLayoutManager, "LayoutManger不能为空");
			// WISERCheckUtil.checkNotNull(mAdapter, "Adapter不能为空");
			// WISERCheckUtil.checkNotNull(activity, "activity不能为空");
			mRecycleView.setLayoutManager(mLayoutManager);
			// 分割线
			switch (decorationType) {
				case DEFAULT_DECORATION:// 默认
					mRecycleView.addItemDecoration(new WISERRVDivider(activity, mOrientation));
					break;
				case PHOTO_DECORATION:// 图片分割线
					mRecycleView.addItemDecoration(new WISERRVDivider(activity, mOrientation, decorationPhoto, decorationType));
					break;
				case CUSTOM_DECORATION:// 自定义高度分割线
					mRecycleView.addItemDecoration(new WISERRVDivider(activity, mOrientation, decorationHeight, decorationColor, decorationType));
					break;
				case STAGGERED_DECORATION:// 瀑布流分割线
					if (itemDecoration != null) mRecycleView.addItemDecoration(itemDecoration);
					break;
			}
			// 动画
			if (mItemAnimator != null) mRecycleView.setItemAnimator(mItemAnimator);
			if (mAdapter != null) {
				mRecycleView.setAdapter(mAdapter);
				if (isFooter) {
					mAdapter.isFooter(true);
					switch (state) {
						case WISERView.STATE_ACTIVITY:
							mRecycleView.addOnScrollListener(new IWISERRVScrollListener(activity, wiserView));
							break;
						case WISERView.STATE_FRAGMENT:
							mRecycleView.addOnScrollListener(new IWISERRVScrollListener(fragment, wiserView));
							break;
						case WISERView.STATE_DIALOG_FRAGMENT:
							mRecycleView.addOnScrollListener(new IWISERRVScrollListener(dialogFragment, wiserView));
							break;
					}
				}
			}
		}
	}

	// 清除
	void detach() {
		mAdapter = null;
		mRecycleView = null;
		mItemAnimator = null;
		mLayoutManager = null;
		mRecycleViewId = 0;
		activity = null;
		if (wiserView != null) wiserView.detach();
		wiserView = null;
	}
}
