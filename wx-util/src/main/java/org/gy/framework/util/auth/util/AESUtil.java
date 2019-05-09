package org.gy.framework.util.auth.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtil {

    private static final Logger logger           = LoggerFactory.getLogger(AESUtil.class);

    private static final String ALGORITHM        = "AES";

    public static final String  DEFAULT_CHARSET  = "UTF-8";

    private static final int    DEFAULT_KEY_SIZE = 128;

    private static final byte[] DEFAULT_SEED     = {
            63,
            117,
            -86,
            -128,
            -97,
            -75,
            -66,
            -119,
            -27,
            26,
            114,
            107,
            -81,
            -7,
            -20,
            65
                                                 };

    private AESUtil() {
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        String originalText = "test";

        initKey(originalText);
        Key key = buildKey(DEFAULT_SEED);

        System.out.println("明文：" + originalText);
        String encryptStr = encrypt(originalText, key);
        System.out.println("加密之后：" + encryptStr);
        String decryptStr = decrypt(encryptStr, key);
        System.out.println("解密之后：" + decryptStr);
        System.out.println("解密后是否与明文相同：" + decryptStr.equals(originalText));

    }

    public static String encrypt(String originalText,
                                 Key key) {
        try {
            byte[] b = encrypt(originalText.getBytes(DEFAULT_CHARSET), key);
            return encryptBASE64(b);
        } catch (Exception e) {
            logger.error("encrypt exception,originalText=" + originalText, e);
        }
        return null;
    }

    public static String decrypt(String cipherText,
                                 Key key) {
        try {
            byte[] data = decryptBASE64(cipherText);
            return new String(decrypt(data, key), DEFAULT_CHARSET);
        } catch (Exception e) {
            logger.error("decrypt exception,cipherText=" + cipherText, e);
        }
        return null;
    }

    private static byte[] decrypt(byte[] data,
                                  Key key) {
        return execute(data, Cipher.DECRYPT_MODE, key);
    }

    private static byte[] encrypt(byte[] data,
                                  Key key) {
        return execute(data, Cipher.ENCRYPT_MODE, key);
    }

    private static byte[] execute(byte[] data,
                                  int opmode,
                                  Key key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(opmode, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            logger.error("execute exception:" + e.getMessage(), e);
        }
        return new byte[0];
    }

    private static byte[] decryptBASE64(String data) {
        return Base64.decodeBase64(data);
    }

    private static String encryptBASE64(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    private static byte[] initKey(String seed) throws NoSuchAlgorithmException {
        SecureRandom secureRandom;
        if (seed != null) {
            secureRandom = new SecureRandom(decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(DEFAULT_KEY_SIZE, secureRandom);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    public static Key buildKey(byte[] key) {
        return new SecretKeySpec(key, ALGORITHM);
    }

    public static Key buildKey() {
        return buildKey(DEFAULT_SEED);
    }

}
