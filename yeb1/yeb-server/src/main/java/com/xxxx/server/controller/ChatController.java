package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Resource
    private IAdminService adminService;
    @ApiOperation(value = "获取所有的操作员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmin(String keywords){
        return adminService.getAllAdmins(keywords);
    }
}
