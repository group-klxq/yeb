package com.xxxx.server.controller;


import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/system/basic")
public class DepartmentController {
    @Resource
    private IDepartmentService departmentService;

    /**
     * 查询所有部门
     *
     * @return
     */
    @GetMapping("/department")
    @ApiOperation(value = "查询所有的部门")
    public List<Department> queryAllDepartment() {
        List<Department> departments = departmentService.queryAllDepartment();
        departments.forEach(System.out::println);
        return departments;
    }


    /**
     * 1.参数校验
     * 模块名-module_name
     * 非空 同一层级下模块名唯一
     * url
     * 二级菜单 非空 不可重复
     * 上级菜单-parent_id
     * 一级菜单 null
     * 二级|三级菜单 parent_id 非空 必须存在
     * 层级-grade
     */
    @ApiOperation(value = "添加部门")
    @PostMapping("/department")
    public RespBean insertDepartment(@RequestBody Department department){
        return departmentService.insertDepartment(department);
    }


    @ApiOperation(value = "删除部门")
    @DeleteMapping("/department/{id}")
    public RespBean deleteDepartment(Integer id){
        return departmentService.deleteDepartment(id);
    }
}
