package com.winning.request;

import com.winning.constant.SystemHeader;
import com.winning.exceptions.ClientException;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/7 16:11
 */
public class Response {
	private okhttp3.Response okhttpResponse;
	
	/**
	 * 
	 * @param response
	 */
	public Response(okhttp3.Response response){
		this.okhttpResponse =response;
	}
	
	/**
	 * 
	 * @return
	 */
	public Request request() {
		return okhttpResponse.request();
	}

	/**
	 * 
	 * @return
	 */
	public Protocol protocol() {
		return okhttpResponse.protocol();
	}

	/**
	 * 
	 * @return
	 */
	public int code() {
		return okhttpResponse.code();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSuccessful() {
		return okhttpResponse.isSuccessful();
	}

	/**
	 * 
	 * @return
	 */
	public String message() {
		return okhttpResponse.message();
	}

	/**
	 * Returns the TLS handshake of the connection that carried this response,
	 * or null if the response was received without TLS.
	 */
	public Handshake handshake() {
		return okhttpResponse.handshake();
	}

	public List<String> headers(String name) {
		return okhttpResponse.headers(name);
	}

	public String header(String name) {
		return okhttpResponse.header(name, null);
	}

	public String header(String name, String defaultValue) {
		return okhttpResponse.header(name, defaultValue);
	}

	public Headers headers() {
		return okhttpResponse.headers();
	}

	public String getTraceId() {
		return okhttpResponse.header(SystemHeader.X_TRACE_ID, null);
	}

	public String getCaErrorMessage() {
		return okhttpResponse.header(SystemHeader.X_CA_ERROR_MESSAGE, null);
	}

	/**
	 * Peeks up to {@code byteCount} bytes from the response body and returns
	 * them as a new response body. If fewer than {@code byteCount} bytes are in
	 * the response body, the full response body is returned. If more than
	 * {@code byteCount} bytes are in the response body, the returned value will
	 * be truncated to {@code byteCount} bytes.
	 *
	 * <p>
	 * It is an error to call this method after the body has been consumed.
	 *
	 * <p>
	 * <strong>Warning:</strong> this method loads the requested bytes into
	 * memory. Most applications should set a modest limit on {@code byteCount},
	 * such as 1 MiB.
	 */
	public ResponseBody peekBody(long byteCount) throws ClientException {
		try {
			return okhttpResponse.peekBody(byteCount);
		} catch (IOException e) {
			throw new ClientException(e);
		}
	}

	/**
	 * Never {@code null}, must be closed after consumption, can be consumed
	 * only once.
	 */
	public ResponseBody body() {
		return okhttpResponse.body();
	}

	
	/**
	 * Returns the response as a string decoded with the charset of the
	 * Content-Type header. If that header is either absent or lacks a charset,
	 * this will attempt to decode the response body as UTF-8.
	 */
	public final String string() throws ClientException {
		try {
			return body().string();
		} catch (IOException e) {
			throw new ClientException(e);
		}
	}
	
	/**
	 * Returns the response as a string decoded with the charset of the
	 * Content-Type header. If that header is either absent or lacks a charset,
	 * this will attempt to decode the response body as UTF-8.
	 */
	public final String string(String charset) throws ClientException {
		try {
			return new String(body().bytes(),charset);
		} catch (IOException e) {
			throw new ClientException(e);
		}
	}
	
	public final byte[] bytes() throws ClientException {
		try {
			return body().bytes();
		} catch (IOException e) {
			throw new ClientException(e);
		}
	}
	
	public final InputStream byteStream() {
	    return body().source().inputStream();
	}
	
	public okhttp3.Response getOkhttpResponse(){
		return okhttpResponse;
	}
	
}
