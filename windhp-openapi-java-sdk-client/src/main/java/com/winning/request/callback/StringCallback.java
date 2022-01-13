package com.winning.request.callback;

import com.winning.exceptions.ClientException;
import com.winning.request.PostRequest;
import com.winning.request.Response;
import okhttp3.Call;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/7 16:11
 */
public abstract class StringCallback implements Callback {

    public static final Log logger = LogFactory.getLog(PostRequest.class);

    @Override
    public void onResponse(Call call, Response response, String id) {
        try {
            onSuccess(call, response.string(), id);
        } catch (ClientException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     *
     */
    @Override
    public void onFailure(Call call, ClientException e, String id) {
        logger.error("onFailure traceId: " + id);
        logger.error(e.getMessage());
    }

    /**
     * response字符串成功请求的响应处理
     * @param call:
     * @param response:
     * @param id:
     * @return: void
     */
    public abstract void onSuccess(Call call, String response, String id);
}
