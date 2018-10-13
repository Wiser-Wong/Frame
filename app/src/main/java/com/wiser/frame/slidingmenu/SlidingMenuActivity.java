package com.wiser.frame.slidingmenu;

import com.wiser.frame.R;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERSlidingMenuActivity;
import com.wiser.library.view.slidingmenu.SlidingMenu;

import android.content.Intent;
import android.graphics.Color;

public class SlidingMenuActivity extends WISERSlidingMenuActivity {

	@Override protected WISERBuilder buildSlidingMenu(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_sliding_menu);
		builder.tintColor(Color.RED);
		builder.tintIs(true);
		builder.tintFitsSystem(true);
		return builder;
	}

	@Override protected void initSlidingMenuData(Intent intent) {
	}

	@Override protected void slidingMenuSetting(SlidingMenu slidingMenu) {
		SlidingMenu sm = slidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setBehindOffsetRes(R.dimen.sliding_menu_offset);
		// 如果单一设置左侧或者右侧 只需设置该属性就可以显示阴影shadow 如果双侧菜单需要另外设置右侧阴影shadow
		// setSecondaryShadowDrawable(R.drawable.shadow)
		sm.setShadowDrawable(R.drawable.shadow);
		// 双侧菜单时候起作用
		sm.setSecondaryShadowDrawable(R.drawable.shadow);
		sm.setFadeDegree(0.5f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// setSlidingActionBarEnabled(true);
		// sm.setBehindScrollScale(0.0f);
		// sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
		// @Override
		// public void transformCanvas(Canvas canvas, float percentOpen) {
		//// float scale = (float) (percentOpen*0.25 + 0.75);
		//// canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);
		// canvas.translate(canvas.getHeight()*(1-new Interpolator() {
		// @Override
		// public float getInterpolation(float t) {
		// t -= 1.0f;
		// return t * t * t + 1.0f;
		// }
		// }.getInterpolation(percentOpen)), 0);
		// }
		// });
	}

	@Override protected int slidingMenuMode() {
		return SlidingMenu.LEFT_RIGHT;
	}

	@Override protected void setLeftMenuFragment(boolean isModeLeft) {
		setMenuFragment(R.id.left_frame, new LeftMenuFragment());
	}

	@Override protected void setContentMenuFragment() {
		setMenuFragment(R.id.content_frame, new ContentFragment());
	}

	@Override protected void setRightMenuFragment(boolean isModeRight) {
		setMenuFragment(R.id.right_frame, new RightMenuFragment());

	}
}
