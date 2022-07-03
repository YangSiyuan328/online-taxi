package org.mashibing.apipassenger.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import org.mashibing.apipassenger.remote.ServicePassengerUserClient;
import org.mashibing.apipassenger.remote.ServiceVerificationcodeClient;
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
    private ServiceVerificationcodeClient serviceVerificationcodeClient;

    @Resource
    private ServicePassengerUserClient passengerUserClient;

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
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);
        // 存入 redis
        stringRedisTemplate.opsForValue().set(key, String.valueOf(numberCode), 2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联

        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     *
     * @param passengerPhone    手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号，去 redis 读取验证码
        // 生成 key
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone);

        // 根据生成的 key 获取 value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的key：" + codeRedis);

        // 校验验证码
        if (StringUtils.isBlank(codeRedis)) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        // 登录成功后移除验证码
        stringRedisTemplate.delete(key);

        // 判断原来是否有用户，并进行对应的处理，进行远程服务的调用
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        passengerUserClient.loginOrRegister(verificationCodeDTO);

        // 颁发令牌，不应该使用魔法值，要用枚举
        String accessToken = JwtUtils.generateToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        // 将 accessToken 存到 redis 中
        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);

        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(passengerPhone, IdentityConstant.PASSENGER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);


        // 响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }

}
