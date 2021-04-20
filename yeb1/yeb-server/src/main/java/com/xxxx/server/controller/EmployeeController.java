package com.xxxx.server.controller;


import com.xxxx.server.pojo.*;
import com.xxxx.server.query.PageQueryParams;
import com.xxxx.server.service.*;
import com.xxxx.server.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
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
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Resource
    private IEmployeeService employeeService;
    @Resource
    private INationService nationService;
    @Resource
    private IPoliticsStatusService politicsStatusService;
    @Resource
    private IDepartmentService departmentService;
    @Resource
    private IJoblevelService joblevelService;
    @Resource
    private IPositionService positionService;

    @ApiOperation(value = "加载用户的基本资料")
    @GetMapping("/")
    public RespPageBean getEmpByPage(Integer currentPage,Integer size, Employee employee, LocalDate[] beginDataScope){
        return employeeService.getEmpByPage(currentPage, size,employee,beginDataScope);
    }

    /**
     * 添加的前期准备
     * @param
     * @return
     */
    @ApiOperation(value = "查询所有的民族")
    @GetMapping("/nations")
    public List<Nation> nationList(){
        return nationService.list();
    }
    @ApiOperation(value = "查询所有的政治面貌")
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> politicsStatusList(){
        return politicsStatusService.list();
    }
    @ApiOperation(value = "查询所有的部门")
    @GetMapping("/deps")
    public List<Department> departmentList(){
        return departmentService.list();
    }
    @ApiOperation(value = "查询所有的职称")
    @GetMapping("/joblevels")
    public List<Joblevel> joblevelList(){
        return joblevelService.list();
    }
    @ApiOperation(value = "查询所有的职位")
    @GetMapping("/positions")
    public List<Position> positionList(){
        return positionService.list();
    }
    @ApiOperation(value = "查询最大的工号")
    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkId(){
        return employeeService.getMaxWorkId();
    }


    @ApiOperation(value = "添加用户的基本资料")
    @PostMapping("/")
    public RespBean addEmp( @RequestBody Employee employee){

        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "修改用户的基本资料")
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if (employeeService.updateById(employee)){

            return RespBean.success("用户信息修改成功");
        }
        return RespBean.error("用户信息修改失败") ;
    }

    @ApiOperation(value = "删除用户的基本资料")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id){
        if (employeeService.removeById(id)){
            return RespBean.success("用户信息删除成功");
        }
        return RespBean.error("用户信息删除失败");
    }


}
