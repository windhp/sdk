package com.winning.request;

import com.winning.profile.IProfile;
import okhttp3.OkHttpClient;

/**
 * @author xch
 * @date 2022/1/7 16:11
 */
public class HttpClient {

	private OkHttpClient okHttpClient;

	public HttpClient(OkHttpClient okHttpClient){
		this.okHttpClient = okHttpClient;
	}

	public GetBuilder get(){
		return new GetBuilder(okHttpClient);
	}

	public PostBuilder post() {
		return new PostBuilder(okHttpClient);
	}

	public PostBuilder post(IProfile profile) {
		return new PostBuilder(okHttpClient, profile);
	}

	public OkHttpClient getOkHttpClient() {
		return okHttpClient;
	}

	public void setOkHttpClient(OkHttpClient okHttpClient) {
		this.okHttpClient = okHttpClient;
	}

	public GetBuilder get(IProfile profile) {
		return new GetBuilder(okHttpClient, profile);
	}
}
