package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @author 杨思远
 * @version 1.0
 * @title token 的请求类 接收 refreshToken
 * @date 2022/7/3 15:28
 * @description
 */
@Data
public class TokenRequest {

    private String accessToken;

    private String refreshToken;
}
