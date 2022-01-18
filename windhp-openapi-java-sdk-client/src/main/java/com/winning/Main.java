package com.winning;

import com.winning.constant.Constants;
import com.winning.constant.SystemHeader;
import com.winning.exceptions.ClientException;
import com.winning.exceptions.ServerException;
import com.winning.profile.DHPProfile;
import com.winning.profile.IProfile;
import com.winning.request.Response;

public class Main {
    public static void main(String[] args) {
        IProfile profile = DHPProfile.getProfile(
                "<your-service-code>",   // 购买的产品的ServiceCode
                "<your-app-key>",           // WinDHP采购商账号的AppKey
                "<your-app-secret>");     // WinDHP采购商账号的AppSecret
        try {
            Response response = DHPHttpClient.get(profile)
                    .url("http://172.16.30.147/opengateway/call/simple")
                    .addHeader(SystemHeader.CONTENT_TYPE, Constants.APPLICATION_JSON)
                    .build()
                    .execute();
            System.out.println(response.string());
        } catch (ServerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
