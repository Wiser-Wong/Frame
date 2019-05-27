package com.wiser.library.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import android.annotation.SuppressLint;

import com.wiser.library.helper.WISERHelper;

/**
 * @author Wiser
 * 
 *         自签证书验证
 */
public class WISERSSLHttpsAuth {

	// 获取HostnameVerifier
	public static HostnameVerifier getHostnameVerifier() {
		return new HostnameVerifier() {

			@SuppressLint("BadHostnameVerifier") @Override public boolean verify(String s, SSLSession sslSession) {
				return true;
			}
		};
	}

	/**
	 * 此方法 忽略SSL 证书验证
	 * 
	 * @return
	 */
	public static synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {

				@SuppressLint("TrustAllX509TrustManager") public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

				@SuppressLint("TrustAllX509TrustManager") public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, null);
			return sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {

		final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];

		return new TrustManager[] { new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return originalTrustManager.getAcceptedIssuers();
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
				try {
					originalTrustManager.checkClientTrusted(certs, authType);
				} catch (CertificateException e) {
					e.printStackTrace();
				}
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
				try {
					originalTrustManager.checkServerTrusted(certs, authType);
				} catch (CertificateException e) {
					e.printStackTrace();
				}
			}
		} };

	}

	/**
	 * 获取自签证书验证
	 * 
	 * @param rawId
	 *            服务端自签证书文件 放到raw目录下
	 * @return
	 */
	public static SSLSocketFactory getSSLSocketFactoryForRaw(int rawId) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream caInput = WISERHelper.getInstance().getResources().openRawResource(rawId);
			Certificate ca = cf.generateCertificate(caInput);
			caInput.close();
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keyStore);
			TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, wrappedTrustManagers, null);
			return sslContext.getSocketFactory();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取自签证书验证
	 *
	 * @param assetFileName
	 *            服务端自签证书文件 放到Assests目录下
	 * @return
	 */
	public static SSLSocketFactory getSSLSocketFactoryForAssets(String assetFileName) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream caInput = WISERHelper.getInstance().getAssets().open(assetFileName);
			Certificate ca = cf.generateCertificate(caInput);
			caInput.close();
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keyStore);
			TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, wrappedTrustManagers, null);
			return sslContext.getSocketFactory();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
