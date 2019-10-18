package com.wiser.frame.http.cookie.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.wiser.frame.http.cookie.CookieBiz;
import com.wiser.frame.http.cookie.CookieCache;
import com.wiser.frame.http.cookie.ICookieManager;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * @author Wiser
 * @version
 * @see CookieManager
 */
public class CookieManager implements ICookieManager {

	private final CookieCache cache;

	private final CookieBiz cookieBiz;

	public CookieManager(Context context) {
		this.cache = new SetCookieCache();
		this.cookieBiz = new SharedPrefsCookieBiz(context);

		this.cache.addAll(cookieBiz.loadAll());
	}

	@Override synchronized public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		cache.addAll(cookies);
		cookieBiz.saveAll(filterPersistentCookies(cookies));
	}

	private static List<Cookie> filterPersistentCookies(List<Cookie> cookies) {
		List<Cookie> persistentCookies = new ArrayList<>();

		for (Cookie cookie : cookies) {
			if (cookie.persistent()) {
				persistentCookies.add(cookie);
			}
		}
		return persistentCookies;
	}

	@Override synchronized public List<Cookie> loadForRequest(HttpUrl url) {
		List<Cookie> cookiesToRemove = new ArrayList<>();
		List<Cookie> validCookies = new ArrayList<>();

		for (Iterator<Cookie> it = cache.iterator(); it.hasNext();) {
			Cookie currentCookie = it.next();

			if (isCookieExpired(currentCookie)) {
				cookiesToRemove.add(currentCookie);
				it.remove();

			} else if (currentCookie.matches(url)) {
				validCookies.add(currentCookie);
			}
		}

		cookieBiz.removeAll(cookiesToRemove);

		return validCookies;
	}

	private static boolean isCookieExpired(Cookie cookie) {
		return cookie.expiresAt() < System.currentTimeMillis();
	}

	@Override synchronized public void clearSession() {
		cache.clear();
		cache.addAll(cookieBiz.loadAll());
	}

	@Override synchronized public void clear() {
		cache.clear();
		cookieBiz.clear();
	}
}
