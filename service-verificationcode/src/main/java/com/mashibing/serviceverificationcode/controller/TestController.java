package com.mashibing.serviceverificationcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/24 0:53
 * @description
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "service-verificationcode";
    }
}
