package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Salary;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
public interface ISalaryService extends IService<Salary> {
    //添加工资账套
    void insert(Salary salary);

    //根据部门名查询
    List<Salary> selectByName(Salary salary);

    //修改工资账套
    void updateSalary(Salary salary);

    //删除工资账套
    void deleteSalary(Salary salary);
}
