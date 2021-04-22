package com.xxxx.server.controller;


import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.xxxx.server.pojo.*;
import com.xxxx.server.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 前端控制器
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
    public RespPageBean getEmpByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDataScope) {
        return employeeService.getEmpByPage(currentPage, size, employee, beginDataScope);
    }

    /**
     * 添加的前期准备
     *
     * @param
     * @return
     */
    @ApiOperation(value = "查询所有的民族")
    @GetMapping("/nations")
    public List<Nation> nationList() {
        return nationService.list();
    }

    @ApiOperation(value = "查询所有的政治面貌")
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> politicsStatusList() {
        return politicsStatusService.list();
    }

    @ApiOperation(value = "查询所有的部门")
    @GetMapping("/deps")
    public List<Department> departmentList() {
//        List<Department> list = departmentService.list();
        return departmentService.selectAllDepartment();
    }

    @ApiOperation(value = "查询所有的职称")
    @GetMapping("/joblevels")
    public List<Joblevel> joblevelList() {
        return joblevelService.list();
    }

    @ApiOperation(value = "查询所有的职位")
    @GetMapping("/positions")
    public List<Position> positionList() {
        return positionService.list();
    }

    @ApiOperation(value = "查询最大的工号")
    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkId() {
        return employeeService.getMaxWorkId();
    }


    @ApiOperation(value = "添加用户的基本资料")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {

        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "修改用户的基本资料")
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {

            return RespBean.success("用户信息修改成功");
        }
        return RespBean.error("用户信息修改失败");
    }

    @ApiOperation(value = "删除用户的基本资料")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {
        if (employeeService.removeById(id)) {
            return RespBean.success("用户信息删除成功");
        }
        return RespBean.error("用户信息删除失败");
    }

    @ApiOperation(value = "导出数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportExcel(HttpServletResponse response) {
        employeeService.exportExcel(response);
    }

    @ApiOperation(value = "导入数据")
    @PostMapping("/import")
    public RespBean importExcel(MultipartFile file) {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(1);
        List<Nation> nationList = nationService.list();
        nationList.forEach(System.out::println);
        List<Position> positionList = positionService.list();
        positionList.forEach(System.out::println);
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();

        List<Department> departmentList = departmentService.selectAllDepartment();
        List<Joblevel> joblevelList = joblevelService.list();
        //将excel文件中的数据放入List中
        try {
            List<Employee> employeeList = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, importParams);
            employeeList.forEach(employee -> {
                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setPosId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setJobLevelId(joblevelList.get(joblevelList.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
            });
            System.out.println("--------------------------------");
            employeeList.forEach(System.out::println);

//            boolean b = employeeService.saveBatch(employeeList,1000);
//            System.out.println(b);
//            if ( ){
//                return RespBean.success("导入成功");
//            }
            if (employeeService.saveBatch(employeeList)) {
                return RespBean.success("导入成功");
            }

        }  catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败");
    }

//

    }