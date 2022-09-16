package com.example.demo.groovy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MD5Util {

    private static final Logger log = LoggerFactory.getLogger(MD5Util.class);
    /**
     * Md5加密 返回byte[]
     *
     * @param s
     * @return
     */
    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    /**
     * byte[]解密 返回string
     *

     * @param hash
     * @return
     */
    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * hash加密
     *
     * @param s
     * @return
     */
    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("not supported charset...{}", e);
            return s;
        }
    }

    public static void main(String[] args) {
        String a = "123";
        byte[] bytes = md5(a);
        String s = toHex(bytes);
        System.out.println(s);
        String hash = hash(a);
        System.out.println(hash);
        String substring = hash.substring(8, 24);
        System.out.println(substring);
    }
}
