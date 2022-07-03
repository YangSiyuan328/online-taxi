package org.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.TokenRequest;
import org.mashibing.apipassenger.service.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/7/3 15:24
 * @description
 */
@RestController
public class TokenController {

    @Resource
    private TokenService tokenService;

    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenRequest request) {
        String refreshToken = request.getRefreshToken();
        System.out.println("原来的 refreshToken ：" + refreshToken);
        return tokenService.refreshToken(refreshToken);
    }
}
