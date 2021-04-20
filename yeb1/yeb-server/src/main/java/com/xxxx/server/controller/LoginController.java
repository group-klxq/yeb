package com.xxxx.server.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.LoginParam;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.utils.VerificationCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆界面
 */
@RestController
@Api(value = "登录")
public class LoginController {

    @Resource
    private IAdminService adminService;

    @ApiOperation(value = "登录接口")
    @PostMapping("/login")
    public RespBean login(@RequestBody LoginParam loginParam,HttpServletRequest request) {

        return adminService.login(loginParam.getUsername(), loginParam.getPassword(),loginParam.getCode(),request);
    }


    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public RespBean logout() {
        return RespBean.success("退出成功");
    }


    @ApiOperation(value = "根据用户名查询对象")
    @PostMapping("/quryAdminByName")
    public Admin quryAdminByName(String username) {
        return adminService.quryAdminByName(username);
    }


    /**
     * 校验验证码
     */
    @ApiOperation(value = "验证码校验")
    @GetMapping(value = "/captcha",produces = "image/jpg")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("captcha", text);
        ServletOutputStream outputStream = resp.getOutputStream();
        VerificationCode.output(image,outputStream);
        outputStream.flush();
    }



}

