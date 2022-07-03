package org.mashibing.apipassenger.interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/30 11:35
 * @description
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");

        // 解析 token
        TokenResult tokenResult = JwtUtils.checkToken(token);

        if (tokenResult == null) {
            resultString = "token invalid";
            result = false;
        } else {
            // 拼接 key
            String phone = tokenResult.getPhone();
            String identity = tokenResult.getIdentity();

            String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

            // 从 redis 中取出 token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
            // 比较我们传入的 token 和 redis 中的 token 是否相等
            if (StringUtils.isEmpty(tokenRedis) || (!token.trim().equals(tokenRedis.trim()))) {
                resultString = "token invalid";
                result = false;
            }
        }


        if (!result) {
            PrintWriter out = response.getWriter();
            out.print(JSONObject.toJSONString(ResponseResult.fail(resultString)));
        }

        return result;
    }
}
