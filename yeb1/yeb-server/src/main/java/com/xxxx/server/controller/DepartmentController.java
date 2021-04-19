package com.xxxx.server.controller;


import com.xxxx.server.pojo.Department;
import com.xxxx.server.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private IDepartmentService iDepartmentService;

    /**
     * 查询所有部门
     * @return
     */
    @GetMapping("queryAllDepartment")
    @ApiOperation(value = "查询所有的部门")
    public List<Department> queryAllDepartment(){
        return iDepartmentService.queryAllDepartment();
    }
}
