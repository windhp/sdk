package com.winning.sign.signers;

import com.winning.constant.Constants;
import com.winning.sign.Signer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * HmacSHA256签名算法
 * @author xch
 * @date 2021/12/17 16:51
 */
public class HmacSHA1Signer extends Signer {

    private static final String ALGORITHM_NAME = "HmacSHA1";

    @Override
    public String signString(String stringToSign, String accessKeySecret) {
        try {
            Mac mac = Mac.getInstance(ALGORITHM_NAME);
            mac.init(new SecretKeySpec(accessKeySecret.getBytes(Constants.ENCODING_UTF8), ALGORITHM_NAME));
            byte[] signData = mac.doFinal(stringToSign.getBytes(Constants.ENCODING_UTF8));
            return DatatypeConverter.printBase64Binary(signData);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e.toString());
        } catch (UnsupportedEncodingException e) {
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
        return null;
    }

}
