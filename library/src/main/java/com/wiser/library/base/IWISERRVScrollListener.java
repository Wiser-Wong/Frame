package com.wiser.library.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Wiser
 * 
 *         上拉加载滚动监听
 */
public class IWISERRVScrollListener extends RecyclerView.OnScrollListener {

	private OnLoadMoreListener onLoadMoreListener;

	public IWISERRVScrollListener(OnLoadMoreListener onLoadMoreListener) {
		this.onLoadMoreListener = onLoadMoreListener;
	}

	// 用来标记是否正在向上滑动
	private boolean isSlidingUpward = false;

	@Override public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
		LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
		// 当不滑动时
		if (newState == RecyclerView.SCROLL_STATE_IDLE) {
			// 获取最后一个完全显示的itemPosition
			assert manager != null;
			int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
			int itemCount = manager.getItemCount();

			// 判断是否滑动到了最后一个item，并且是向上滑动
			if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
				// 加载更多
				if (onLoadMoreListener != null) onLoadMoreListener.onLoadMore();
			}
		}
	}

	@Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		// 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
		isSlidingUpward = dy > 0;
	}

	/**
	 * 加载更多回调
	 */
	public interface OnLoadMoreListener {

		void onLoadMore();
	}
}
