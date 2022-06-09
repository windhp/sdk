package com.winning;

import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.exceptions.ClientException;
import com.winning.exceptions.ServerException;
import com.winning.profile.DHPProfile;
import com.winning.profile.IProfile;
import com.winning.request.Response;
import com.winning.request.callback.StringCallback;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author xch
 * @date  2022/1/7 18:11
 */
public class OpenAPISdkTest {

    @Test
    public void MyTest(){
        // 产品：主数据
        //https://openapi.windhp.com/call/simple
        // 接口调用 Q_SQDMBXX 查询申请单模板信息
        String serviceCode = "48971667513344";
        serviceCode = "e700a5b2549ab5bbb51a776e651393aa#GETMZHZZJH";
        String appkey = "68929467060224";
        String appSecret = "7GUc4gKGkbsYtHk40GD4dkTKnnqsWvD4";
        String url = "http://localhost:7070/call/simple";
        IProfile profile = DHPProfile.getProfile(serviceCode, appkey, appSecret);
        try {
            long time1 = System.currentTimeMillis();
            Response response = DHPHttpClient.post(profile)
//                    .addHeader(SystemHeader.CONTENT_TYPE, "application/x-www-form-urlencoded")
                    .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .url(url)
                    .addHeader("Win-Biz-Code","GETMZHZZJH")
                    .addHeader("Win-Region-Id","100021")
                    .addParams("yydm","100021")
                    .addParams("action","GETMZHZZJH")
                    .addParams("hzxm","周远杰")
                    .addParams("zjh","452421198002151618")
                    .body("{" +
                            "  \"zjh\": \"119003078371\"" +
                            "}")
                    .build()
                    .execute();
            long time2 = System.currentTimeMillis();
            System.out.println(response.string());
            System.out.println("耗时：" + (int)(time2-time1));
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
    public void getNaliTest(){
        IProfile profile = DHPProfile.getProfile("41563211440128", "62989828116480", "xxx");
        try {
            Response response = DHPHttpClient.post(profile)
                    .url("http:///opengateway/call/simple")
                    .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .body("{" +
                            "  \"zjh\": \"119003078371\"" +
                            "}")
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
    public void getTest(){
        IProfile profile = DHPProfile.getProfile("", "", "");
        try {
            Response response = DHPHttpClient.post(profile)
                    .url("http:///opengateway/call/simple")
                    .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .body("{\"hzxm\":\"张三\",\"zjh\":\"319001130209\"}")
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
        IProfile profile = DHPProfile.getProfile("63719546888192", "65203300044800", "xxx");
        DHPHttpClient.get(profile)
            .url("http:///opengateway/call/simple")
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
        DHPProfile profile = DHPProfile.getProfile("63719546888192", "65203300044800", "xxx");
        DHPHttpClientBuilder dhpHttpClientBuilder = new DHPHttpClientBuilder();
        Response response = dhpHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .build().get(profile)
                .url("http:///opengateway/call/simple")
                .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .build()
                .execute();
        Assert.assertEquals(response.code(), 200);
        Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
        Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
    }

    @Test
    public void postTest() throws Exception {
        IProfile profile = DHPProfile.getProfile("57874954723328", "65203300044800", "xxx");
        Response response = DHPHttpClient.post(profile)
                .url("http:///opengateway/call/simple")
                .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .body("{\"identity\":\"3301888881\",\"answers\":{\"a1\":\"0\",\"a2\":\"1\",\"a3\":\"0\",\"a4\":\"1\",\"a5\":\"1\",\"a6\":\"0\",\"a7\":\"1\",\"a8\":\"0\",\"a9\":\"1\",\"a10\":\"0\",\"a11\":\"1\",\"a12\":\"1\",\"a13\":\"0\",\"a14\":\"1\",\"a15\":\"1\",\"a16\":\"0\",\"a17\":\"1\",\"a18\":\"0\",\"a19\":\"1\",\"a20\":\"1\",\"a21\":\"1\",\"a22\":\"1\",\"a23\":\"1\",\"a24\":\"0\",\"a25\":\"0\",\"a26\":\"1\",\"a27\":\"0\",\"a28\":\"1\",\"a29\":\"0\",\"a30\":\"0\"}}")
                .build()
                .execute();

        System.out.println(response.body().string());
        Assert.assertEquals(response.code(), 200);
        Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
        Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
    }

    public static void postAsyncTest() {
        IProfile profile = DHPProfile.getProfile("57874954723328", "65203300044800", "xxx");
        try {
            DHPHttpClient.post(profile)
                    .url("http:///opengateway/call/simpl")
                    .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .body("{\"identity\":\"330188888811111511\",\"answers\":{\"a1\":\"0\",\"a2\":\"1\",\"a3\":\"0\",\"a4\":\"1\",\"a5\":\"1\",\"a6\":\"0\",\"a7\":\"1\",\"a8\":\"0\",\"a9\":\"1\",\"a10\":\"0\",\"a11\":\"1\",\"a12\":\"1\",\"a13\":\"0\",\"a14\":\"1\",\"a15\":\"1\",\"a16\":\"0\",\"a17\":\"1\",\"a18\":\"0\",\"a19\":\"1\",\"a20\":\"1\",\"a21\":\"1\",\"a22\":\"1\",\"a23\":\"1\",\"a24\":\"0\",\"a25\":\"0\",\"a26\":\"1\",\"a27\":\"0\",\"a28\":\"1\",\"a29\":\"0\",\"a30\":\"0\"}}")
                    .build()
                    .executeAsync(new StringCallback() {
                        @Override
                        public void onSuccess(Call call, String response, String id) {
                            System.out.println(response);
                        }
                    });
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postBuilder() throws Exception  {
        IProfile profile = DHPProfile.getProfile("57874954723328", "65203300044800", "xxx");
        DHPHttpClientBuilder dhpHttpClientBuilder = new DHPHttpClientBuilder();
        Response response = dhpHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .build().post(profile)
                .url("http:///opengateway/call/simple")
                .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .body("{\"identity\":\"330188888811111511\",\"answers\":{\"a1\":\"0\",\"a2\":\"1\",\"a3\":\"0\",\"a4\":\"1\",\"a5\":\"1\",\"a6\":\"0\",\"a7\":\"1\",\"a8\":\"0\",\"a9\":\"1\",\"a10\":\"0\",\"a11\":\"1\",\"a12\":\"1\",\"a13\":\"0\",\"a14\":\"1\",\"a15\":\"1\",\"a16\":\"0\",\"a17\":\"1\",\"a18\":\"0\",\"a19\":\"1\",\"a20\":\"1\",\"a21\":\"1\",\"a22\":\"1\",\"a23\":\"1\",\"a24\":\"0\",\"a25\":\"0\",\"a26\":\"1\",\"a27\":\"0\",\"a28\":\"1\",\"a29\":\"0\",\"a30\":\"0\"}}")
                .build()
                .execute();
        Assert.assertEquals(response.code(), 200);
        Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
        Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
    }


    /**
     * SSl 请求HTTPS
     * @throws ClientException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    @Test
    public void getHttpsTestt() throws ClientException, IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, CertificateException, UnrecoverableKeyException {
        X509TrustManager myTrustManager = new X509TrustManager() {
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
        };
        HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                System.out.println(hostname);
                return true;
            }
        };
        SSLContext sslCtx = SSLContext.getInstance("SSL");
        sslCtx.init(null, new TrustManager[] { myTrustManager }, new SecureRandom());
        SSLSocketFactory mySSLSocketFactory = sslCtx.getSocketFactory();

        DHPHttpClientBuilder dhpHttpClientBuilder = new DHPHttpClientBuilder();
        IProfile profile = DHPProfile.getProfile("415632440128", "6298916480", "xxx");
        try {
            Response response = dhpHttpClientBuilder.sslSocketFactory(mySSLSocketFactory, myTrustManager)
                    .hostnameVerifier(myHostnameVerifier)
                    .build()
                    .post(profile)
                    .url("http://localhost/opengateway/call/simple")
                    .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .body("{" +
                            "  \"zjh\": \"11900371\"" +
                            "}")
                    .build()
                    .execute();
            System.out.println(response.string());
            Assert.assertEquals(response.code(), 200);
            Assert.assertTrue(StringUtils.isEmpty(response.getCaErrorMessage()));
            Assert.assertTrue(StringUtils.isNoneEmpty(response.getTraceId()));
        }catch (Exception e) {
            e.printStackTrace();
        }
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
                    try {
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
                    } catch (ClientException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println();
        }
    }

}