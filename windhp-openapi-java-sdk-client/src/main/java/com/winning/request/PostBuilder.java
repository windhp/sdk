package com.winning.request;

import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.exceptions.ClientException;
import com.winning.profile.IProfile;
import com.winning.util.MessageDigestUtil;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.winning.request.PostRequest.FileInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author xch
 * @date 2022/1/7 16:01
 */
public class PostBuilder extends OkHttpRequestBuilder<PostBuilder> {

    public static Log logger = LogFactory.getLog(PostBuilder.class);

    private static final Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");

    private final List<FileInfo> fileInfos;
    private String postBody;
    private MultipartBody multipartBody;

    public PostBuilder(OkHttpClient httpClient){
        super(httpClient);
        fileInfos = new ArrayList<>();
    }
    public PostBuilder(OkHttpClient httpClient, IProfile profile){
        super(httpClient, profile);
        fileInfos = new ArrayList<>();
    }

    @Override
    public RequestCall build() throws ClientException{
        String md5 = getBodyMd5(postBody);
        headers.put(SystemHeader.X_CONENT_MD5, md5);
        headers.put(SystemHeader.HTTP_METHOD, MethodType.POST.name());
        encodedParamsUrl();
        return new PostRequest(
                url,
                tag,
                params,
                encodedParams,
                headers,
                fileInfos,
                postBody,
                multipartBody,
                profile,
                id).
                build(httpClient);
    }

    private void encodedParamsUrl() throws ClientException{
        if (params == null || params.isEmpty()) {
            return;
        }
        if (!this.url.contains(Constants.QUESTION_MARK)) {
            this.url += Constants.QUESTION_MARK;
        }
        if (!this.url.endsWith(Constants.AND_MARK)) {
            this.url += Constants.AND_MARK;
        }
        this.url += getUrlString(params);
    }

    public PostBuilder body(String postBody) {
        this.postBody = postBody;
        return this;
    }

    public PostBuilder multipartBody(MultipartBody multipartBody) {
        this.multipartBody = multipartBody;
        return this;
    }

    public PostBuilder addFile(String partName, String fileName, byte[] content){
        FileInfo fileInfo=new FileInfo();
        fileInfo.partName = partName;
        fileInfo.fileName = fileName;
        fileInfo.fileContent = content;
        fileInfos.add(fileInfo);
        return this;
    }

    public PostBuilder addFile(String partName, String fileName, InputStream is){
        FileInfo fileInfo=new FileInfo();
        fileInfo.partName = partName;
        fileInfo.fileName = fileName;
        fileInfo.fileInputStream = is;
        fileInfos.add(fileInfo);
        return this;
    }

    public PostBuilder addFile(String partName, String fileName, File file){
        FileInfo fileInfo=new FileInfo();
        fileInfo.partName = partName;
        fileInfo.fileName = fileName;
        fileInfo.file = file;
        fileInfos.add(fileInfo);
        return this;
    }

    public PostBuilder addFile(String partName, String fileName, String content)
            throws UnsupportedEncodingException {
        return addFile(partName, fileName, content, StandardCharsets.UTF_8.toString());
    }

    public PostBuilder addFile(String partName, String fileName, String content, String charsetName)
            throws UnsupportedEncodingException{
        return addFile(partName, fileName, content.getBytes(charsetName));
    }

    public PostBuilder addFile(String partName, String fileName, byte[] content, String charsetName){
        return addFile(partName, fileName,content);
    }

    protected  String getBodyMd5(String postBody) throws ClientException{
        if (Constants.APPLICATION_FORM_URLENCODED.equals(headers.get(SystemHeader.CONTENT_TYPE))) {
            if (params == null || params.size() == 0) {
                return MessageDigestUtil.base64AndMd5("");
            }
            String md5Content = getUrlString(params);
            logger.debug(" POST md5Content :" + md5Content);
            return MessageDigestUtil.base64AndMd5(md5Content);
        }
        if (postBody == null || postBody.isEmpty()) {
            return MessageDigestUtil.base64AndMd5("");
        }
        Matcher m = pattern.matcher(postBody);
        String formatbody = m.replaceAll("");
        logger.debug(" POST md5Content :" + formatbody);
        return MessageDigestUtil.base64AndMd5(formatbody);
    }

    private String getUrlString(final Map<String, String> params) throws ClientException {
        List<String> queryList = new ArrayList<>();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                queryList.add(String.format("%s=%s", URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()),
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name())));
            }
        } catch (UnsupportedEncodingException e) {
            throw new ClientException( "UnsupportedEncodingException:" + e.getMessage());
        }
        return StringUtils.join(queryList, Constants.AND_MARK);
    }
}
