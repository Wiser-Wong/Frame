package com.wiser.library.adapter;

import java.util.List;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.wiser.library.R;
import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBiz;
import com.wiser.library.base.WISERDialogFragment;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.base.WISERView;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.util.WISERCheck;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Wiser
 * @param <V>
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class WISERRVAdapter<T, V extends WISERHolder> extends RecyclerView.Adapter<V> {

	private final int		TYPE_FOOTER		= 989898;		// 上拉加载TYPE

	private LayoutInflater	mInflater;

	private boolean			isFooter;						// 是否显示上拉加载

	public static final int	LOAD_RUNNING	= 1000;			// 加载中

	public static final int	LOAD_COMPLETE	= 1001;			// 加载完成

	public static final int	LOAD_END		= 1002;			// 加载结束

	private int				loadState		= LOAD_COMPLETE;

	private String			loadTip;						// 加载提示

	/**
	 * 数据
	 */
	private List<T>			mItems;

	private WISERView		wiserView;

	public WISERRVAdapter(WISERActivity mWiserActivity) {
		WISERCheck.checkNotNull(mWiserActivity, "View层不存在");
		wiserView = mWiserActivity.wiserView();
		this.mInflater = LayoutInflater.from(mWiserActivity);
	}

	public WISERRVAdapter(WISERFragment mWiserFragment) {
		WISERCheck.checkNotNull(mWiserFragment, "View层不存在");
		wiserView = mWiserFragment.wiserView();
		this.mInflater = LayoutInflater.from(wiserView.activity());
	}

	public WISERRVAdapter(WISERDialogFragment mWiserDialogFragment) {
		WISERCheck.checkNotNull(mWiserDialogFragment, "View层不存在");
		wiserView = mWiserDialogFragment.wiserView();
		this.mInflater = LayoutInflater.from(wiserView.activity());
	}

	// WiserHolder对象
	public abstract V newViewHolder(ViewGroup viewGroup, int type);

	public WISERRVAdapter getAdapter() {
		return this;
	}

	protected View inflate(ViewGroup viewGroup, int layoutId) {
		return mInflater.inflate(layoutId, viewGroup, false);
	}

	public WISERActivity activity() {
		return wiserView.activity();
	}

	public WISERFragment fragment() {
		return wiserView.fragment();
	}

	public void isFooter(boolean isFooter) {
		this.isFooter = isFooter;
	}

	public void setItems(List<T> mItems) {
		this.mItems = mItems;
		notifyDataSetChanged();
	}

	public List<T> getItems() {
		return mItems;
	}

	public T getItem(int position) {
		if (mItems == null) return null;
		return mItems.get(position);
	}

	public void addItem(int position, T t) {
		if (t == null || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.add(position, t);
		notifyItemInserted(position);
	}

	public void addList(int position, List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.addAll(position, list);
		notifyItemRangeInserted(position, list.size());
	}

	public void addList(List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.addAll(list);
		notifyItemRangeInserted(position, list.size());
	}

	public void updateList(int position, T t) {
		getItems().set(position, t);
		notifyItemChanged(position);
	}

	public void delete(int position) {
		if (getItems() == null || position < 0 || getItems().size() < position) {
			return;
		}
		mItems.remove(position);
		notifyItemRemoved(position);
	}

	public void delete(List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.removeAll(list);
		notifyItemRangeRemoved(position, list.size());
	}

	public void delete(int position, List<T> list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		mItems.removeAll(list);
		notifyItemRangeRemoved(position, list.size());
	}

	public void clear() {
		mItems.clear();
		notifyDataSetChanged();
	}

	/**
	 * 获取fragment
	 *
	 * @param <U>
	 *            参数
	 * @param clazzName
	 *            参数
	 * @return 返回值
	 */
	public <U> U findFragment(String clazzName) {
		if (WISERCheck.isEmpty(clazzName)) return null;
		return (U) wiserView.manager().findFragmentByTag(clazzName);
	}

	public WISERView wiserView() {
		return wiserView;
	}

	public <B extends WISERBiz> B biz(Class<B> clazz) {
		return wiserView.biz(clazz);
	}

	/**
	 * 获取调度
	 *
	 * @param <E>
	 *            参数
	 * @return 返回值
	 */
	protected <E extends IWISERDisplay> E display() {
		return wiserView.display();
	}

	@Override public int getItemViewType(int position) {
		if (isFooter) {
			if (position + 1 == getItemCount()) return TYPE_FOOTER;
		}
		return super.getItemViewType(position);
	}

	@NonNull @Override public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
		if (isFooter) {
			if (viewType == TYPE_FOOTER) {
				if (wiserView != null && wiserView.getFooterModel() != null && wiserView.getFooterModel().footerLayoutId > 0) {
					V v = (V) new FooterHolder(inflate(viewGroup, wiserView.getFooterModel().footerLayoutId));
					v.setContext(activity());
					v.setAdapter(this);
					return v;
				}
				V v = (V) new FooterHolder(inflate(viewGroup, R.layout.footer_layout));
				v.setContext(activity());
				v.setAdapter(this);
				return v;
			} else {
				V v = newViewHolder(viewGroup, viewType);
				v.setContext(activity());
				v.setAdapter(this);
				return v;
			}
		} else {
			V v = newViewHolder(viewGroup, viewType);
			v.setContext(activity());
			v.setAdapter(this);
			return v;
		}
	}

	@Override public void onBindViewHolder(@NonNull V v, int position) {
		if (isFooter) {
			if (position == getItemCount() - 1) {
				v.bindData(null, position);
			} else v.bindData(getItem(position), position);
		} else {
			v.bindData(getItem(position), position);
		}
	}

	@Override public int getItemCount() {
		if (isFooter) return mItems == null ? 0 : mItems.size() + 1;
		return mItems == null ? 0 : mItems.size();
	}

	public void loadState(int loadState) {
		this.loadState = loadState;
		if (getItemCount() > 0) notifyItemChanged(getItemCount() - 1);
	}

	public int getLoadState() {
		return loadState;
	}

	public void loadTip(String loadTip) {
		this.loadTip = loadTip;
	}

	private class FooterHolder extends WISERHolder {

		FooterHolder(@NonNull View itemView) {
			super(itemView);
		}

		@Override public void bindData(Object o, int position) {
			if (wiserView != null && wiserView.getFooterModel() != null) {
				// footerView 定制footer
				if (wiserView.getFooterModel().footerLayoutId > 0) {
					if (wiserView.getFooterModel().onFooterCustomListener != null) wiserView.getFooterModel().onFooterCustomListener.footerListener(itemView, loadState);
					else {
						footerLoadState(itemView);
					}
				} else {// 默认footer
					footerLoadState(itemView);
				}
			} else {
				footerLoadState(itemView);
			}

		}
	}

	// footer判断
	private void footerLoadState(View footerView) {
		TextView tvFooterTip = footerView.findViewById(R.id.tv_footer_tip);
		CircularProgressView loadingFooter = footerView.findViewById(R.id.loading_progress_footer);
		switch (loadState) {
			case LOAD_RUNNING:// 加载中
				footerView.setVisibility(View.VISIBLE);
				// 文本
				if (WISERCheck.isEmpty(loadTip)) tvFooterTip.setText(activity().getResources().getText(R.string.load_running));
				else tvFooterTip.setText(loadTip);
				break;
			case LOAD_COMPLETE:// 加载完成
				footerView.setVisibility(View.GONE);
				break;
			case LOAD_END:// 加载结束
				footerView.setVisibility(View.VISIBLE);
				loadingFooter.setVisibility(View.GONE);
				if (WISERCheck.isEmpty(loadTip)) tvFooterTip.setText(activity().getResources().getText(R.string.load_end));
				else tvFooterTip.setText(loadTip);
				break;
			default:
				break;
		}
	}

	@Override public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		if (!isFooter) return;
		if (!isGridLayoutManage(recyclerView)) return;
		final GridLayoutManager gridManager = ((GridLayoutManager) recyclerView.getLayoutManager());
		assert gridManager != null;
		gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {

			@Override public int getSpanSize(int position) {
				return position + 1 == getItemCount() ? gridManager.getSpanCount() : 1;
			}
		});
	}

	@Override public void onViewAttachedToWindow(@NonNull WISERHolder holder) {
		super.onViewAttachedToWindow((V) holder);
		if (!isFooter) return;
		if (isStaggerManage(holder)) handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
	}

	// 判断是否GridLayoutManage
	private boolean isGridLayoutManage(@NonNull RecyclerView recyclerView) {
		return recyclerView.getLayoutManager() instanceof GridLayoutManager;
	}

	// 判断是否瀑布流管理器
	private boolean isStaggerManage(WISERHolder holder) {
		ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
		return layoutParams instanceof StaggeredGridLayoutManager.LayoutParams;
	}

	// 如果是footer,则占满
	private void handleLayoutIfStaggeredGridLayout(WISERHolder holder, int position) {
		try {
			if (getItem(position) == null) {
				StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
				p.setFullSpan(true);
			}
		} catch (IndexOutOfBoundsException e) {
			StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
			p.setFullSpan(true);
		}
	}

	public interface OnFooterCustomListener {

		void footerListener(View footerView, int state);
	}

}
