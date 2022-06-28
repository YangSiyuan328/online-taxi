package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/23 21:48
 * @description
 */
@Data
public class VerificationCodeDTO {

    private String passengerPhone;

    private String verificationCode;

}
