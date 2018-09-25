package com.wiser.frame;

import com.wiser.library.helper.WISERHelper;

import android.app.Application;

public class WiserApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
		WISERHelper.newBind().setWiserBind(new MBind()).Inject(this, true);

		WISERHelper.fileCacheManage().initConfigureFile(WISERHelper.fileCacheManage().configureFileDir(this));

		WISERHelper.downUploadManage().initFileDownloader(this);

		WISERHelper.setCrashHandler(this, "log", false);
	}
}
