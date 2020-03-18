package com.wiser.library.view.irregular;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiser.library.R;

/**
 * @author Wiser
 * @param <T>
 * 
 *            不规则布局排版
 */
public class IrregularLayout<T> extends ViewGroup {

	private int					mUsedWidth			= 0;

	private Line				mCurrentLine;

	private ArrayList<Line> lineList			= new ArrayList<>();

	// 水平间距
	private int					mHorizontalSpacing	= dip2px(10);

	// 垂直间距
	private int					mVerticalSpacing	= dip2px(10);

	private List<T> list;

	private int					choiceLayoutId;							// item布局id

	private boolean				isFillSurplusWidth;						// 是否填充剩余宽度

	private List<View> views				= new ArrayList<>();

	private LayoutInflater mInflater;

	private OnIrregularAdapter<T> onIrregularAdapter;

	private OnItemClickListener<T> onItemClickListener;

	public IrregularLayout(Context context) {
		this(context, null);
		init(context, null);
	}

	public IrregularLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init(context, attrs);
	}

	public IrregularLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		mInflater = LayoutInflater.from(context);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IrregularLayout);
		choiceLayoutId = ta.getResourceId(R.styleable.IrregularLayout_il_layoutId, -1);
		isFillSurplusWidth = ta.getBoolean(R.styleable.IrregularLayout_il_isFillSurplusWidth, isFillSurplusWidth);
		mHorizontalSpacing = (int) ta.getDimension(R.styleable.IrregularLayout_il_horizontalSpacing,mHorizontalSpacing);
		mVerticalSpacing = (int) ta.getDimension(R.styleable.IrregularLayout_il_verticalSpacing,mVerticalSpacing);
		ta.recycle();
	}

	@SuppressLint("DrawAllocation") @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		reset();

		// 获取控件的宽度
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		widthSize = widthSize - getPaddingLeft() - getPaddingRight();
		int count = getChildCount();
		mCurrentLine = new Line();
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			// 测量给予约束，宽度最多只有屏幕的宽度
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST);
			childView.measure(childWidthMeasureSpec, 0);
			// 获取childView的宽度
			int childWidth = childView.getMeasuredWidth();
			mUsedWidth = mUsedWidth + childWidth;
			if (mUsedWidth < widthSize) {
				// 表示在同一行
				mCurrentLine.addLineView(childView);
				mUsedWidth = mUsedWidth + mHorizontalSpacing;
			} else {
				// 表示需要换行
				newLine();
				mCurrentLine.addLineView(childView);
				mUsedWidth = mUsedWidth + childWidth + mHorizontalSpacing;
			}
		}
		// 添加最后一个元素
		if (!lineList.contains(mCurrentLine)) {
			lineList.add(mCurrentLine);
		}
		int totalHeight = 0;
		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);
			totalHeight = totalHeight + line.lineHeight + mVerticalSpacing;
		}
		// 减去最后一行的高度间隔
		totalHeight = totalHeight - mVerticalSpacing;

		totalHeight = totalHeight + getPaddingTop() + getPaddingBottom();
		// 确定控件的高度
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(totalHeight, MeasureSpec.EXACTLY);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void reset() {
		lineList.clear();
		mUsedWidth = 0;
	}

	private void newLine() {
		// 把装
		lineList.add(mCurrentLine);
		// 重新创建装载这行view的对象
		mCurrentLine = new Line();
		// 把已经用的mUsedWidth置位0
		mUsedWidth = 0;
	}

	class Line {

		int						lineHeight;

		private ArrayList<View> lineViews	= new ArrayList<>();

		void addLineView(View childView) {
			if (!lineViews.contains(childView)) {
				lineViews.add(childView);
				if (lineHeight < childView.getMeasuredHeight()) {
					lineHeight = childView.getMeasuredHeight();
				}
			}
		}

		void layout(int left, int top) {
			for (int i = 0; i < lineViews.size(); i++) {
				View childView = lineViews.get(i);
				childView.layout(left, top, left + childView.getMeasuredWidth(), top + childView.getMeasuredHeight());
				left = left + childView.getMeasuredWidth() + mHorizontalSpacing;
			}
		}

		// 这个是均等份从新测量
		void reMeasure() {
			int surplusWidth;// 剩余部分宽度
			int totalWidth = 0;// 总共所占宽度
			int equalWidth = 0;// 均等份宽度
			for (int i = 0; i < lineViews.size(); i++) {
				View childView = lineViews.get(i);
				totalWidth = totalWidth + childView.getMeasuredWidth() + mHorizontalSpacing;
			}
			totalWidth = totalWidth - mHorizontalSpacing;
			surplusWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - totalWidth;
			if (isFillSurplusWidth) equalWidth = surplusWidth / lineViews.size();

			for (int i = 0; i < lineViews.size(); i++) {
				View childView = lineViews.get(i);
				int measuredWidth = childView.getMeasuredWidth();
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth + equalWidth, MeasureSpec.EXACTLY);
				int measuredHeight = childView.getMeasuredHeight();
				int measuredHeightSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST);
				childView.measure(widthMeasureSpec, measuredHeightSpec);
			}
		}
	}

	@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int top = getPaddingTop();
		int left = getPaddingLeft();
		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);
			line.reMeasure();// 在摆放之前从新测量
			line.layout(left, top);// 确定摆放的位置
			top = top + line.lineHeight + mVerticalSpacing;
		}
	}

	/**
	 * dip转换成px
	 *
	 * @param dpValue
	 * @return
	 */
	private int dip2px(float dpValue) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public void setItems(List<T> list) {
		if (list == null || list.size() == 0) return;
		this.list = list;
		removeAllViews();
		setView();

	}

	private void setView() {
		if (choiceLayoutId == -1) return;

		for (int i = 0; i < list.size(); i++) {
			final View view = mInflater.inflate(choiceLayoutId, this, false);
			addView(view);
			views.add(view);
			if (onIrregularAdapter != null) onIrregularAdapter.onCreateItemView(view, i, list.get(i));
			final int finalI = i;
			view.setOnClickListener(new OnClickListener() {

				@Override
                public void onClick(View v) {
					if (onItemClickListener != null) onItemClickListener.onItemClick(IrregularLayout.this, view, finalI, list.get(finalI));
				}
			});
		}
	}

	public void notifyItemPositionData(int position, T t) {
		if (views == null || views.size() == 0) return;
		for (int i = 0; i < views.size(); i++) {
			if (i == position) {
				if (onIrregularAdapter != null) onIrregularAdapter.onCreateItemView(views.get(i), position, t);
			}
		}
	}

	public void notifyItemPositionData(int position) {
		if (views == null || views.size() == 0) return;
		for (int i = 0; i < views.size(); i++) {
			if (i == position) {
				if (onIrregularAdapter != null) onIrregularAdapter.onCreateItemView(views.get(i), position, list != null && list.size() > 0 ? list.get(position) : null);
			}
		}
	}

	public void notifyDataSetChanged() {
		if (list == null || list.size() == 0) return;
		removeAllViews();
		setView();
	}

	public void notifyDataSetChanged(List<T> list) {
		removeAllViews();
		this.list = list;
		if (this.list == null || this.list.size() == 0) return;
		setView();
	}

	public void notifyList(List<T> list) {
		this.list = list;
	}

	public void setOnIrregularAdapter(OnIrregularAdapter<T> onIrregularAdapter) {
		this.onIrregularAdapter = onIrregularAdapter;
	}

	public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}
}
