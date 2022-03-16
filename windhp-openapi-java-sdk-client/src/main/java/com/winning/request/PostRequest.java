package com.winning.request;

import com.winning.constant.SystemHeader;
import com.winning.profile.IProfile;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * @author xch
 * @date 2022/1/7 15:59
 */
public class PostRequest extends OkHttpRequest {
    public static Log logger = LogFactory.getLog(PostRequest.class);

    public PostRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers,
                       List<FileInfo> fileInfos, String postBody, MultipartBody multipartBody, IProfile profile, String id) {
        super(url, tag, params, headers, fileInfos, postBody, multipartBody, profile, id);
    }


    public PostRequest(String url, Object tag, Map<String, String> params, Map<String, String> encodeParams,
                       Map<String, String> headers, List<FileInfo> fileInfos, String postBody, MultipartBody multipartBody,
                       IProfile profile,
                       String id) {
        super(url, tag, params, encodeParams, headers, fileInfos, postBody, multipartBody, profile, id);
    }

    public static class FileInfo {
        public String partName;
        public String fileName;
        public byte[] fileContent;
        public File file;
        public InputStream fileInputStream;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (multipartBody != null) {
            return multipartBody;
        }
        if (fileInfos != null && fileInfos.size() > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            addParams(builder);
            fileInfos.forEach(fileInfo -> {
                RequestBody fileBody;
                if (fileInfo.file != null) {
                    fileBody = RequestBody.create(fileInfo.file, MediaType.parse("application/octet-stream"));
                } else if (fileInfo.fileInputStream != null) {
                    fileBody = createRequestBody(MediaType.parse("application/octet-stream"), fileInfo.fileInputStream);
                } else {
                    fileBody = RequestBody.create(fileInfo.fileContent, MediaType.parse(getMimeType(fileInfo.fileName)));
                }
                builder.addFormDataPart(fileInfo.partName, fileInfo.fileName, fileBody);
            });
            if (body != null && body.length() > 0) {
                builder.addPart(RequestBody.create(body, MultipartBody.FORM));
            }
            return builder.build();
        }
        if (body != null && body.length() > 0) {
            MediaType mediaType = MediaType.parse(headers.getOrDefault(SystemHeader.CONTENT_TYPE, "text/plain;charset=utf-8"));
            return RequestBody.create(body, mediaType);
        }
        FormBody.Builder builder = new FormBody.Builder();
        addParams(builder);
        return builder.build();
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    private void addParams(FormBody.Builder builder) {
        if (params != null) {
            params.forEach(builder::add);
        }
        if (encodedParams != null) {
            encodedParams.forEach(builder::addEncoded);
        }
    }

    private void addParams(MultipartBody.Builder builder) {
        if (params != null && !params.isEmpty()) {
            params.forEach((k, v) -> builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + k + "\""),
                    RequestBody.create(v, null)));
        }
    }

    public static String getMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
