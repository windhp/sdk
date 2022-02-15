package com.winning.request.callback;

import com.winning.exceptions.ClientException;
import com.winning.request.Response;
import okhttp3.Call;

/**
 * @author xch
 * @date 2022/1/7 16:09
 */
public interface Callback {
    /**
     * 异步失败处理方法
     * @param call call
     * @param e 客户端异常
     * @param traceId traceId
     */
    void onFailure(Call call, ClientException e, String traceId);

    /**
     * 异步相应处理
     * @param call: call
     * @param response: 响应类
     * @param traceId traceId
     */
    void onResponse(Call call, Response response, String traceId);
}
