package com.meiken.springbootmeikenshardingsphereencrypt.util;

import com.google.common.base.Preconditions;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @Author glf
 * @Date 2020/9/28
 */
public class SensitiveEncryptUtil {


    private static final String AES_KEY = "1111111111111111";

    public static String getType() {
        return "AES";
    }

    public void init() {
    }

    public static String encrypt(final Object plaintext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (null == plaintext) {
            return null;
        }
        byte[] result = getCipher(Cipher.ENCRYPT_MODE).doFinal(StringUtils.getBytesUtf8(String.valueOf(plaintext)));
        return Base64.encodeBase64String(result);
    }

    public static Object decrypt(final String ciphertext) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (null == ciphertext) {
            return null;
        }
        byte[] result = getCipher(Cipher.DECRYPT_MODE).doFinal(Base64.decodeBase64(ciphertext));
        return new String(result, StandardCharsets.UTF_8);
    }

    private static Cipher getCipher(final int decryptMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher result = Cipher.getInstance(getType());
        result.init(decryptMode, new SecretKeySpec(createSecretKey(), getType()));
        return result;
    }

    private static byte[] createSecretKey() {
        return Arrays.copyOf(DigestUtils.sha1(AES_KEY), 16);
    }


    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        System.out.println(decrypt("3ED0f6yqD2NO/JYZmK87Ig=="));
    }
}
