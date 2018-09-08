package com.wiser.library.adapter;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.wiser.library.R;
import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBiz;
import com.wiser.library.base.WISERDialogFragment;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.base.WISERView;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.util.WISERCheck;
import com.wiser.library.view.FooterView;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

	private final int				TYPE_FOOTER		= 989898;		// 上拉加载TYPE

	private LayoutInflater			mInflater;

	private boolean					isFooter;						// 是否显示上拉加载

	public static final int			LOAD_RUNNING	= 1000;			// 加载中

	public static final int			LOAD_COMPLETE	= 1001;			// 加载完成

	public static final int			LOAD_END		= 1002;			// 加载结束

	private int						loadState		= LOAD_COMPLETE;

	private String					loadTip;						// 加载提示

	private FooterCustomListener	footerCustomListener;

	/**
	 * 数据
	 */
	private List					mItems;

	private WISERView				wiserView;

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
		if (getItemCount() > 0) notifyItemChanged(getItemCount() - 1);
	}

	public int getLoadState() {
		return loadState;
	}

	public void loadTip(String loadTip) {
		this.loadTip = loadTip;
	}

	private class FooterHolder extends WISERHolder {

		FooterView footerView;

		FooterHolder(@NonNull View itemView) {
			super(itemView);
			this.footerView = (FooterView) itemView;
			if (wiserView != null && wiserView.getFooterModel() != null) {
				// 定制footer
				if (wiserView.getFooterModel().footerLayoutId > 0) {
					if (footerCustomListener != null) {
						this.footerView.removeAllViews();
						this.footerView.addView(inflate(footerView, wiserView.getFooterModel().footerLayoutId));
					}
				}
			}
		}

		@Override public void bindData(Object o, int position) {
			if (wiserView != null && wiserView.getFooterModel() != null) {
				// footerView 定制footer
				if (wiserView.getFooterModel().footerLayoutId > 0) {
					if (footerCustomListener != null) footerCustomListener.footerListener(footerView, loadState);
					else {
						footerThemeChange(footerView);
						footerLoadState(footerView);
					}
				} else {// 默认footer
					footerThemeChange(footerView);
					footerLoadState(footerView);
				}
			} else {
				footerLoadState(footerView);
			}

		}
	}

	// footer 属性变化
	private void footerThemeChange(FooterView footerView) {
		// 文本颜色
		if (wiserView.getFooterModel().textColor != 0) footerView.text().setTextColor(wiserView.getFooterModel().textColor);
		// loading颜色
		if (wiserView.getFooterModel().barColor != 0) footerView.setBarColor(wiserView.getFooterModel().barColor);
		// 背景色
		if (wiserView.getFooterModel().backgroundColor != 0) footerView.setBackgroundColor(wiserView.getFooterModel().backgroundColor);
		// 间距
		if (wiserView.getFooterModel().leftPadding == 0 || wiserView.getFooterModel().topPadding == 0 || wiserView.getFooterModel().rightPadding == 0
				|| wiserView.getFooterModel().bottomPadding == 0) {
			footerView.setPadding(wiserView.getFooterModel().leftPadding, wiserView.getFooterModel().topPadding, wiserView.getFooterModel().rightPadding, wiserView.getFooterModel().bottomPadding);
		}
	}

	// footer判断
	private void footerLoadState(FooterView footerView) {
		switch (loadState) {
			case LOAD_RUNNING:// 加载中
				footerView.setVisibility(View.VISIBLE);
				footerView.bar().setVisibility(View.VISIBLE);
				// 文本
				if (StringUtils.isBlank(loadTip)) footerView.text().setText(activity().getResources().getText(R.string.load_running));
				else footerView.text().setText(loadTip);
				break;
			case LOAD_COMPLETE:// 加载完成
				footerView.setVisibility(View.GONE);
				break;
			case LOAD_END:// 加载结束
				footerView.setVisibility(View.VISIBLE);
				footerView.bar().setVisibility(View.GONE);
				if (StringUtils.isBlank(loadTip)) footerView.text().setText(activity().getResources().getText(R.string.load_end));
				else footerView.text().setText(loadTip);
				break;
			default:
				break;
		}
	}

	@Override public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
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
		if (isStaggerManage(holder)) handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
	}

	// 判断是否GridLayoutManage
	private boolean isGridLayoutManage(@NonNull RecyclerView recyclerView) {
		return recyclerView.getLayoutManager() instanceof GridLayoutManager;
	}

	// 判断是否瀑布流管理器
	private boolean isStaggerManage(WISERHolder holder) {
		ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
		return layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams;
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

	public void setFooterCustomListener(FooterCustomListener footerCustomListener) {
		this.footerCustomListener = footerCustomListener;
	}

	public interface FooterCustomListener {

		void footerListener(FooterView footerView, int state);
	}

}
