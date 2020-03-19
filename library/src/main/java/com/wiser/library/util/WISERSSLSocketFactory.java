package com.wiser.library.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @author Wiser Created on 2020-02-13
 * 
 *         https 验证证书
 */
public class WISERSSLSocketFactory extends SSLSocketFactory {

	private SSLContext		sslContext	= SSLContext.getInstance("TLS");

	private TrustManager	trustManager;

	public SSLContext getSSLContext() {
		return sslContext;
	}

	public X509TrustManager getTrustManager() {
		return (X509TrustManager) trustManager;
	}

	public WISERSSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException {
		trustManager = new X509TrustManager() {

			@Override public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

			}

			@Override public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

			}

			@Override public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}
		};

		sslContext.init(null, new TrustManager[] { trustManager }, null);
	}

	@Override public String[] getDefaultCipherSuites() {
		return new String[0];
	}

	@Override public String[] getSupportedCipherSuites() {
		return new String[0];
	}

	@Override public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}

	@Override public Socket createSocket(Socket socket, String host, int post, boolean autoClose) throws IOException {
		return sslContext.getSocketFactory().createSocket(socket, host, post, autoClose);
	}

	@Override public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
		return null;
	}

	@Override public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
		return null;
	}

	@Override public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
		return null;
	}

	@Override public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
		return null;
	}

}
