package com.xxxx.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Salary;
import com.xxxx.server.service.ISalaryService;
import com.xxxx.server.service.impl.SalaryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@RestController
@RequestMapping("/salary/sob")
@Api(value = "工资账套")
public class SalaryController {

    @Resource
    private ISalaryService iSalaryService;

    @ApiOperation(value = "所有人员的工资账套")
    @GetMapping("/")
    public List<Salary> sal(){
        return iSalaryService.list();
    }

//    @ApiOperation(value = "根据部门名查询")
//    @GetMapping("/select")
//    public List<Salary> selectByName(Salary salary){
//        return iSalaryService.selectByName(salary);
//    }


//    @ApiOperation(value = "添加或者修改")
//    @PostMapping("/addOrUpdate")
//    public RespBean addOrUpdate(Salary salary){
//        if (salary.getId()==null){
//            //添加
//            iSalaryService.insert(salary);
//            return RespBean.success("添加成功");
//        }else {
//            iSalaryService.updateSalary(salary);
//            return RespBean.success("修改成功");
//        }
//    }

    @ApiOperation(value = "添加账套")
    @PostMapping("/")
    public RespBean insert(@RequestBody Salary salary){
        iSalaryService.insert(salary);
        return RespBean.success("添加成功");
    }

    @ApiOperation(value = "修改账套")
    @PutMapping("/")
    public RespBean update(@RequestBody Salary salary){
        iSalaryService.updateSalary(salary);
        return RespBean.success("修改成功");
    }

    @ApiOperation(value = "删除账套")
    @DeleteMapping("/{id}")
    public RespBean delete(@PathVariable Integer id){
        iSalaryService.deleteSalary(id);
        return RespBean.success("删除成功");
    }
}
