package com.mashibing.internalcommon.util;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/30 18:44
 * @description
 */
public class RedisPrefixUtils {

    // 乘客验证码的前缀
    public static String verificationCodePrefix = "passage-verification-code-";

    // token 存储的前缀
    public static String tokenPrefix = "token-";

    /**
     * 根据手机号，生成key
     * @param passengerCode
     * @return
     */
    public static String generatorKeyByPhone(String passengerCode) {
        return verificationCodePrefix + passengerCode;
    }

    /**
     * 根据手机号和身份标识，生成 token
     * @param phone
     * @param identity
     * @return
     */
    public static String generatorTokenKey(String phone, String identity) {
        return tokenPrefix + phone + identity;
    }


}
