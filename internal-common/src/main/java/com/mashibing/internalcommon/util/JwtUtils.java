package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/29 17:58
 * @description
 */
public class JwtUtils {

    // 盐
    private static final String SIGN = "n$jSR$GavF@GSks531jvh#*}“";

    // 生成 Token
    public static String generateToken(Map<String, String> map) {
        // token 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date date = calendar.getTime();

        JWTCreator.Builder builder = JWT.create();

        map.forEach((k, v) ->{
            builder.withClaim(k, v);
        });

        // 整合过期时间
        builder.withExpiresAt(date);

        // 生成 token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    // 解析 Token


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhang san");
        map.put("age", "18");
        // System.out.println("生成的token:" + generateToken(map));
        /*
        eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiemhhbmcgc2FuIiwiZXhwIjoxNjU2NTg0NjQxLCJhZ2UiOiIxOCJ9.LIV23UFov1YJDRS50IKoTlvD_JuF9H_B6oEcvk9lbpk
         */
    }

}
