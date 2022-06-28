package com.mashibing.servicepassengeruser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/28 16:28
 * @description
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "service passenger user";
    }

}
