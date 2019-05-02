package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.tab.listener.OnTabShowCurrentPageListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ThreeFragment extends WISERFragment implements OnTabShowCurrentPageListener {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_three);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {

	}

	@Override public void onShowCurrentPage(int position) {
		System.out.println("-------ThreeFragment-----Show");
	}

	@Override public void onHideCurrentPage(int position) {
		System.out.println("-------ThreeFragment-----Hide");
	}

	@Override public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		if (nextAnim <= 0) return super.onCreateAnimation(transit, enter, nextAnim);
		Animation anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);

		anim.setAnimationListener(new Animation.AnimationListener() {

			public void onAnimationStart(Animation animation) {
				// 动画开始
				System.out.println("------动画开始----");
				WISERHelper.display().showFragment((Fragment) WISERHelper.display().findFragment(TestFragment.class.getName()));
			}

			public void onAnimationRepeat(Animation animation) {
				// 动画循环
				System.out.println("------动画循环----");
			}

			public void onAnimationEnd(Animation animation) {
				// 动画结束
				System.out.println("------动画结束----");
				WISERHelper.display().hideFragment((Fragment) WISERHelper.display().findFragment(TestFragment.class.getName()));
			}
		});
		return anim;
	}
}
