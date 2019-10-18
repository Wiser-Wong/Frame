package com.wiser.frame.http.cookie;

import java.util.Collection;

import okhttp3.Cookie;

/**
 * @author Wiser
 * @version 1.0
 * @see CookieCache
 */
public interface CookieCache extends Iterable<Cookie> {

	void addAll(Collection<Cookie> cookies);

	void clear();
}
