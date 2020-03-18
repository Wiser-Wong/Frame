package com.wiser.library.manager.http;

import javax.inject.Inject;
import javax.net.ssl.SSLSocketFactory;

import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERCheck;
import com.wiser.library.util.WISERSSLSocketFactory;
import com.wiser.library.util.WISERSSLHttpsAuth;

import android.support.annotation.RawRes;

import okhttp3.OkHttpClient;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERHttpManage {

	@Inject public WISERHttpManage() {}

	public <H> H http(Class<H> hClass) {
		return WISERHelper.retrofit().create(hClass);
	}

	/**
	 * 初始化自签名
	 * 
	 * @param fileName
	 *            assets 目录下 文件名字 xxx.crt
	 * @param builder
	 *            OkHttpBuilder
	 */
	public void initSSLSocketFactory(String fileName, OkHttpClient.Builder builder) {
		if (WISERCheck.isEmpty(fileName) || builder == null) return;
		// 自签证书
		SSLSocketFactory sslSocketFactory = WISERSSLHttpsAuth.getSSLSocketFactoryForAssets(fileName);
		if (sslSocketFactory != null) builder.sslSocketFactory(sslSocketFactory).hostnameVerifier(WISERSSLHttpsAuth.getHostnameVerifier());
	}

	/**
	 * 初始化自签名
	 *
	 * @param fileId
	 *            raw 目录下 文件名字 R.raw.签名
	 * @param builder
	 *            OkHttpBuilder
	 */
	public void initSSLSocketFactory(@RawRes int fileId, OkHttpClient.Builder builder) {
		if (builder == null) return;
		// 自签证书
		SSLSocketFactory sslSocketFactory = WISERSSLHttpsAuth.getSSLSocketFactoryForRaw(fileId);
		if (sslSocketFactory != null) builder.sslSocketFactory(sslSocketFactory).hostnameVerifier(WISERSSLHttpsAuth.getHostnameVerifier());
	}

	/**
	 * 初始化自签名
	 *
	 * @param builder
	 *            OkHttpBuilder
	 */
	public void initSSLSocketFactory(OkHttpClient.Builder builder) {
		if (builder == null) return;
		// 验证
		WISERSSLHttpsAuth.sslHttpsAuth(builder);
	}
}
