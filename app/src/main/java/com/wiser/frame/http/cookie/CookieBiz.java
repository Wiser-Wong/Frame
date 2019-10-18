package com.wiser.frame.http.cookie;

import java.util.Collection;
import java.util.List;

import okhttp3.Cookie;

/**
 * @author Wiser
 * @version 1.0
 * @see CookieBiz
 */
public interface CookieBiz {

	List<Cookie> loadAll();

	void saveAll(Collection<Cookie> cookies);

	void removeAll(Collection<Cookie> cookies);

	void clear();
}
