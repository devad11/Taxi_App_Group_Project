package com.taxiproject.group6.taxiapp.classes;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {

    private final static String AES = "AES";
    private final static String TAG = "Encryption";

    public static String encryptCardNumber(String cardNo){
        return encrypt(cardNo);
    }

    public static String decryptCardNumber(String cardNo){
        return decrypt(cardNo);
    }

    private static String encrypt( String cardNo) {
        String encryptedValue = "FAILURE";
        try{
            SecretKeySpec key = generateKey();

            Cipher c = Cipher.getInstance(AES);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(cardNo.getBytes());
            encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        } catch (Exception e) {
            Log.d(TAG, "EncryptionHelper --> encrypt()");
            e.printStackTrace();
        }

        return encryptedValue;
    }

    private static String decrypt(String cipherText)
    {
        String res = "";
        try {
            SecretKeySpec secretKey = generateKey();
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = Base64.decode(cipherText, Base64.DEFAULT);
            byte[] val = cipher.doFinal(bytes);
            res = new String(val);
        }catch(Exception e){
            Log.d(TAG, "EncryptionHelper --> decrypt()");
        }
        return res;
    }

    private static SecretKeySpec generateKey() {
        String password = "qwerty";
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = password.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            byte[] key = digest.digest();
            return new SecretKeySpec(key, "AES");
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
