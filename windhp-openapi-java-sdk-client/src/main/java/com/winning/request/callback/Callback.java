package com.winning.request.callback;

import com.winning.exceptions.ClientException;
import com.winning.request.Response;
import okhttp3.Call;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/7 16:09
 */
public interface Callback {
    /**
     * 异步失败处理方法
     * @param call
     * @param e
     * @param traceId
     */
    void onFailure(Call call, ClientException e, String traceId);

    /**
     * 异步相应处理
     * @param call:
     * @param response:
     * @param traceId:
     * @return: void
     */
    void onResponse(Call call, Response response, String traceId);
}
