package org.mashibing.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/23 21:52
 * @description
 */
@Service
public class VerificationCodeService {

    public String generatorCode(String passengerCode) {
        // 调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        String code = "111111";
        // 存入 redis
        System.out.println("存入 redis");

        // 返回值
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");
        return result.toString();
    }

}
