package com.xxxx.server.controller;


import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.query.PageQueryParams;
import com.xxxx.server.service.IEmployeeService;
import com.xxxx.server.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@Api(value = "员工的基本资料")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private IEmployeeService employeeService;

    @ApiOperation(value = "加载用户的基本资料")
    @GetMapping("/basic")
    public RespPageBean getEmpByPage( Employee employee,LocalDate[] beginDataScope){
        return employeeService.getEmpByPage(employee,beginDataScope);
    }
//
//    @ApiOperation(value = "添加用户的基本资料")
//    @PostMapping("/batis")
//    public RespBean addEmp(Employee employee){
//        employeeService.addEmp(employee);
//        return RespBean.success("添加成功");
//    }
//
//    @ApiOperation(value = "修改用户的基本资料")
//    @PutMapping("/batis")
//    public RespBean updateEmpById(Integer id){
//        employeeService.updateEmpById(id);
//        return RespBean.success("修改成功");
//    }



}
