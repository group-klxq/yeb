package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import org.springframework.stereotype.Service;

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
     * @param username
     * @param password
     * @return
     */
    RespBean login(String username, String password);

    /**
     * 通过用户名返回查询对象
     * @return
     */
    Admin quryAdminByName(String name);
}
