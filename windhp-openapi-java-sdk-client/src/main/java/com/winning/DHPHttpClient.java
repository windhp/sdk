package com.winning;


import com.winning.constant.Constants;
import com.winning.profile.IProfile;
import com.winning.request.GetBuilder;
import com.winning.request.HttpClient;
import com.winning.request.PostBuilder;
import com.winning.request.ssl.X509TrustManagerImpl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.ssl.*;
import java.security.SecureRandom;

/**
 * @author xch
 * @date 2022/1/7 16:11
 */
public class DHPHttpClient {

	public static final Log logger = LogFactory.getLog(DHPHttpClient.class);

	public static final String VERSION = "1.0.0";

	private static HttpClient httpClient = new HttpClient(getDefaultOkHttpClient());

	private static OkHttpClient getDefaultOkHttpClient() {
		OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

		final X509TrustManager trustManager = new X509TrustManagerImpl();
		SSLSocketFactory sslSocketFactory=null;
		try {
			SSLContext sslContext = SSLContext.getInstance(Constants.HTTP_SSL);
			sslContext.init(null, new TrustManager[] { trustManager },new SecureRandom());
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assert sslSocketFactory != null;
		return builder.sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier((hostname, session) -> Boolean.TRUE).build();
	}

	public static DHPHttpClientBuilder newBuilder(){
		return new DHPHttpClientBuilder(httpClient.getOkHttpClient());
	}

	public static DHPHttpClientBuilder newBuilder(OkHttpClient client){
		return new DHPHttpClientBuilder(client);
	}

	public static GetBuilder get() {
		return httpClient.get();
	}

	public static PostBuilder post() {
		return httpClient.post();
	}

	public static GetBuilder get(IProfile profile) {
		return httpClient.get(profile);
	}

	public static PostBuilder post(IProfile profile) {
		return httpClient.post(profile);
	}


	public static HttpClient getHttpClient() {
		return httpClient;
	}

	public static void setHttpClient(HttpClient httpClient) {
		DHPHttpClient.httpClient = httpClient;
	}

	public static void cancelAll() {
		cancelAll(httpClient.getOkHttpClient());
	}

	public static void cancelAll(final OkHttpClient okHttpClient) {
		if (okHttpClient != null) {
			for (Call call : okHttpClient.dispatcher().queuedCalls()) {
				call.cancel();
		    }
		    for (Call call : okHttpClient.dispatcher().runningCalls()) {
		        call.cancel();
		    }
		}
	}

	/**
	 * 取消
	 * @param tag: tag
	 */
	public static void cancel(final Object tag) {
		cancel(httpClient.getOkHttpClient(), tag);
	}

	/**
	 * 取消
	 * @param okHttpClient:
	 * @param tag: tag
	 */
	public static void cancel(final OkHttpClient okHttpClient,final Object tag) {
		if (okHttpClient != null && tag!=null) {
			for (Call call : okHttpClient.dispatcher().queuedCalls()) {
		        if (tag.equals(call.request().tag())) {
		            call.cancel();
		        }
		    }
		    for (Call call : okHttpClient.dispatcher().runningCalls()) {
		        if (tag.equals(call.request().tag())) {
		            call.cancel();
		        }
		    }
		}
	}
}
