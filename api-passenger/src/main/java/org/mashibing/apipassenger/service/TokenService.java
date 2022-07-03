package org.mashibing.apipassenger.service;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/7/3 15:27
 * @description
 */
public class TokenService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult refreshToken(String refreshTokenSrc){
        // 解析 refreshToken
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        String phone = tokenResult.getPhone();
        String identity = tokenResult.getIdentity();


        // 去读取 redis 中的 refreshToken
        String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);


        // 校验 refreshToken
        if (StringUtils.isEmpty(refreshTokenRedis) || (!refreshTokenSrc.trim().equals(refreshTokenRedis.trim()))) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        // 生成双 token
        String refreshToken= JwtUtils.generateToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String accessToken = JwtUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse response = new TokenResponse();
        response.setRefreshToken(refreshToken);
        response.setAccessToken(accessToken);

        return ResponseResult.success(response);
    }
}
