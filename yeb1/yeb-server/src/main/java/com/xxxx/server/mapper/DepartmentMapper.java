package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Department;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    //查询所有的部门
    List<Department> queryAllDepartment(Integer parentId);

    //添加部门
    void insertDepartment(Department department);

    List<Map<String,Object>> queryDepartmentByParentId(Integer id);
    //根据部门删除部门
    Integer deleteDepartment(Integer id);
    //根据部门名查询部门信息
    List<Department> queryDepartmentByName(String name);

    List<Department> selectAllDepartment();
}
