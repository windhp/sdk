package com.winning.request;

import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.profile.ApiClient;
import com.winning.profile.IProfile;
import com.winning.sign.Signer;
import okhttp3.*;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.winning.request.callback.Callback;
import com.winning.request.PostRequest.FileInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/7 15:30
 */
public abstract class OkHttpRequest {

    public static Log logger = LogFactory.getLog(OkHttpRequest.class);

    protected String id;
    protected String url;
    protected Map<String, String> params;
    protected Map<String, String> encodedParams;
    protected Map<String, String> headers;
    protected String body;
    protected List<FileInfo> fileInfos;
    protected MultipartBody multipartBody;
    protected Request.Builder builder = new Request.Builder();

    protected IProfile profile;

    protected OkHttpRequest(String url,
                            Object tag,
                            Map<String, String> params,
                            Map<String, String> headers,
                            List<FileInfo> fileInfos,
                            String body,
                            MultipartBody multipartBody,
                            IProfile profile,
                            String id) {
        this(url, tag, params, null, headers, fileInfos, body, multipartBody, profile, id);
    }

    protected OkHttpRequest(String url,
                            Object tag,
                            Map<String, String> params,
                            Map<String, String> encodedParams,
                            Map<String, String> headers,
                            List<FileInfo> fileInfos,
                            String body,
                            MultipartBody multipartBody,
                            IProfile profile,
                            String id) {
        this.url = url;
        this.params = params;
        this.encodedParams = encodedParams;
        this.headers = headers;
        this.fileInfos = fileInfos;
        this.body = body;
        this.multipartBody = multipartBody;
        this.profile = profile;
        this.id = id;
        if (url == null) {
            throw new IllegalArgumentException("url can not be null.");
        }
        builder.url(url).tag(tag);
        appendHeaders(profile);
    }

    /**
     * 创建 okhttp3.RequestBody
     * @return: okhttp3.RequestBody
     */
    protected abstract RequestBody buildRequestBody();

    /**
     * 创建 okhttp3.RequestBody
     * @param requestBody:
     * @return: okhttp3.Request
     */
    protected abstract Request buildRequest(RequestBody requestBody);

    public RequestCall build(OkHttpClient okHttpClient) {
        return new RequestCall(this, okHttpClient);
    }

    public Request createRequest(Callback callback) {
        RequestBody requestBody = buildRequestBody();
        Request request = buildRequest(requestBody);
        return request;
    }

    protected void appendHeaders(IProfile profile) {
        if (profile != null) {
            ApiClient apiClient = profile.getApiClient();
            if (id == null || id.length() == 0) {
                id = UUID.randomUUID().toString().replace("-", "");
            }
            headers.put(SystemHeader.X_CA_NONCE, id);
            headers.put(SystemHeader.X_CA_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
            headers.put(SystemHeader.X_CA_KEY, apiClient.getAppKey());
            headers.put(SystemHeader.X_SERVICE_CODE, apiClient.getServiceCode());
            String signString = getSignString();
            logger.debug("The original string of the signature is " + signString);
            Signer signer = Signer.getSigner();
            String signature = signer.signString(signString, apiClient.getAppSecret());
            logger.debug("The signature result is " + signature);
            headers.put(SystemHeader.X_CA_SIGNATURE, signature);
            logger.info(headers);
        }
        appendHeaders();
    }

    protected String getSignString() {
        String method = headers.get(SystemHeader.HTTP_METHOD);
        String contentType = headers.get(SystemHeader.CONTENT_TYPE);
        String xCaKey = headers.get(SystemHeader.X_CA_KEY);
        String xCaNonce = headers.get(SystemHeader.X_CA_NONCE);
        String xCaTimestamp = headers.get(SystemHeader.X_CA_TIMESTAMP);
        String xContentMd5 = headers.get(SystemHeader.X_CONENT_MD5);
        String xServiceCode = headers.get(SystemHeader.X_SERVICE_CODE);
        StringBuilder stringSign = new StringBuilder(method);
        stringSign.append(Constants.LF)
                .append(contentType)
                .append(Constants.LF)
                .append(SystemHeader.X_CA_KEY.toLowerCase()).append(Constants.COLON).append(xCaKey).append(Constants.AND_MARK)
                .append(SystemHeader.X_CA_NONCE.toLowerCase()).append(Constants.COLON).append(xCaNonce).append(Constants.AND_MARK)
                .append(SystemHeader.X_CA_TIMESTAMP.toLowerCase()).append(Constants.COLON).append(xCaTimestamp).append(Constants.AND_MARK)
                .append(SystemHeader.X_CONENT_MD5.toLowerCase()).append(Constants.COLON).append(xContentMd5).append(Constants.AND_MARK)
                .append(SystemHeader.X_SERVICE_CODE.toLowerCase()).append(Constants.COLON).append(xServiceCode);
        return stringSign.toString();
    }



    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) {
            return;
        }
        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public String getId() {
        return id;
    }

    /** Returns a new request body that transmits the content of {@code file}. */
    public static RequestBody createRequestBody(final MediaType contentType, final InputStream is) {
        if (is == null) {
            throw new NullPointerException("is == null");
        }
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                try {
                    return is.available();
                } catch (IOException e) {
                    return 0;
                }
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(is);
                    sink.writeAll(source);
                } finally {
                    Util.closeQuietly(source);
                }
            }
        };
    }
}
