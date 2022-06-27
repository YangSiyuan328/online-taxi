package com.mashibing.internalcommon.constant;

import lombok.Data;
import lombok.Getter;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/25 23:41
 * @description
 */
@Getter
public enum CommonStatusEnum {

    /**
     * 验证码错误提示
     */
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),
    /**
     * 成功
     */
    SUCCESS(1, "success"),

    /**
     * 失败
     */
    FAIL(0, "fail");

    private int code;

    private String value;

    CommonStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
