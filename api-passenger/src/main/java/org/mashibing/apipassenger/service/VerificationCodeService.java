package org.mashibing.apipassenger.service;

import com.alibaba.fastjson.JSONObject;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
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

    /**
     * 生成验证码
     *
     * @param passengerPhone 手机号
     * @return
     */
    public ResponseResult generatorCode(String passengerPhone) {
        // 调用验证码服务，获取验证码

        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // 存入 redis
        // key,value,过期时间
        String key = generatorKeyByPhone(passengerPhone);
        // 存入 redis
        stringRedisTemplate.opsForValue().set(key, String.valueOf(numberCode), 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联

        return ResponseResult.success("");
    }

    private String generatorKeyByPhone(String passengerCode) {
        return verificationCodePrefix + passengerCode;
    }

    /**
     * 校验验证码
     *
     * @param passengerCode    手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerCode, String verificationCode) {
        // 根据手机号，去 redis 读取验证码
        System.out.println("据手机号，去 redis 读取验证码");

        // 生成 key
        String key = generatorKeyByPhone(passengerCode);

        // 根据生成的 key 获取 value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的key：" + codeRedis);

        // 校验验证码
        System.out.println("校验验证码");

        // 判断原来是否有用户，并进行对应的处理
        System.out.println("判断原来是否有用户，并进行对应的处理");

        // 颁发令牌
        System.out.println("颁发令牌");

        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");

        return ResponseResult.success(tokenResponse);
    }

}
