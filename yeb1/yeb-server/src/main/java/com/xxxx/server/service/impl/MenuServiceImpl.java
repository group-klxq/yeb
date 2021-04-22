package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RedisTemplate redisTemplate;

    //根据登录用户名查询拥有的权限
//    @Override
//    public List<Menu> getMenuByAdminName() {
//        //从SecurityContextHolder上下文环境中拿到当前登录的对象信息（ID）
//        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Integer id = admin.getId();
//
//        //调用dao层查询拥有的权限
//        List<Menu> menuList = menuMapper.getMenuByAdminId(id);
//        return menuList;
//    }
    @Override
    public List<Menu> getMenusByAdminId() {
        //从SecurityContextHolder上下文环境中拿到当前登录的对象信息（ID）
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String,Object> valueOperations=redisTemplate.opsForValue();
        //从redis获取菜单数据
        List<Menu> menus =(List<Menu>)valueOperations.get("menu_" + adminId);
        //如果为空，从数据库获取
        if (CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(adminId);
            //将数据设置到redis中
            valueOperations.set("menu_"+adminId,menus);
        }
        //调用dao层查询拥有的权限
        return menus;
    }

    //根据当前资源，查询可使用他的角色
    @Override
    public List<Menu> getRoleByMenuId() {
        return menuMapper.getRoleByMenuId();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }



}
