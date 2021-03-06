package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;

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


    private static final String JWT_KEY_PHONE = "phone";

    // 乘客是1 司机是2
    private static final String JWT_KEY_IDENTITY = "identity";

    // token 类型
    private static final String JWT_TOKEN_TYPE = "tokenType";


    private static final String JWT_TOKEN_TIME = "tokenTime";


    // 生成 Token
    public static String generateToken(String phone, String identity, String tokenType) {

        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, phone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);
        // 防止每次生成的 token 一样
        map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();

        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        // 整合过期时间
//        builder.withExpiresAt(date);

        // 生成 token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));

        return sign;
    }

    // 解析 Token
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String phone = verify.getClaim(JWT_KEY_PHONE).asString();
        String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(phone);
        tokenResult.setIdentity(identity);

        return tokenResult;
    }

    /**
     * 校验 token 主要判断 token 是否异常
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token) {
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (Exception e) {

        }
        return tokenResult;
    }

//    public static void main(String[] args) {
//        String s = generateToken("18183158825", "1");
//        System.out.println(s);
//    }
}
