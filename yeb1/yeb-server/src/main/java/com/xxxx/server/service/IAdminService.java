package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@Service
public interface IAdminService extends IService<Admin> {


    /**
     * 登录
     *
     * @param code
     * @param username
     * @param password
     * @param
     * @param request
     * @return
     */
    RespBean login(String code, String username, String password, HttpServletRequest request);

    /**
     * 通过用户名返回查询对象
     * @return
     */
    Admin quryAdminByName(String username);

    /**
     * 通过用户名找到对应的角色
     * @return
     */
    List<Admin> quryRoleByAdmin(Integer id);


    /**
     * 通过用户信息找到对应的角色
     * @return
     */
    List<Role> quryRoles(Integer id);


    /**
     * 获取所有操作员
     * @return
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新操作员角色
     * @return
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);

}
