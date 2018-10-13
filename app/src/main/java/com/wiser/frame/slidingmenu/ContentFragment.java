package com.wiser.frame.slidingmenu;

import com.wiser.frame.R;
import com.wiser.library.adapter.WISERHolder;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class ContentFragment extends WISERFragment {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.content_frg);
		builder.recycleView().recycleViewId(R.id.rlv_list);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new MAdapter(this));
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {

	}

	private class MAdapter extends WISERRVAdapter<String, MHolder> {

		MAdapter(WISERFragment mWiserFragment) {
			super(mWiserFragment);
		}

		@Override public MHolder newViewHolder(ViewGroup viewGroup, int type) {
			return new MHolder(inflate(viewGroup, R.layout.item));
		}

		@Override public int getItemCount() {
			return 100;
		}
	}

	public class MHolder extends WISERHolder<String> {

		MHolder(@NonNull View itemView) {
			super(itemView);
		}

		@Override public void bindData(String s, int position) {
			itemView.setOnClickListener(new View.OnClickListener() {

				@Override public void onClick(View view) {
					Toast.makeText(getActivity(), "哈哈哈", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
