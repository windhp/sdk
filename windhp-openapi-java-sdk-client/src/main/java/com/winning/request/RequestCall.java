package com.winning.request;

import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.exceptions.ClientException;
import com.winning.exceptions.ServerException;
import com.winning.request.callback.Callback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @description: 请求类
 * @author: xch
 * @time: 2022/1/7 16:11
 */
public class RequestCall {

	private final OkHttpClient httpClient;
	private final OkHttpRequest dhpHttpRequest;
	private Request request;
	private Call call;

	public RequestCall(OkHttpRequest request, OkHttpClient httpClient) {
		this.dhpHttpRequest = request;
		this.httpClient = httpClient;
	}
	
	public void buildCall(Callback callback) {
		request = createRequest(callback);
		call = httpClient.newCall(request);
	}

	private Request createRequest(Callback callback) {
		return dhpHttpRequest.createRequest(callback);
	}
	
	public Response execute() throws ServerException, ClientException {
		buildCall(null);
		Response rsp;
		try {
			rsp = new Response(call.execute());
		} catch (Exception e) {
			throw new ClientException(e);
		}
		if (rsp.isSuccessful()) {
			return rsp;
		}
		String traceId = rsp.getTraceId();
		final String requestId = this.dhpHttpRequest.id;
		if (Constants.SERVER_ERROR_HTTPCODE <= rsp.code()) {
			throw new ServerException(String.valueOf(rsp.code()), rsp.string(), requestId, traceId);
		} else {
			throw new ClientException(String.valueOf(rsp.code()), rsp.string(), requestId, traceId);
		}
	}

	public void executeAsync(Callback callback) {
		buildCall(callback);
		execute(this, callback);
	}

	private void execute(final RequestCall requestCall, Callback callback) {
		final Callback finalCallback = callback;
		requestCall.getCall().enqueue(new okhttp3.Callback() {
			@Override
			public void onFailure(@NotNull Call call, @NotNull final IOException exp) {
				if(finalCallback != null){
					ClientException clientException = new ClientException("SDK.ServerUnreachable",
							"Server unreachable: connection " + call.request().url() + " failed", exp);
					finalCallback.onFailure(call, clientException, null);
				}
			}
			@Override
			public void onResponse(@NotNull final Call call, @NotNull final okhttp3.Response response) {
				finalCallback.onResponse(call, new Response(response), response.header(SystemHeader.X_TRACE_ID, null));
			}
		});
	}
	
	public Call getCall() {
		return call;
	}

	public Request getRequest() {
		return request;
	}

	public OkHttpRequest getDhpHttpRequest() {
		return dhpHttpRequest;
	}

	public void cancel() {
		if (call != null) {
			call.cancel();
		}
	}
}
