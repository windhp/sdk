package com.winning.profile;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/10 13:47
 */
public class ApiClient {
    private String serviceCode;

    private String appKey;

    private String appSecret;

    public ApiClient(String serviceCode, String accessKey, String accessSecret) {
        this.serviceCode = serviceCode;
        this.appKey = accessKey;
        this.appSecret = accessSecret;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

}
