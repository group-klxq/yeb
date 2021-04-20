package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.SalaryMapper;
import com.xxxx.server.pojo.Salary;
import com.xxxx.server.service.ISalaryService;
import com.xxxx.server.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

    @Resource
    private SalaryMapper salaryMapper;


    //根据部门名查询
    @Override
    public List<Salary> selectByName(Salary salary) {
        QueryWrapper<Salary> wrapper = new QueryWrapper<>();
        //开启分页，一页有2个
        Page<Salary> page = new Page<>(1, 2);
        salaryMapper.selectPage(page, null);

        return salaryMapper.selectList(wrapper.like("name", salary.getName()));

    }

    //添加工资账套
    @Override
    public void insert(Salary salary) {
        System.out.println(salary.getName());
        //数据校验
        checkSalary(salary);
        //避免重复
        QueryWrapper<Salary> wrapper = new QueryWrapper<>();
        Salary salary1 = salaryMapper.selectOne(wrapper.eq("name", salary.getName()));
        AssertUtil.isTrue(salary1 != null, "部门名已存在");

        //判断是否成功
        int insert = salaryMapper.insert(salary);
        System.out.println(insert);
        System.out.println(salary);
        AssertUtil.isTrue(insert != 1, "添加失败");
    }

    //修改工资账套
    @Override
    public void updateSalary(Salary salary) {
        //数据校验
        AssertUtil.isTrue(salary.getId() == null, "数据异常");
        checkSalary(salary);

        //校验不能改的重名了
        //通过新部门名查ID
        Integer id = salaryMapper.selectIdByName(salary.getName());
        AssertUtil.isTrue(id != null && id != salary.getId(), "部门名已存在，请更换名称");

        //执行修改操作
        AssertUtil.isTrue(salaryMapper.update(salary, null) != 1, "编辑失败");

    }

    //删除账套
    @Override
    public void deleteSalary(Salary salary) {
        AssertUtil.isTrue(salaryMapper.selectById(salary.getId())==null,"待删除项不存在");
        AssertUtil.isTrue(salary.getId()==null,"待删除项异常，请重试");
        AssertUtil.isTrue(salaryMapper.deleteById(salary.getId())!=1,"删除失败");
    }


    //非空判断
//    public void checkSalary(Salary salary){
//        AssertUtil.isTrue(salary.getName()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getBasicSalary()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getBonus()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getLunchSalary()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getTrafficSalary()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getPensionBase()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getMedicalBase()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getMedicalPer()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getAccumulationFundBase()==null,"该项不能为空");
//        AssertUtil.isTrue(salary.getAccumulationFundPer()==null,"该项不能为空");
//    }
    public void checkSalary(Salary salary) {
        AssertUtil.isTrue(salary.getName() == null ||
                salary.getBasicSalary() == null ||
                salary.getBonus() == null ||
                salary.getLunchSalary() == null ||
                salary.getTrafficSalary() == null ||
                salary.getPensionBase() == null ||
                salary.getMedicalBase() == null ||
                salary.getMedicalPer() == null ||
                salary.getAccumulationFundBase() == null ||
                salary.getAccumulationFundPer() == null, "必填项不能为空");
    }
}

