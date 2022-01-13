package com.winning.sign.signers;

import com.winning.constant.Constants;
import com.winning.sign.Signer;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: HmacSHA256签名算法
 * @author: xch
 * @time: 2021/12/17 16:51
 */
public class HmacSM3Signer extends Signer {

    private static final String ALGORITHM_NAME = "HMAC-SM3";
    private static String HASH_SM3 = "SM3";

    @Override
    public String signString(String stringToSign, String accessKeySecret) {
        try {
            SecretKey key = new SecretKeySpec((accessKeySecret).getBytes(Constants.ENCODING), ALGORITHM_NAME);
            HMac mac = new HMac(new SM3Digest());
            byte[] sign = new byte[mac.getMacSize()];
            byte[] inputBytes = stringToSign.getBytes(Constants.ENCODING);
            mac.init(new KeyParameter(key.getEncoded()));
            mac.update(inputBytes, 0, inputBytes.length);
            mac.doFinal(sign, 0);
            return DatatypeConverter.printBase64Binary(sign);
        } catch (UnsupportedEncodingException e) {
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
        BouncyCastleProvider provider = new BouncyCastleProvider();
        MessageDigest digest = MessageDigest.getInstance(HASH_SM3, provider);
        return digest.digest(raw);
    }

}
