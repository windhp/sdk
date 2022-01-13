package com.winning.constant;

/**
 * @description: 请求头的KEY
 * @author: xch
 * @time: 2021/12/17 15:40
 */
public class SystemHeader {

    /**
     * 请求头 HTTPMethod
     * HTTP的方法，全部大写，比如POST
     */
    public static final String HTTP_METHOD = "HTTPMethod";

    /**
     * 请求头 Content-Type
     * 请求中的Content-Type头的值，可为空
     */
    public static final String CONTENT_TYPE	 = "Content-Type";

    /**
     * 请求头 X-Service-Code
     * WinDHP平台的服务code ，所有的API商品发布后均会由平台自动生成唯一的服务code
     */
    public static final String X_SERVICE_CODE = "X-Service-Code";

    /**
     * 请求头 X-Ca-Key
     * WinDHP平台分配的appkey
     */
    public static final String X_CA_KEY = "X-Ca-Key";

    /**
     * 请求头 X-Ca-Nonce
     * 请求唯一标识，15分钟内 AppKey+API+Nonce 不能重复，与时间戳结合使用才能起到防重放作用。
     */
    public static final String X_CA_NONCE = "X-Ca-Nonce";

    /**
     * 请求头 X-Ca-Timestamp
     * 请求的时间戳，值为当前时间的毫秒数，也就是从1970年1月1日起至今的时间转换为毫秒，时间戳有效时间为15分钟。
     */
    public static final String X_CA_TIMESTAMP = "X-Ca-Timestamp";

    /**
     * 请求头 X-Conent-MD5
     * 注意先进行MD5摘要再进行Base64编码获取摘要字符串，用于校验QueryParams/Body参数是否被篡改
     * （post请求为body内容，get/delete请求为url参数（按照字典排序）)
     */
    public static final String X_CONENT_MD5 = "X-Content-MD5";

    /**
     * 请求头 X-Ca-Signature
     * 按照规则获取的签名值
     */
    public static final String X_CA_SIGNATURE = "X-Ca-Signature";

    /**
     * 响应头 X-Trace-Id
     * //请求唯一 ID，请求一旦进入 API 网关应用后，
     * API 网关就会生成请求 ID 并通过响应头返回给客户端，
     * 建议客户端与后端服务都记录此请求 ID，可用于问题排查与跟踪。
     */
    public static final String X_TRACE_ID = "X-Trace-Id";

    /**
     * 响应头 X-Ca-Error-Message
     * 签名验证失败时服务端返回的StringToSign，用户可比对签名串的差异
     */
    public static final String X_CA_ERROR_MESSAGE = "X-Ca-Error-Message";


}

















