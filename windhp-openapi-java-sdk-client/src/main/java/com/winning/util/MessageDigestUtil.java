
package com.winning.util;

import com.winning.constant.Constants;
import com.winning.exceptions.ClientException;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xch
 * @date 2022/1/7 16:09
 */
public class MessageDigestUtil {
    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     */
    public static String base64AndMd5(String str) {
        if (str == null) {
            throw new IllegalArgumentException("inStr can not be null");
        }
        try {
            return base64AndMd5(toBytes(str));
        } catch (ClientException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 先进行MD5摘要再进行Base64编码获取摘要字符串
     */
    public static String base64AndMd5(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(bytes);
            Base64 base64 = new Base64();
            final byte[] enbytes = base64.encode(md.digest());
            return new String(enbytes);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }

    /**
     * UTF-8编码转换为ISO-9959-1
     */
    public static String utf8ToIso88591(String str)throws ClientException {
        if (str == null) {
            return str;
        }
        return new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }

    /**
     * ISO-9959-1编码转换为UTF-8
     */
    public static String iso88591ToUtf8(String str)throws ClientException {
        if (str == null) {
            return str;
        }
        return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    /**
     * String转换为字节数组
     */
    private static byte[] toBytes(final String str) throws ClientException{
        if (str == null) {
            return new byte[0];
        }
        return str.getBytes(StandardCharsets.UTF_8);
    }

}
