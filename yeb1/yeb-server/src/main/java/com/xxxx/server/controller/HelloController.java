package com.xxxx.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测试")
public class HelloController {

    @ApiOperation("测试接口")
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }
}
