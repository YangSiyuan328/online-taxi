package org.mashibing.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.mashibing.apipassenger.remote.ServiceVerificationcodeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/23 21:52
 * @description
 */
@Service
public class VerificationCodeService {

    @Resource
    ServiceVerificationcodeClient serviceVerificationcodeClient;

    // 乘客验证码的前缀
    private String verificationCodePrefix = "passage-verification-code-";

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult generatorCode(String passengerCode) {
        // 调用验证码服务，获取验证码

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // 存入 redis
        // key,value,过期时间
        String key = verificationCodePrefix + passengerCode;
        // 存入 redis
        stringRedisTemplate.opsForValue().set(key, String.valueOf(numberCode), 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联

        return ResponseResult.success("");
    }

}
