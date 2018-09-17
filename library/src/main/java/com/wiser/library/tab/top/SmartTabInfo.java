package com.wiser.library.tab.top;

import android.os.Bundle;

/**
 * @author Wiser
 * 
 *         tab标签信息
 */
public class SmartTabInfo {

	public String	mTitle;

	public Class	mClazz;

	public Bundle	mBundle;

	public SmartTabInfo(String title, Class clazz, Bundle bundle) {

		mTitle = title;
		mClazz = clazz;
		mBundle = bundle;
	}

}
