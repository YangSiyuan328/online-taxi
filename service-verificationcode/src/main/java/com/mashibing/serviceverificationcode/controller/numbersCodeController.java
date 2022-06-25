package com.mashibing.serviceverificationcode.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨思远
 * @version 1.0
 * @title
 * @date 2022/6/25 0:30
 * @description
 */
@RestController
public class numbersCodeController {

    @GetMapping("/numberCode/{size}")
    public String numberCode(@PathVariable("size") int size) {

        System.out.println("size : " + size);

        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");
        JSONObject data = new JSONObject();
        data.put("numberCode", 123456);
        result.put("data", data);

        return result.toString();
    }
}
