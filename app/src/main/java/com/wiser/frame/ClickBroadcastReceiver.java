package com.wiser.frame;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ClickBroadcastReceiver extends BroadcastReceiver {

	@Override public void onReceive(Context context, Intent intent) {
		// 跳转
		Intent newIntent = new Intent(context, WebViewActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(newIntent);
	}
}
