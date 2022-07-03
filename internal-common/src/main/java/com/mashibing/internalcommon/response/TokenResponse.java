package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/26 18:49
 * @description
 */
@Data
public class TokenResponse {

    private String accessToken;

    private String refreshToken;

}
