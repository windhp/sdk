<h1 align="center">WinDHP SDK for Java</h1>


欢迎使用 WinDHP SDK for Java。
WinDHP SDK for Java 让您不用复杂编程即可访问WinDHP产品。
这里向您介绍如何获取 WinDHP SDK for Java 并开始调用。
如果您在使用 WinDHP SDK for Java 的过程中遇到任何问题，欢迎联系我们提出疑问 [WinDHP官网](http://172.16.9.131/contact)，我们随时解答。

## 环境要求
1. 要使用 WinDHP SDK for Java ，您需要一个WinDHP账号以及一对`AppKey`和`AppSecret`。 请在WinDHP数字健康平台的[采购商中心管理页面](http://172.16.9.131/crm/#/index)上创建和查看您的AppKey，或者联系您的系统管理员。
2. 要使用 WinDHP SDK for Java 访问某个产品的API，您需要事先在[WinDHP云市场](http://172.16.9.131/market/#/api/list)中购买这个产品。
3. WinDHP SDK for Java 需要1.8以上的JDK。

## 安装依赖
作为采购商，需要调用WinDHP的产品，只需要安装`windhp-openapi-java-sdk-client`。
#### 通过Maven来管理项目依赖(推荐)
如果您使用Apache Maven来管理Java项目，只需在项目的`pom.xml`文件加入相应的依赖项即可。您可以在[WinDHP开发资源](http://172.16.9.131/cooperate)中下载各云产品的Maven依赖。
以使用Client SDK为例，您只需在`pom.xml`中声明以下两个依赖：
```xml
<dependency>
    <groupId>com.winning</groupId>
    <artifactId>windhp-openapi-java-sdk-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

如果 maven 没有从中央存储库下载 jar 包，则需要将这些依赖项添加到`pom.xml`文件中，否则将报告 NoClassDefFoundError 异常
```xml
 <dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
    <version>1.15</version>
 </dependency>

 <dependency>    
    <groupId>org.bouncycastle</groupId>
    <artifactId>bcprov-jdk15on</artifactId>
    <version>1.69</version>
 </dependency>
```

## 使用诊断
通过响应头Response-header的`X-Trace-Id` 或 `X-Ca-Error-Message` ，帮助开发者快速定位，为开发者提供解决方案。

## 快速使用

以下这个代码示例向您展示了调用 WinDHP SDK for Java 的3个主要步骤：
1. 创建DefaultAcsClient实例并初始化。
2. 创建API请求并设置参数。
3. 发起请求并处理应答或异常。

```java
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
                "<your-app-key>",        // WinDHP采购商账号的AppKey
                "<your-app-secret>");    // WinDHP采购商账号的AppSecret
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
```



