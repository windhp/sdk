package com.winning.request;


import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.exceptions.ClientException;
import com.winning.profile.IProfile;
import com.winning.util.MessageDigestUtil;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author xch
 * @date 2022/1/7 16:01
 */
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> {

    public static Log logger = LogFactory.getLog(GetBuilder.class);

    public GetBuilder(OkHttpClient httpClient) {
        super(httpClient);
    }

    public GetBuilder(OkHttpClient httpClient, IProfile profile) {
        super(httpClient, profile);
    }

    @Override
    public RequestCall build() throws ClientException{
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
        return url + builder;
    }

    protected String getParamsMd5(String url) throws ClientException {
        if (url == null || !url.contains(Constants.QUESTION_MARK)) {
            return MessageDigestUtil.base64AndMd5(Constants.EMPTY_STRING);
        }
        String uri = url.substring(url.indexOf(Constants.QUESTION_MARK));
        String[] split = uri.split(Constants.AND_MARK);
        if (split.length == 0) {
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
        try {
            for (Map.Entry<String, String> entry : uriMap.entrySet()) {
                queryList.add(String.format("%s=%s", entry.getKey(), URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name())));
            }
        } catch (
            UnsupportedEncodingException e) {
            throw new ClientException( "UnsupportedEncodingException:" + e.getMessage());
        }
        String md5Content = StringUtils.join(queryList, Constants.AND_MARK);
        logger.debug("MD5 Content :" + md5Content);
        return MessageDigestUtil.base64AndMd5(md5Content);
    }

}
