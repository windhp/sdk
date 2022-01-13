package com.winning.profile;

/**
 * @description:
 * @author: xch
 * @time: 2022/1/10 11:33
 */
public class DHPProfile implements IProfile {

    private static DHPProfile profile;

    private ApiClient apiClient;

    private DHPProfile(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public synchronized static DHPProfile getProfile( String serviceCode, String appKey, String appSecret) {
        ApiClient client = new ApiClient(serviceCode, appKey, appSecret);
        profile = new DHPProfile(client);
        return profile;
    }

    @Override
    public ApiClient getApiClient() {
        return apiClient;
    }
}
