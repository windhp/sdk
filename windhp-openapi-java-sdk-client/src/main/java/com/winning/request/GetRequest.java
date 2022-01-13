package com.winning.request;

import com.winning.profile.IProfile;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Map;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/7 15:59
 */
public class GetRequest extends OkHttpRequest {
    protected GetRequest(String url,
                         Object tag,
                         Map<String, String> params,
                         Map<String, String> headers,
                         IProfile profile,
                         String requestId) {
        super(url, tag, params, headers, null, null, null, profile, requestId);
    }

    protected GetRequest(String url,
                         Object tag,
                         Map<String, String> params,
                         Map<String, String> encodedParams,
                         Map<String, String> headers,
                         IProfile profile,
                         String requestId) {
        super(url, tag, params, encodedParams, headers, null, null, null, profile,  requestId);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.get().build();
    }
}
