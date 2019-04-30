package com.example.bus_uni;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {
    public static SecretKey generateKey() {
        try {
            //Make secretKey from pass without using "key"?
            String key = "1Hbfh667adfDEJ78";
            return new SecretKeySpec(key.getBytes(), "AES");
            //return new SecretKeySpec(password.getBytes(), "AES");

        } catch (Exception e) {
            return null;
        }
    }

    public static String encryptPass(String pass, SecretKey secret) {
        try {

            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            //make it String instead of byte: new String(cipher.doFinal(pass.getBytes()), "UTF-8");
            byte[] cipherText = cipher.doFinal(pass.getBytes("UTF-8"));
            String enc = cipherText.toString();
            return enc;
        } catch (Exception e) {
            return null;
        }
    }

   /* public static String decryptPass(String pass, SecretKey secret) {
        try {
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            String decryptString = new String(cipher.doFinal(pass.getBytes()), "UTF-8");
            return decryptString;
        } catch (Exception e) {
            return null;
        }
    }*/
}
