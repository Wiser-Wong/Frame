package com.wiser.library.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.wiser.library.adapter.WISERRVAdapter;

/**
 * @author Wiser
 *         <p>
 *         上拉加载滚动监听
 */
public class IWISERRVScrollListener extends RecyclerView.OnScrollListener {

	private OnLoadMoreListener	onLoadMoreListener;

	private WISERView			wiserView;

	public IWISERRVScrollListener(OnLoadMoreListener onLoadMoreListener, WISERView wiserView) {
		this.onLoadMoreListener = onLoadMoreListener;
		this.wiserView = wiserView;
	}

	// 用来标记是否正在向上滑动
	private boolean isSlidingUpward = false;

	@Override public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
		super.onScrollStateChanged(recyclerView, newState);
		LinearLayoutManager linearLayoutManager = null;
		StaggeredGridLayoutManager staggeredGridLayoutManager = null;
		int[] lastPositions;
		boolean isStaggered;
		if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
			isStaggered = true;
			staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
			staggeredGridLayoutManager.invalidateSpanAssignments(); // 防止第一行到顶部有空白区域
		} else {
			isStaggered = false;
			linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
		}
		// 当不滑动时
		if (newState == RecyclerView.SCROLL_STATE_IDLE) {
			if (isStaggered) {
				// 获取最后一个完全显示的itemPosition
				lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
				staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
				int lastItemPosition = findMax(lastPositions);
				int itemCount = staggeredGridLayoutManager.getItemCount();
				// 判断是否滑动到了最后一个item，并且是向上滑动
				if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
					// 加载更多
					if (onLoadMoreListener != null) onLoadMoreListener.onLoadMore();
				}
			} else {
				// 获取最后一个完全显示的itemPosition
				assert linearLayoutManager != null;
				int lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
				int itemCount = linearLayoutManager.getItemCount();
				// 判断是否滑动到了最后一个item，并且是向上滑动
				if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
					if (wiserView != null) {
						// 加载更多
						if (onLoadMoreListener != null) onLoadMoreListener.onLoadMore();
					}
				}
			}
		}
	}

	@Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		// 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
		isSlidingUpward = dy > 0;
	}

	private int findMax(int[] lastPositions) {
		int max = lastPositions[0];
		for (int value : lastPositions) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

    //    //判断顶部底部
//    private int judgeTopAndBottom() {
//        if (recyclerView == null) return -1;
//        //滑动到底部
//        if (!recyclerView.canScrollVertically(1)) {
//            return BOTTOM;
//        }
//        //滑动到顶部
//        if (!recyclerView.canScrollVertically(-1)) {
//            return TOP;
//        }
//        return -1;
//    }

	/**
	 * 加载更多回调
	 */
	public interface OnLoadMoreListener {

		void onLoadMore();
	}
}
