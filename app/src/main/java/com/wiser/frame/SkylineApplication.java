package com.wiser.frame;

import android.app.Application;

import com.wiser.library.helper.WISERHelper;

public class SkylineApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
		WISERHelper.newBind().setWiserBind(new AtBind()).Inject(this, BuildConfig.DEBUG);
	}
}
