package com.xxxx.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.EmployeeMapper;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.IEmployeeService;
import com.xxxx.server.utils.CheckIdCard;
import com.xxxx.server.utils.PhoneFormatCheckUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;


    @Override
    public RespPageBean getEmpByPage(Employee employee, LocalDate[] beginDataScope) {
        Page<Employee> page=new Page<>(1,10);
        IPage empByPage = employeeMapper.getEmpByPage(page, employee,beginDataScope);
        RespPageBean respPageBean=new RespPageBean(empByPage.getTotal(),empByPage.getRecords());
        return respPageBean;
    }
/**
 //     * 添加操作
 //     * @param employee
 //
 * @return*/
    @Override
    public RespBean addEmp(Employee employee) {
//        LocalDate beginContract = employee.getBeginContract();
//        LocalDate endContract = employee.getEndContract();
//        long days = beginContract.until(endContract, ChronoUnit.DAYS);
//        DecimalFormat decimalFormat = new DecimalFormat("##.00");
//        String format = decimalFormat.format(days / 365.00);
//        employee.setContractTerm(Double.parseDouble(format));

        //校验
        checkData(employee);
        if (employeeMapper.insert(employee)!=1){
            return RespBean.error("员工信息添加失败");
        }
        return RespBean.success("员工信息添加成功");
    }

    @Override
    public RespBean getMaxWorkId() {
        QueryWrapper<Employee> wrapper=new QueryWrapper<>();
        wrapper.select("MAX(workID)");
        List<Map<String, Object>> maps = employeeMapper.selectMaps(wrapper);
        String s = String.valueOf(Integer.parseInt(maps.get(0).get("MAX(workID)").toString()) + 1);
        return RespBean.success(null,s);
    }

    private void checkData(Employee employee) {
        //手机号非空并且符合规范

        if (StringUtils.isEmpty(employee.getPhone())|| !PhoneFormatCheckUtils.isPhoneLegal(employee.getPhone())){
           RespBean.error("手机号不能为空或者手机号不符合规范");
        }
        //身份证非空并且符合规范
        if (StringUtils.isEmpty(employee.getIdCard()) ||!CheckIdCard.check(employee.getIdCard())){
            RespBean.error("身份证号不能为空或者不符合规范");
        }
        //出生日期非空
        if (employee.getBirthday()==null){
            RespBean.error("出生日期不能为空");
        }
        //name非空且存在
        if (StringUtils.isEmpty(employee.getName())){
            RespBean.error("员工姓名不能为空");
        }
        //地址不能为空
        if (StringUtils.isEmpty(employee.getNativePlace())){
            RespBean.error("籍贯不能为空");
        }


    }



//
//
}
