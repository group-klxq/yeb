package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.query.PageQueryParams;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
public interface IEmployeeService extends IService<Employee> {


    RespPageBean getEmpByPage(Employee employee, LocalDate[] beginDataScope);
}
