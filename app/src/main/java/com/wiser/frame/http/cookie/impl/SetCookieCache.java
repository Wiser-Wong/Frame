package com.wiser.frame.http.cookie.impl;

import com.wiser.frame.http.cookie.CookieCache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Cookie;

/**
 * @author Wiser
 * @version
 * @see SetCookieCache
 */
public class SetCookieCache implements CookieCache {

	private Set<IdentifiableCookie> cookies;

	public SetCookieCache() {
		cookies = new HashSet<>();
	}

	@Override public void addAll(Collection<Cookie> newCookies) {
		for (IdentifiableCookie cookie : IdentifiableCookie.decorateAll(newCookies)) {
			this.cookies.remove(cookie);
			this.cookies.add(cookie);
		}
	}

	@Override public void clear() {
		cookies.clear();
	}

	@Override public Iterator<Cookie> iterator() {
		return new SetCookieCacheIterator();
	}

	private class SetCookieCacheIterator implements Iterator<Cookie> {

		private Iterator<IdentifiableCookie> iterator;

		public SetCookieCacheIterator() {
			iterator = cookies.iterator();
		}

		@Override public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override public Cookie next() {
			return iterator.next().getCookie();
		}

		@Override public void remove() {
			iterator.remove();
		}
	}

	static class IdentifiableCookie {

		private Cookie cookie;

		static List<IdentifiableCookie> decorateAll(Collection<Cookie> cookies) {
			List<IdentifiableCookie> identifiableCookies = new ArrayList<>(cookies.size());
			for (Cookie cookie : cookies) {
				identifiableCookies.add(new IdentifiableCookie(cookie));
			}
			return identifiableCookies;
		}

		IdentifiableCookie(Cookie cookie) {
			this.cookie = cookie;
		}

		Cookie getCookie() {
			return cookie;
		}

		@Override public boolean equals(Object other) {
			if (!(other instanceof IdentifiableCookie)) return false;
			IdentifiableCookie that = (IdentifiableCookie) other;
			return that.cookie.name().equals(this.cookie.name()) && that.cookie.domain().equals(this.cookie.domain()) && that.cookie.path().equals(this.cookie.path())
					&& that.cookie.secure() == this.cookie.secure() && that.cookie.hostOnly() == this.cookie.hostOnly();
		}

		@Override public int hashCode() {
			int hash = 17;
			hash = 31 * hash + cookie.name().hashCode();
			hash = 31 * hash + cookie.domain().hashCode();
			hash = 31 * hash + cookie.path().hashCode();
			hash = 31 * hash + (cookie.secure() ? 0 : 1);
			hash = 31 * hash + (cookie.hostOnly() ? 0 : 1);
			return hash;
		}
	}
}