package com.risingtest.wanted.utils;

import java.security.MessageDigest;

public class SHA256 {
    public SHA256() {
    }

    public static String encrypt(String planText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(planText.getBytes());
            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();

            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 255) + 256, 16).substring(1));
            }

            StringBuffer hexString = new StringBuffer();

            for (byte byteDatum : byteData) {
                String hex = Integer.toHexString(255 & byteDatum);
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception var7) {
            var7.printStackTrace();
            throw new RuntimeException();
        }
    }
}
