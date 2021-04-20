package com.xxxx.server.controller;


import com.xxxx.server.pojo.*;
import com.xxxx.server.query.PageQueryParams;
import com.xxxx.server.service.*;
import com.xxxx.server.service.impl.EmployeeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/employee")
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
    @GetMapping("/basic")
    public RespPageBean getEmpByPage( Employee employee,LocalDate[] beginDataScope){
        return employeeService.getEmpByPage(employee,beginDataScope);
    }

    /**
     * 添加的前期准备
     * @param
     * @return
     */
    @ApiOperation(value = "查询所有的民族")
    @GetMapping("/batis/nations")
    public List<Nation> nationList(){
        return nationService.list();
    }
    @ApiOperation(value = "查询所有的政治面貌")
    @GetMapping("/batis/politicsstatus")
    public List<PoliticsStatus> politicsStatusList(){
        return politicsStatusService.list();
    }
    @ApiOperation(value = "查询所有的部门")
    @GetMapping("/batis/departments")
    public List<Department> departmentList(){
        return departmentService.list();
    }
    @ApiOperation(value = "查询所有的职称")
    @GetMapping("/batis/joblevels")
    public List<Joblevel> joblevelList(){
        return joblevelService.list();
    }
    @ApiOperation(value = "查询所有的职位")
    @GetMapping("/batis/positions")
    public List<Position> positionList(){
        return positionService.list();
    }
    @ApiOperation(value = "查询最大的工号")
    @GetMapping("/batis/maxWorkId")
    public RespBean getMaxWorkId(){
        return employeeService.getMaxWorkId();
    }


    @ApiOperation(value = "添加用户的基本资料")
    @PostMapping("/batis")
    public RespBean addEmp( Employee employee){

        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "修改用户的基本资料")
    @PutMapping("/batis")
    public RespBean updateEmp(Employee employee){
        if (employeeService.updateById(employee)){

            return RespBean.success("用户信息修改成功");
        }
        return RespBean.error("用户信息修改失败") ;
    }

    @ApiOperation(value = "修改用户的基本资料")
    @DeleteMapping("/batis")
    public RespBean deleteEmp(Integer id){
        if (employeeService.removeById(id)){
            return RespBean.success("用户信息删除成功");
        }
        return RespBean.error("用户信息删除失败");
    }


}
