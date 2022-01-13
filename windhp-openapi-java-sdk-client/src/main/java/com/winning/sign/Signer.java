package com.winning.sign;


import com.winning.sign.signers.HmacSHA1Signer;
import com.winning.sign.signers.HmacSHA256Signer;
import com.winning.sign.signers.HmacSM3Signer;
import com.winning.sign.signers.SignatureAlgorithm;

import java.security.NoSuchAlgorithmException;

/**
 * @description: 签名
 * @author: xch
 * @time: 2021/12/17 16:48
 */
public abstract class Signer {

    private static final Signer HMAC_SHA1 = new HmacSHA1Signer();

    private static final Signer HMAC_SHA256 = new HmacSHA256Signer();

    private static final Signer HMAC_SM3 = new HmacSM3Signer();


    public static Signer getSigner() {
        return HMAC_SHA256;
    }

    public static Signer getSigner(SignatureAlgorithm signatureAlgorithm) {
        switch (signatureAlgorithm){
            case HMAC_SHA1:
                return HMAC_SHA1;
            case HMAC_SHA256:
                return HMAC_SHA256;
            case HMAC_SM3:
                return HMAC_SM3;
            default:
                return HMAC_SHA256;
        }
    }

    /**
     * 获取签名
     * @param stringToSign: 签名内容
     * @param accessKeySecret: 签名秘钥
     * @return: java.lang.String 签名结果
     */
    public abstract String signString(String stringToSign, String accessKeySecret);

    /**
     * 获取签名算法名称
     * @return: java.lang.String
     */
    public abstract String getSignerName();

    /**
     * 对应算法的hash
     * @param raw
     * @return
     * @throws NoSuchAlgorithmException
     */
    public abstract byte[] hash(byte[] raw) throws NoSuchAlgorithmException;

}
