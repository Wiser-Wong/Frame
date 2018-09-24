package com.wiser.frame;

import com.liulishuo.filedownloader.FileDownloader;
import com.wiser.library.helper.WISERHelper;

import android.app.Application;
import android.os.Environment;

import java.io.File;

public class WiserApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
		WISERHelper.newBind().setWiserBind(new MBind()).Inject(this, BuildConfig.DEBUG);

		WISERHelper.fileCacheManage().initConfigureStorageDirectoryFolder("frame");

		WISERHelper.downUploadManage().initFileDownloader(this);
	}
}
