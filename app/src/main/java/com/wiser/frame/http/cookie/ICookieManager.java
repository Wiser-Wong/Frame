package com.wiser.frame.http.cookie;

import okhttp3.CookieJar;

/**
 * @author Wiser
 * @version 1.0
 * @see ICookieManager
 */
public interface ICookieManager extends CookieJar {

	void clearSession();

	void clear();
}
