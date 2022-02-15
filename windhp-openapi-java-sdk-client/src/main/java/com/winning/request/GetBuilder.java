package com.winning.request;


import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.profile.IProfile;
import com.winning.util.MessageDigestUtil;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author xch
 * @date 2022/1/7 16:01
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> {


    public GetBuilder(OkHttpClient httpClient) {
        super(httpClient);
    }

    public GetBuilder(OkHttpClient httpClient, IProfile profile) {
        super(httpClient, profile);
    }

    @Override
    public RequestCall build() {
        if (params != null) {
            url = appendParams(url, params);
        }
        String md5 = getParamsMd5(url);
        headers.put(SystemHeader.X_CONENT_MD5, md5);
        headers.put(SystemHeader.HTTP_METHOD, MethodType.GET.name());
        return new GetRequest(url, tag, params, headers, profile, id).build(httpClient);
    }

    protected String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder builder = new StringBuilder();
        params.forEach((k,v)->{
            if(builder.length() == 0){
                builder.append(Constants.QUESTION_MARK);
            }else if (builder.length() > 0) {
                builder.append(Constants.AND_MARK);
            }
            builder.append(k);
            builder.append(Constants.EQUAL_MARK).append(v);
        });
        return url + builder.toString();
    }

    protected String getParamsMd5(String url) {
        if (url == null || !url.contains(Constants.QUESTION_MARK)) {
            return MessageDigestUtil.base64AndMd5(Constants.EMPTY_STRING);
        }
        String uri = url.substring(url.indexOf(Constants.QUESTION_MARK));
        String[] split = uri.split(Constants.AND_MARK);
        if (split == null || split.length == 0) {
            return MessageDigestUtil.base64AndMd5(Constants.EMPTY_STRING);
        }
        Map<String, String> uriMap = new TreeMap<>();
        String[] keys = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            String[] keyValue = split[i].split(Constants.EQUAL_MARK);
            String key = keyValue[0];
            String value = keyValue[1];
            uriMap.put(key, value);
            keys[i] = key;
        }
        uriMap.putAll(params);
        List<String> queryList = new ArrayList<>(split.length);
        for (Map.Entry<String, String> entry : uriMap.entrySet()) {
            queryList.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
        }

        return MessageDigestUtil.base64AndMd5(StringUtils.join(queryList, Constants.AND_MARK));
    }

}
