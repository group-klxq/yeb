package com.xxxx.server.controller;


import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @ApiOperation(value = "根据用户名查询对象")
    @GetMapping("/info")
    public Admin quryAdminByName(Principal principal) {
        Admin admin = adminService.quryAdminByName(principal.getName());
        admin.setPassword(null);
        List<Role> roles = adminService.quryRoles(admin.getId());
        admin.setRoles(roles);
        return admin;
    }
}
