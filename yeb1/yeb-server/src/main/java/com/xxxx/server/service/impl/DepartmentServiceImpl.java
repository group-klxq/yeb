package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.DepartmentMapper;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

        /**
         * 查询所有部门，实现树形结构
         * @return
         */
    @Override
    public List<Department> queryAllDepartment() {
             return departmentMapper.queryAllDepartment(-1);
        }


    /**
     * 删除部门
     * @param id
     * @return
     */
    @Override
    public RespBean deleteDepartment(Integer id) {
        if (null==id){
            return RespBean.error("数据异常");
        }
        List<Map<String, Object>> maps = departmentMapper.queryDepartmentByParentId(id);
        if (maps.size()==0){
            Integer num2 = departmentMapper.deleteDepartment(id);
            if (num2==0){
                return RespBean.error("删除失败");
            }
        }else {
            return RespBean.error("存在子菜单，不可以删除");
        }
        return RespBean.success("删除成功",id);
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @Override
    public RespBean insertDepartment(Department department) {
        if (StringUtils.isEmpty(department.getName())){
            return RespBean.error("部门名不能为空");
        }
        String name = department.getName();
        List<Department> list = departmentMapper.queryDepartmentByName(name);
        if (list.size()!=0){
            return RespBean.error("部门名重复");
        }
        department.setEnabled(true);
        departmentMapper.insert(department);
        return null;
    }

    @Override
    public List<Department> selectAllDepartment() {
        return departmentMapper.selectAllDepartment();
    }


}