package com.wiser.frame.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @author Wiser
 * @see CommonParamsInterceptor 公共参数
 */
@SuppressWarnings("ALL")
public class CommonParamsInterceptor implements Interceptor {

	public interface IParams {

		void addParamsMap(Map<String, String> paramsMap);
	}

	IParams				iParams;

	Map<String, String>	queryParamsMap	= new HashMap<>();

	Map<String, String>	paramsMap		= new HashMap<>();

	Map<String, String>	headerParamsMap	= new HashMap<>();

	List<String>		headerLinesList	= new ArrayList<>();

	private CommonParamsInterceptor() {

	}

	@Override public Response intercept(Chain chain) throws IOException {

		Request request = chain.request();
		Request.Builder requestBuilder = request.newBuilder();
		Map<String, String> bodyParamsMap = null;

		// process header params inject
		Headers.Builder headerBuilder = request.headers().newBuilder();
		if (headerParamsMap.size() > 0) {
			for (Object o : headerParamsMap.entrySet()) {
				Map.Entry entry = (Map.Entry) o;
				headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
			}
		}

		if (headerLinesList.size() > 0) {
			for (String line : headerLinesList) {
				headerBuilder.add(line);
			}
		}

		requestBuilder.headers(headerBuilder.build());

		// process header params end

		// process queryParams inject whatever it's GET or POST
		if (iParams != null) {
			iParams.addParamsMap(queryParamsMap);
		}

		if (queryParamsMap.size() > 0) {
			request = injectParamsIntoUrl(request.url().newBuilder(), requestBuilder, queryParamsMap);
		}
		// process post body inject
		if (paramsMap.size() > 0) {
			if (canInjectIntoBody(request)) {
				FormBody.Builder formBodyBuilder = new FormBody.Builder();
				for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
					formBodyBuilder.add(entry.getKey(), entry.getValue());
				}

				RequestBody formBody = formBodyBuilder.build();
				String postBodyString = bodyToString(request.body());
				postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
				requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
			}
		}
		request = requestBuilder.build();
		return chain.proceed(request);
	}

	private boolean canInjectIntoBody(Request request) {
		if (request == null) {
			return false;
		}
		if (!TextUtils.equals(request.method(), "POST")) {
			return false;
		}
		RequestBody body = request.body();
		if (body == null) {
			return false;
		}
		MediaType mediaType = body.contentType();
		return mediaType != null && TextUtils.equals(mediaType.subtype(), "x-www-form-urlencoded");
	}

	// func to inject params into url
	private Request injectParamsIntoUrl(HttpUrl.Builder httpUrlBuilder, Request.Builder requestBuilder, Map<String, String> paramsMap) {
		if (paramsMap.size() > 0) {
			for (Object o : paramsMap.entrySet()) {
				Map.Entry entry = (Map.Entry) o;
				httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
			}
			requestBuilder.url(httpUrlBuilder.build());
			return requestBuilder.build();
		}

		return null;
	}

	private static String bodyToString(final RequestBody request) {
		try {
			final Buffer buffer = new Buffer();
			if (request != null) request.writeTo(buffer);
			else return "";
			return buffer.readUtf8();
		} catch (final IOException e) {
			return "did not work";
		}
	}

	public static class Builder {

		CommonParamsInterceptor interceptor;

		public Builder() {
			interceptor = new CommonParamsInterceptor();
		}

		public Builder addParam(String key, String value) {
			interceptor.paramsMap.put(key, value);
			return this;
		}

		public Builder addParamsMap(Map<String, String> paramsMap) {
			interceptor.paramsMap.putAll(paramsMap);
			return this;
		}

		public Builder addHeaderParam(String key, String value) {
			interceptor.headerParamsMap.put(key, value);
			return this;
		}

		public Builder setOnHeaderParams(IParams iParams) {
			interceptor.iParams = iParams;
			return this;
		}

		public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
			interceptor.headerParamsMap.putAll(headerParamsMap);
			return this;
		}

		public Builder addHeaderLine(String headerLine) {
			int index = headerLine.indexOf(":");
			if (index == -1) {
				throw new IllegalArgumentException("Unexpected header: " + headerLine);
			}
			interceptor.headerLinesList.add(headerLine);
			return this;
		}

		public Builder addHeaderLinesList(List<String> headerLinesList) {
			for (String headerLine : headerLinesList) {
				int index = headerLine.indexOf(":");
				if (index == -1) {
					throw new IllegalArgumentException("Unexpected header: " + headerLine);
				}
				interceptor.headerLinesList.add(headerLine);
			}
			return this;
		}

		public Builder addQueryParam(String key, String value) {
			interceptor.queryParamsMap.put(key, value);
			return this;
		}

		public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
			interceptor.queryParamsMap.putAll(queryParamsMap);
			return this;
		}

		public CommonParamsInterceptor build() {
			return interceptor;
		}

	}
}