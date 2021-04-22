package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.pojo.Employee;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
public interface EmployeeMapper extends BaseMapper<Employee> {
    /**
     * 获取员工账套
     * @param page
     * @return
     */

    IPage getEmpByPage(Page<Employee> page, Employee employee, LocalDate[] beginDataScope);

    void insertList(List<Employee> employeeList);


    List<Employee> getEmp(Integer id);

    Integer insertEmp(List<Employee> employeeList);
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
