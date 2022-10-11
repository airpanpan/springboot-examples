package com.example.shiro.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.shiro.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JWTUtil {

    //过期时间
    public static final long EXP = 36000;

    //密钥
    public static final String SECRET ="271dad09d1a71f27b7aeaa27306d5e24";



    public static String generateToken(Long userId, User user){
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        long now = System.currentTimeMillis();
        Date nowDate = new Date(now);

        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(user.getUsername())
                .setId(UUID.randomUUID().toString())
                .setClaims(claims)
                .setIssuedAt(nowDate) //签发时间
                .setExpiration(new Date(now + EXP)) //过期时间
                .signWith(hs256, SECRET.getBytes(StandardCharsets.UTF_8))
                .compact();
    }


    /**
     * Token的解密
     * @param token  加密后的token
     * @return
     */
    public static Claims parseJWT(String token) {
        // 得到DefaultJwtParser
        Claims claims = Jwts.parser()
                // 设置签名的秘钥
                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的jwt
                .parseClaimsJws(token).getBody();
        return claims;
    }


    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @return  是否正确
     */
    public static boolean  verify(String token){
        try{
            Algorithm algorithm=Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier=JWT.require(algorithm)
                    .build();
            DecodedJWT JWT=verifier.verify(token);
            return true;
        }catch (Exception ex){
           // throw ex;
            ex.printStackTrace();
            return false;
        }
    }



    public static void main(String[] args) {
        User user = new User();
        user.setId(1L);
        user.setPassword("271dad09d1a71f27b7aeaa27306d5e24");
        user.setUsername("zhangsan");
        String token = generateToken(1L, user);
        System.out.println("token = " + token);
        Claims claims = parseJWT(token);
        JSONObject json = (JSONObject)JSON.toJSON(claims.get("user"));
        //User user1 = json.toJavaObject(User.class);
        //System.out.println("user = " + user1);
        User user2 = getUser(token);
        System.out.println("user2 = " + user2);
        boolean verify = verify(token);
        System.out.println("verify = " + verify);
    }


    /**
     * 获取token中的信息无需secret解密也能获取
     *
     * @param token 密钥
     * @return  token中包含的用户ID
     */
    public static User  getUser(String token){
        try {
            DecodedJWT jwt=JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("user");
            User user = claim.as(User.class);
            return user;
        }catch (JWTDecodeException ex){
            return null;
        }
    }


}
