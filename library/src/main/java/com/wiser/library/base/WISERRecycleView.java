package com.wiser.library.base;

import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.util.WISERCheckUtil;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private WISERRVAdapter mAdapter;                                // RecycleView适配器

    private RecyclerView mRecycleView;                            // RecycleView实例

    private int mRecycleViewId;                            // RecycleView布局id

    private RecyclerView.LayoutManager mLayoutManager;                            // 布局管理器

    private RecyclerView.ItemAnimator mItemAnimator;                            // 动画

    public final static int NULL_DECORATION = 999;                // 空线

    public final static int DEFAULT_DECORATION = 1000;                // 默认分割线搞2px

    public final static int PHOTO_DECORATION = 1001;                // 图片分割线

    public final static int CUSTOM_DECORATION = 1002;                // 自定义高度分割线

    private int decorationType = NULL_DECORATION;    // 分割线类型

    private int mOrientation;                            // 列表方向

    private int decorationPhoto;                        // 分割线图片ID

    private int decorationHeight;                        // 分割线自定义高度

    private int decorationColor;                        // 分割线自定义颜色

    private WISERActivity activity;

    WISERRecycleView(WISERActivity activity) {
        this.activity = activity;
    }

    public void recycleViewId(int recycleViewId) {
        this.mRecycleViewId = recycleViewId;
    }

    private int getRecycleViewId() {
        return mRecycleViewId;
    }

    public void recycleAdapter(WISERRVAdapter adapter) {
        this.mAdapter = adapter;
    }

    /**
     * 不设置分割线
     *
     * @param spanCount     列数
     * @param orientation   方向
     * @param itemAnimator  动画
     * @param reverseLayout 从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
     * @param orientation         方向
     * @param isHasDefaultDivider 是否有默认的分割线
     * @param itemAnimator        动画
     * @param reverseLayout       从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
     * @param orientation       方向
     * @param itemAnimator      动画
     * @param decorationPhotoId 分割线图片
     * @param reverseLayout     从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
     * @param orientation      方向
     * @param itemAnimator     动画
     * @param decorationHeight 分割线高度
     * @param decorationColor  分割线颜色
     * @param reverseLayout    从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
     * @param orientation   方向
     * @param itemAnimator  动画
     * @param reverseLayout 从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
     * @param orientation   方向
     * @param itemAnimator  动画
     * @param reverseLayout 从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
     * @param orientation       方向
     * @param itemAnimator      动画
     * @param decorationPhotoId 分割线图片
     * @param reverseLayout     从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
     * @param orientation      方向
     * @param itemAnimator     动画
     * @param decorationHeight 分割线高度
     * @param decorationColor  分割线颜色
     * @param reverseLayout    从倒叙填充数据（true倒叙填充数据 false正序填充数据）
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
                    mRecycleView.addItemDecoration(new RecycleViewDivider(activity, mOrientation));
                    break;
                case PHOTO_DECORATION:// 图片分割线
                    mRecycleView.addItemDecoration(new RecycleViewDivider(activity, mOrientation, decorationPhoto, decorationType));
                    break;
                case CUSTOM_DECORATION:// 自定义高度分割线
                    mRecycleView.addItemDecoration(new RecycleViewDivider(activity, mOrientation, decorationHeight, decorationColor, decorationType));
                    break;
            }
            // 动画
            if (mItemAnimator != null) mRecycleView.setItemAnimator(mItemAnimator);
            mRecycleView.setAdapter(mAdapter);
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
    }
}
