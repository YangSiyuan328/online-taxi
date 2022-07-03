package org.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.mashibing.apipassenger.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/7/3 17:12
 * @description
 */
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request) {

        // 从 HttpServletRequest请求 中获取 accessToken
        String accessToken = request.getHeader("Authorization");

        return userService.getUserByAccessToken(accessToken);
    }

}
