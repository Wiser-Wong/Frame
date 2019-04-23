package com.wiser.frame;

import com.wiser.library.annotation.impl.Impl;
import com.wiser.library.annotation.thread.Background;
import com.wiser.library.annotation.thread.BackgroundType;
import com.wiser.library.base.IWISERBiz;
import com.wiser.library.base.WISERBiz;
import com.wiser.library.network.WISERRxJavaDisposableObserver;

import io.reactivex.Observable;
import io.reactivex.functions.Function4;
import retrofit2.Call;

public class WebViewBiz extends WISERBiz<WebViewActivity> implements IWebBiz {

	@Override public void callMethod() {
		Call<String> call = http(IHttp.class).getData();
		String s = httpBody(call);
		System.out.println("------>>" + s);
		ui().show(s);
	}

	@Override public void observableMethod() {
		/**
		 * 单一请求 准确写法需要subscribe(httpDisposableObserver(new
		 * WISERRxJavaDisposableObserver)这么写防止内存泄漏
		 */
		 httpObservableIO(http(IHttp.class).getObservableData()).subscribe(httpDisposableObserver(new
		 WISERRxJavaDisposableObserver<String>() {

		 @Override protected void onSuccess(String s) {
		 System.out.println("------>>" + s);
		 ui().show(s);
		 }
		 }));
		/**
		 * flatMap处理多循环嵌套 与 concatMap一样的效果
		 * flatMap与concatMap区别是flatMap处理多条数据时候是无序的，而concatMap处理多条数据是有序的
		 */
		// httpObservableIO(http(IHttp.class).getObservableData().flatMap(new
		// Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>1:==>>" + s);
		// return http(IHttp.class).getObservableData();
		// }
		// }).flatMap(new Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>2:==>>" + s);
		// return http(IHttp.class).getObservableData1(s);
		// }
		// }).flatMap(new Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>3:==>>" + s);
		// return http(IHttp.class).getObservableData();
		// }
		// })).subscribe(httpDisposableObserver(new
		// WISERRxJavaDisposableObserver<String>() {
		//
		// @Override protected void onSuccess(String s) {
		// System.out.println("------>>" + s);
		// ui().show(s);
		// }
		// }));
//		/** zip事件合并 */
//		httpObservableIO(Observable.zip(http(IHttp.class).getObservableData(), http(IHttp.class).getObservableData1(""), http(IHttp.class).getObservableData(), http(IHttp.class).getObservableData(),
//				new Function4<String, String, String, String, String>() {
//
//					@Override public String apply(String s, String s2, String s3, String s4) {
//						return s + "-->>" + s2 + "-->>" + s3 + "-->>" + s4;
//					}
//				})).subscribe(httpDisposableObserver(new WISERRxJavaDisposableObserver<String>() {
//
//					@Override protected void onSuccess(String s) {
//						System.out.println("------>>" + s);
//						ui().show(s);
//						hideLoading();
//					}
//				}));

		/** flatMap和map */
		// httpObservableIO(http(IHttp.class).getObservableData().flatMap(new
		// Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>first:==>>" + s);
		// return http(IHttp.class).getObservableData();
		// }
		// }).flatMap(new Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>second:==>>" + s);
		// return http(IHttp.class).getObservableData1(s);
		// }
		// }).map(new Function<String, String>() {
		//
		// @Override public String apply(String s) {
		// System.out.println("------->>third:==>>" + s);
		// return s;
		// }
		// })).subscribe(new WISERRxJavaObserver<String>() {
		//
		// @Override protected void onSuccess(String s) {
		// System.out.println("------->>end:==>>" + s);
		// ui().show(s);
		// }
		// });

		/**
		 * concatMap处理多循环嵌套 与 flatMap一样的效果
		 * flatMap与concatMap区别是flatMap处理多条数据时候是无序的，而concatMap处理多条数据是有序的
		 */
		// httpObservableIO(http(IHttp.class).getObservableData().concatMap(new
		// Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>1:==>>" + s);
		// return http(IHttp.class).getObservableData1(s);
		// }
		// }).concatMap(new Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>2:==>>" + s);
		// return http(IHttp.class).getObservableData();
		// }
		// }).concatMap(new Function<String, ObservableSource<String>>() {
		//
		// @Override public ObservableSource<String> apply(String s) {
		// System.out.println("------->>3:==>>" + s);
		// return http(IHttp.class).getObservableData();
		// }
		// })).subscribe(httpDisposableObserver(new
		// WISERRxJavaDisposableObserver<String>() {
		//
		// @Override protected void onSuccess(String s) {
		// System.out.println("------>>" + s);
		// ui().show(s);
		// }
		// }));

	}

	@Override public void singleNetMethod() {
		Call<String> call = http(IHttp.class).getData();
		String s = httpBody(call);
		System.out.println("------>>" + s);
		ui().show(s);
	}

	@Override public void aVoid() {
		ui().show("假的");
	}

}

@Impl(WebViewBiz.class)
interface IWebBiz extends IWISERBiz {

	@Background(BackgroundType.HTTP) void callMethod();

	void observableMethod();

	@Background(BackgroundType.SINGLE) void singleNetMethod();

	void aVoid();

}
