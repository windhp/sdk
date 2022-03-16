package com.winning;


import com.winning.request.HttpClient;
import com.winning.request.ssl.X509TrustManagerImpl;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xch
 * @date  2022/1/7 16:11
 */
public class DHPHttpClientBuilder {

	private static Log logger = LogFactory.getLog(DHPHttpClientBuilder.class);

	/**
	 * okhttp3的builder
	 */
	private OkHttpClient.Builder builder;


	public DHPHttpClientBuilder(){
		this.builder = new OkHttpClient.Builder();
	}


	public DHPHttpClientBuilder(OkHttpClient okHttpClient){
		this.builder = okHttpClient.newBuilder();
	}


	public DHPHttpClientBuilder connectTimeout(long timeout, TimeUnit unit) {
		builder.connectTimeout(timeout, unit);
		return this;
	}

	public DHPHttpClientBuilder readTimeout(long timeout, TimeUnit unit) {
		builder.readTimeout(timeout, unit);
		return this;
	}

	/**
	 * @param timeout 超时时间
	 * @param unit 超时单位
	 * @return DHPHttpClientBuilder
	 */
	public DHPHttpClientBuilder writeTimeout(long timeout, TimeUnit unit) {
		builder.writeTimeout(timeout, unit);
		return this;
	}

	public DHPHttpClientBuilder pingInterval(long interval, TimeUnit unit) {
		builder.pingInterval(interval, unit);
		return this;
	}

	public DHPHttpClientBuilder proxy(Proxy proxy) {
		builder.proxy(proxy);
		return this;
	}

	public DHPHttpClientBuilder proxySelector(ProxySelector proxySelector) {
		builder.proxySelector(proxySelector);
		return this;
	}

	public DHPHttpClientBuilder cookieJar(CookieJar cookieJar) {
		builder.cookieJar(cookieJar);
		return this;
	}

	public DHPHttpClientBuilder cache(Cache cache) {
		builder.cache(cache);
		return this;
	}

	/**
	 * 设置DNS
	 * @param dns DNS
	 * @return DHPHttpClientBuilder
	 */
	public DHPHttpClientBuilder dns(Dns dns) {
		builder.dns(dns);
		return this;
	}

	public DHPHttpClientBuilder socketFactory(SocketFactory socketFactory) {
		builder.socketFactory(socketFactory);
		return this;
	}

	@SuppressWarnings("deprecation")
	public DHPHttpClientBuilder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
		builder.sslSocketFactory(sslSocketFactory);
		return this;
	}

	/**
	 * SSL
	 * @param sslSocketFactory  sslSocketFactory
	 * @param trustManager  trustManager
	 * @return DHPHttpClientBuilder
	 */
	public DHPHttpClientBuilder sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
		builder.sslSocketFactory(sslSocketFactory, trustManager);
		return this;
	}

	public DHPHttpClientBuilder hostnameVerifier(HostnameVerifier hostnameVerifier) {
		builder.hostnameVerifier(hostnameVerifier);
		return this;
	}

	public DHPHttpClientBuilder certificatePinner(CertificatePinner certificatePinner) {
		builder.certificatePinner(certificatePinner);
		return this;
	}

	public DHPHttpClientBuilder authenticator(Authenticator authenticator) {
		builder.authenticator(authenticator);
		return this;
	}

	public DHPHttpClientBuilder proxyAuthenticator(Authenticator proxyAuthenticator) {
		builder.proxyAuthenticator(proxyAuthenticator);
		return this;
	}

	public DHPHttpClientBuilder connectionPool(ConnectionPool connectionPool) {
		builder.connectionPool(connectionPool);
		return this;
	}

	public DHPHttpClientBuilder followSslRedirects(boolean followProtocolRedirects) {
		builder.followSslRedirects(followProtocolRedirects);
		return this;
	}

	public DHPHttpClientBuilder followRedirects(boolean followRedirects) {
		builder.followRedirects(followRedirects);
		return this;
	}

	public DHPHttpClientBuilder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
		builder.retryOnConnectionFailure(retryOnConnectionFailure);
		return this;
	}

	public DHPHttpClientBuilder dispatcher(Dispatcher dispatcher) {
		builder.dispatcher(dispatcher);
		return this;
	}

	public DHPHttpClientBuilder protocols(List<Protocol> protocols) {
		builder.protocols(protocols);
		return this;
	}

	public DHPHttpClientBuilder connectionSpecs(List<ConnectionSpec> connectionSpecs) {
		builder.connectionSpecs(connectionSpecs);
		return this;
	}

	public DHPHttpClientBuilder addInterceptor(Interceptor interceptor) {
		builder.addInterceptor(interceptor);
		return this;
	}

	public DHPHttpClientBuilder addNetworkInterceptor(Interceptor interceptor) {
		builder.addNetworkInterceptor(interceptor);
		return this;
	}

	public OkHttpClient.Builder getBuilder() {
		return builder;
	}
	
	public DHPHttpClientBuilder sslContext(SSLContext sslContext) {
		SSLSocketFactory sslSocketFactory = null;
		final X509TrustManager trustManager=new X509TrustManagerImpl();
		try {
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		assert sslSocketFactory != null;
		builder.sslSocketFactory(sslSocketFactory, trustManager).
		hostnameVerifier((hostname, session) -> Boolean.TRUE);
		return this;
	}

	public HttpClient build() {
		return new HttpClient(builder.build());
	}
}
