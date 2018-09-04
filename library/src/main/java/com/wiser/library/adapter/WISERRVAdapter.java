package com.wiser.library.adapter;

import java.util.List;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBiz;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.base.WISERView;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.util.WISERCheck;
import com.wiser.library.view.FooterView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

	public final int		LOAD_RUNNING	= 1000;			// 加载中

	public final int		LOAD_COMPLETE	= 1001;			// 加载完成

	public final int		LOAD_END		= 1002;			// 加载结束

	private int				loadState		= LOAD_RUNNING;

	/**
	 * 数据
	 */
	private List			mItems;

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

	public void isFooter(boolean isFooter) {
		this.isFooter = isFooter;
	}

	public void setItems(List mItems) {
		this.mItems = mItems;
		notifyDataSetChanged();
	}

	public List getItems() {
		return mItems;
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public void addItem(int position, Object object) {
		if (object == null || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.add(position, object);
		notifyItemInserted(position);
	}

	public void addList(int position, List list) {
		if (list == null || list.size() < 1 || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.addAll(position, list);
		notifyItemRangeInserted(position, list.size());
	}

	public void addList(List list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.addAll(list);
		notifyItemRangeInserted(position, list.size());
	}

	public void delete(int position) {
		if (getItems() == null || position < 0 || getItems().size() < position) {
			return;
		}
		mItems.remove(position);
		notifyItemRemoved(position);
	}

	public void delete(List list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.removeAll(list);
		notifyItemRangeRemoved(position, list.size());
	}

	public void delete(int position, List list) {
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
	 * @param clazz
	 *            参数
	 * @return 返回值
	 */
	public <U> U findFragment(Class<U> clazz) {
		WISERCheck.checkNotNull(clazz, "class不能为空");
		return (U) wiserView.manager().findFragmentByTag(clazz.getSimpleName());
	}

	public WISERView wiserView() {
		return wiserView;
	}

	public <B extends WISERBiz> B biz() {
		return wiserView.biz();
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
				return (V) new FooterHolder(new FooterView(activity()));
			} else return newViewHolder(viewGroup, viewType);
		} else {
			return newViewHolder(viewGroup, viewType);
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
		notifyDataSetChanged();
	}

	public int getLoadState() {
		return loadState;
	}

	private class FooterHolder extends WISERHolder {

		FooterView footerView;

		public FooterHolder(@NonNull View itemView) {
			super(itemView);
			this.footerView = (FooterView) itemView;
		}

		@Override public void bindData(Object o, int position) {
			switch (loadState) {
				case LOAD_RUNNING:// 加载中
					footerView.bar().setVisibility(View.VISIBLE);
					footerView.text().setText("正在加载...");
					break;
				case LOAD_COMPLETE:// 加载完成
					break;
				case LOAD_END:// 加载结束
					footerView.bar().setVisibility(View.GONE);
					footerView.text().setText("已经到底了");
					break;
				default:
					break;
			}
		}
	}
}
