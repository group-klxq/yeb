package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.mapper.AdminRoleMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.utils.AdminUtils;
import com.xxxx.server.utils.JwtTokenUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author shi
 * @since 2021-04-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private AdminRoleMapper adminRoleMapper;



    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {

        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return RespBean.error("验证失败123");
        }
        //加载登录对象信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UsernameNotFoundException("用户名和密码异常");
        }

        //判断当前对象是否可用
        if (!userDetails.isEnabled()) {
            return RespBean.error("用户状态异常");
        }

        //将更新之后的用户的基本信息存放在security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);



        //准备令牌
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> map = new HashMap<>();
        System.out.println(tokenHead);
        map.put("tokenHead", tokenHead);
        map.put("token", token);
        return RespBean.success("登录成功", map);
    }



    /**
     * 根据用户名查询对象
     *
     * @param
     * @return
     */
    @Override
    public Admin quryAdminByName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username));
    }

    /**
     * 根据用户信息查询对应角色
     *
     * @return
     */
    @Override
    public List<Admin> quryRoleByAdmin(Integer id) {
        // //获取当前登录用户信息
        // Admin principal = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Integer id = principal.getId();
        //查询对应的角色
        List<Admin> adminList = adminMapper.quryRoleByAdmin(id);
        return adminList;
    }

    //根据用户ID查询所有角色
    @Override
    public List<Role> quryRoles(Integer id) {
        return adminMapper.quryRoles(id);
    }


    //查询所有的操作员信息
    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(AdminUtils.getCurrentAdmin().getId(), keywords);
    }

    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        Integer count = adminRoleMapper.updateAdminrole(adminId, rids);
        if (rids.length == count) {
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
