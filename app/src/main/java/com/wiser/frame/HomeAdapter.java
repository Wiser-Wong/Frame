package com.wiser.frame;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wiser.library.adapter.WISERHolder;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERActivity;

import butterknife.BindView;

public class HomeAdapter extends WISERRVAdapter<ABean, WISERHolder> {

	public HomeAdapter(WISERActivity mWiserActivity) {
		super(mWiserActivity);
	}

	@Override public WISERHolder newViewHolder(ViewGroup viewGroup, int type) {
		return new HomeHolder(inflate(viewGroup, R.layout.home_item));
	}

	public class HomeHolder extends WISERHolder<ABean> {

		@BindView(R.id.tv_age) TextView tvAge;

		public HomeHolder(@NonNull View itemView) {
			super(itemView);
		}

		@Override public void bindData(ABean aBean, int position) {
			if (aBean != null) tvAge.setText(aBean.age);
		}
	}
}
