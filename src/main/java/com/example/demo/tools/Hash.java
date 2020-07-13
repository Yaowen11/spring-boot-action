package com.example.demo.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author z
 */
public class Hash {
    public static String hash(String origin, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] md5hash = messageDigest.digest(origin.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            int n;
            for (byte b : md5hash) {
                n = b;
                if (n < 0) {
                    n += 256;
                }
                if (n < 16) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(n));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }


}
