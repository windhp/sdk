package com.winning;

import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.exceptions.ClientException;
import com.winning.exceptions.ServerException;
import com.winning.profile.DHPProfile;
import com.winning.profile.IProfile;
import com.winning.request.Response;
import com.winning.request.callback.StringCallback;
import okhttp3.Call;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/7 18:11
 */
public class OpenAPISdkTest {

    @Test
    public void getTest(){
        IProfile profile = DHPProfile.getProfile("63719546888192", "65203300044800", "EaET70NgqMfYApKebWSWNRskjR2BjRyI");
        Response response = null;
        try {
            response = DHPHttpClient.get(profile)
                    .url("http://172.16.30.147/opengateway/call/simple")
                    .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .build()
                    .execute();
            System.out.println(response.string());
            Assert.assertEquals(response.code(), 200);
            Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
            Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
        } catch (ServerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void getAsyncTest() throws Exception {
        IProfile profile = DHPProfile.getProfile("63719546888192", "65203300044800", "EaET70NgqMfYApKebWSWNRskjR2BjRyI");
        DHPHttpClient.get(profile)
            .url("http://172.16.30.147/opengateway/call/simple")
            .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
            .build()
            .executeAsync(new StringCallback() {
                @Override
                public void onSuccess(Call call, String response, String id) {
                    System.out.println(response);
                }

                @Override
                public void onResponse(Call call, Response response, String id) {
                    Assert.assertEquals(response.code(), 200);
                    Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
                    Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
                }
            });
    }

    @Test
    public void getBuilder() throws Exception  {
        DHPProfile profile = DHPProfile.getProfile("63719546888192", "65203300044800", "EaET70NgqMfYApKebWSWNRskjR2BjRyI");
        DHPHttpClientBuilder dhpHttpClientBuilder = new DHPHttpClientBuilder();
        Response response = dhpHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .build().get(profile)
                .url("http://172.16.30.147/opengateway/call/simple")
                .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .build()
                .execute();
        Assert.assertEquals(response.code(), 200);
        Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
        Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
    }

    @Test
    public void postTest() throws Exception {
        IProfile profile = DHPProfile.getProfile("57874954723328", "65203300044800", "EaET70NgqMfYApKebWSWNRskjR2BjRyI");
        Response response = DHPHttpClient.post(profile)
                .url("http://172.16.30.147/opengateway/call/simple")
                .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .body("{\"identity\":\"330188888811111511\",\"answers\":{\"a1\":\"0\",\"a2\":\"1\",\"a3\":\"0\",\"a4\":\"1\",\"a5\":\"1\",\"a6\":\"0\",\"a7\":\"1\",\"a8\":\"0\",\"a9\":\"1\",\"a10\":\"0\",\"a11\":\"1\",\"a12\":\"1\",\"a13\":\"0\",\"a14\":\"1\",\"a15\":\"1\",\"a16\":\"0\",\"a17\":\"1\",\"a18\":\"0\",\"a19\":\"1\",\"a20\":\"1\",\"a21\":\"1\",\"a22\":\"1\",\"a23\":\"1\",\"a24\":\"0\",\"a25\":\"0\",\"a26\":\"1\",\"a27\":\"0\",\"a28\":\"1\",\"a29\":\"0\",\"a30\":\"0\"}}")
                .build()
                .execute();

        System.out.println(response.body().string());
        Assert.assertEquals(response.code(), 200);
        Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
        Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
    }

    public static void postAsyncTest() {
        IProfile profile = DHPProfile.getProfile("57874954723328", "65203300044800", "EaET70NgqMfYApKebWSWNRskjR2BjRyI");
        DHPHttpClient.post(profile)
                .url("http://172.16.30.147/opengateway/call/simpl")
                .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .body("{\"identity\":\"330188888811111511\",\"answers\":{\"a1\":\"0\",\"a2\":\"1\",\"a3\":\"0\",\"a4\":\"1\",\"a5\":\"1\",\"a6\":\"0\",\"a7\":\"1\",\"a8\":\"0\",\"a9\":\"1\",\"a10\":\"0\",\"a11\":\"1\",\"a12\":\"1\",\"a13\":\"0\",\"a14\":\"1\",\"a15\":\"1\",\"a16\":\"0\",\"a17\":\"1\",\"a18\":\"0\",\"a19\":\"1\",\"a20\":\"1\",\"a21\":\"1\",\"a22\":\"1\",\"a23\":\"1\",\"a24\":\"0\",\"a25\":\"0\",\"a26\":\"1\",\"a27\":\"0\",\"a28\":\"1\",\"a29\":\"0\",\"a30\":\"0\"}}")
                .build()
                .executeAsync(new StringCallback() {
                    @Override
                    public void onSuccess(Call call, String response, String id) {
                        System.out.println(response);
                    }
                });
    }

    public static void main(String[] args) {
        postAsyncTest();
    }

    @Test
    public void postBuilder() throws Exception  {
        IProfile profile = DHPProfile.getProfile("57874954723328", "65203300044800", "EaET70NgqMfYApKebWSWNRskjR2BjRyI");
        DHPHttpClientBuilder dhpHttpClientBuilder = new DHPHttpClientBuilder();
        Response response = dhpHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .build().post(profile)
                .url("http://172.16.30.147/opengateway/call/simple")
                .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .body("{\"identity\":\"330188888811111511\",\"answers\":{\"a1\":\"0\",\"a2\":\"1\",\"a3\":\"0\",\"a4\":\"1\",\"a5\":\"1\",\"a6\":\"0\",\"a7\":\"1\",\"a8\":\"0\",\"a9\":\"1\",\"a10\":\"0\",\"a11\":\"1\",\"a12\":\"1\",\"a13\":\"0\",\"a14\":\"1\",\"a15\":\"1\",\"a16\":\"0\",\"a17\":\"1\",\"a18\":\"0\",\"a19\":\"1\",\"a20\":\"1\",\"a21\":\"1\",\"a22\":\"1\",\"a23\":\"1\",\"a24\":\"0\",\"a25\":\"0\",\"a26\":\"1\",\"a27\":\"0\",\"a28\":\"1\",\"a29\":\"0\",\"a30\":\"0\"}}")
                .build()
                .execute();
        Assert.assertEquals(response.code(), 200);
        Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
        Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
    }

    @Test
    public void getHttpsTest() throws Exception {
        Response execute = DHPHttpClient.get().url("https://www.baifubao.com/callback?cmd=1059&callback=phone&phone=15639009724").build().execute();
        System.out.println(execute.body().string());
        Assert.assertTrue(execute.code() == 200);
    }

    @Test
    public void postHttpsTest() throws ClientException, IOException {
        Response execute = DHPHttpClient.get().url("https://www.baidu.com").build().execute();
        System.out.println(execute.body().string());
        Assert.assertTrue(execute.code() == 200);
    }

    private static void pressureTest() {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 1; j < 10; j++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DHPHttpClient.get().url("http://localhost:8077/standard/test?organ_id=" + j).build().executeAsync(new StringCallback() {
                        @Override
                        public void onResponse(Call call, Response response, String id) {
                            HttpUrl url = call.request().url();
                            try {
                                String string = response.body().string();
                                System.out.println(url + "==" + string);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onSuccess(Call call, String response, String id) {
                        }
                    });
                }
            }).start();
            System.out.println();
        }
    }

}
