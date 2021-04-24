package com.xxxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuRoleService;
import com.xxxx.server.service.IMenuService;
import com.xxxx.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/basic/permiss")
public class PermissionController {

    @Resource
    private IRoleService roleService;

    @Resource
    private IMenuService menuService;

    @Resource
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "查询所有的角色")
    @GetMapping("/")
    public List<Role> queryAllRole(){
        return roleService.list();
    }

    @ApiOperation(value = "添加角色")
    @PostMapping("/role")
    public RespBean insertRole(@RequestBody Role role){
        if (!role.getName().startsWith("ROLE_")){
            role.setName("Role_"+role.getName());
        }
        if (roleService.save(role)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable Integer rid){
        if (roleService.removeById(rid)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除角色失败");
    }

    @ApiOperation(value = "查询所有的菜单")
    @GetMapping("/menus")
    public List<Menu> queryAllMenu(){
        return menuService.queryAllMenu();
    }

    @ApiOperation(value = "根据角色ID查询拥有的菜单ID")
    @GetMapping("/mid/{rid}")
    public List<Integer> queryMenuByRoleId(@PathVariable Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>()
                .eq("rid",rid))
                .stream()
                .map(MenuRole::getMid)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateRoleMenus(Integer rid,Integer[] mids){
        return menuRoleService.updateRoleMenus(rid,mids);
    }
}
