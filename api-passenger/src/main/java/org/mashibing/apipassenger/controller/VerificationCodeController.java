package org.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.apipassenger.request.VerificationCodeDTO;
import org.mashibing.apipassenger.service.VerificationCodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨思远
 * @version 1.0
 * @title 验证码
 * @date 2022/6/23 21:33
 * @description
 */
@RestController
public class VerificationCodeController {

    @Resource
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return verificationCodeService.generatorCode(passengerPhone);
    }

    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();

        System.out.println("手机号：" + passengerPhone + ",验证码：" + verificationCode);

        return verificationCodeService.checkCode(passengerPhone, verificationCode);
    }
}
