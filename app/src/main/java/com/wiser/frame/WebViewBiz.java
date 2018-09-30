package com.wiser.frame;

import com.wiser.library.annotation.impl.Impl;
import com.wiser.library.annotation.thread.Background;
import com.wiser.library.annotation.thread.BackgroundType;
import com.wiser.library.base.IWISERBiz;
import com.wiser.library.base.WISERBiz;

import retrofit2.Call;

public class WebViewBiz extends WISERBiz<WebViewActivity> implements IWebBiz {

	@Override public void netMethod() {
		Call<String> call = http(IHttp.class).getData();
		String s = httpBody(call);
		System.out.println("------>>" + s);
		ui().show(s);
	}

	@Override public void singleNetMethod() {
		Call<String> call = http(IHttp.class).getData();
		String s = httpBody(call);
		System.out.println("------>>" + s);
		ui().show(s);
	}

	public void b(){

	}

	@Override public void aVoid() {
		ui().show("假的");
	}
}

@Impl(WebViewBiz.class)
interface IWebBiz extends IWISERBiz {

	@Background(BackgroundType.HTTP) void netMethod();

	@Background(BackgroundType.SINGLE) void singleNetMethod();

	void aVoid();

}
