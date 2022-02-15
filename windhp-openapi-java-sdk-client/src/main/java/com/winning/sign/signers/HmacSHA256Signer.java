package com.winning.sign.signers;

import com.winning.sign.Signer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HmacSHA256签名算法
 * @author xch
 * @date 2021/12/17 16:51
 */
public class HmacSHA256Signer extends Signer {

    private static final String ALGORITHM_NAME = "HmacSHA256";
    private static String HASH_SHA256 = "SHA-256";

    @Override
    public String signString(String stringToSign, String accessKeySecret) {
        try {
            Mac hmacSha256 = Mac.getInstance(ALGORITHM_NAME);
            SecretKeySpec secretKey = new SecretKeySpec(accessKeySecret.getBytes(), ALGORITHM_NAME);
            hmacSha256.init(secretKey);
            return DatatypeConverter.printBase64Binary(hmacSha256.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e.toString());
        }
    }


    @Override
    public String getSignerName() {
        return ALGORITHM_NAME;
    }

    @Override
    public byte[] hash(byte[] raw) throws NoSuchAlgorithmException {
        if(null == raw){
            return null;
        }
        MessageDigest digest = MessageDigest.getInstance(HASH_SHA256);
        return digest.digest(raw);
    }

}
