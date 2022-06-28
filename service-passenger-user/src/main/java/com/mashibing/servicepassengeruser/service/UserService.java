package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/28 17:04
 * @description
 */
@Service
public class UserService {

    public ResponseResult loginOrRegister(String passengerPhone){
        System.out.println("UserService" + passengerPhone);

        // 根据手机号查询业务信息

        // 判断用户信息是否存在

        // 如果不存在 插入用户信息

        return ResponseResult.success();
    }


}
