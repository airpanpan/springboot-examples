package com.example.shiro.utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class DigestsUtil {



    public static final String SHA1 = "SHA-1";

    public static final Integer ITERATIONS = 1024;

    public static final String MD5 = "md5";


    /**
     * sha1加密方法
     * @param pwd
     * @param salt
     * @return
     */
    public static String sha1(String pwd, String salt){
        return new SimpleHash(SHA1, pwd, salt, ITERATIONS).toHex();
    }


    public static String md5(String pwd, String salt){
        return new SimpleHash(MD5, pwd, salt, ITERATIONS).toHex();
    }

    public static String md5(String pwd, ByteSource salt){
        return new SimpleHash(MD5, pwd, salt, ITERATIONS).toHex();
    }


    public static String generateSalt(){
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        return generator.nextBytes().toHex();
    }


    public static void main(String[] args) {
        String salt = generateSalt();
        System.out.println("salt = " + salt);

        String zhangsan = md5("123", "zhangsan");
        System.out.println("pwd = " + zhangsan);

        ByteSource zhangsan1 = ByteSource.Util.bytes("zhangsan");
        String s = md5("123", zhangsan1);
        System.out.println("pwd2 = " + s);
    }




}
