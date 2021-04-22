package com.xxxx.server.controller;


import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.impl.RoleServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.impl.RoleServiceImpl;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;

    @Resource
    private RoleServiceImpl roleService;


    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/system/admin")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
        }

    @ApiOperation(value = "更新操作员")
    @PutMapping("/system/admin")
    public RespBean updateAdmin(@RequestBody Admin admin) {
            if (adminService.updateById(admin)) {
                return RespBean.success("更新成功！");
            }
            return RespBean.error("更新失败！");
        }

    /**
     只有系统管理员才能删除操作员
     */
    @ApiOperation(value = "删除操作员")
    @DeleteMapping("/system/admin/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {
        if (id == 1) {
            if (adminService.removeById(id)) {
                return RespBean.success("删除成功！");
            }
            return RespBean.error("删除失败！");
        }
        return RespBean.error("非常抱歉，您没有删除权限");
    }

    /**
     * 1.查询所有的角色
     * 2.查询操作员所携带的角色
     * 3.更新操作员的角色
     */

    @ApiOperation(value = "获取所有角色")
    @GetMapping("/system/admin/roles")
    public List<Role>getRoles(){
    @GetMapping("/roles")
    public List<Role> getRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "更新操作员的角色")
    @PutMapping("/system/admin/role")
    public RespBean updateAdminRole(Integer adminId,Integer[] rids) {
        if ( rids != null) {
            return adminService.updateAdminRole(adminId, rids);
        }
        return RespBean.error("操作员角色不能为空");
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        return adminService.updateAdminRole(adminId, rids);
    }

    @ApiOperation(value = "获取所有操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
    @ApiOperation(value = "根据用户名查询对象")
    @GetMapping("admin/info")
    public Admin quryAdminByName(Principal principal) {
        Admin admin = adminService.quryAdminByName(principal.getName());
        admin.setPassword(null);
        List<Role> roles = adminService.quryRoles(admin.getId());
        admin.setRoles(roles);
        return admin;
    }
}

