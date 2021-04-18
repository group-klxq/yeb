package com.xxxx.server.config;

import com.xxxx.server.config.compoent.JwtAuthencationTokenFilter;
import com.xxxx.server.controller.LoginController;
import com.xxxx.server.pojo.Admin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private LoginController loginController;
    @Resource
    private AccessDeniedHandler restfulAccessDeniedHandler;
    @Resource
    private AuthenticationEntryPoint restAuthorizationEntryPoint;

    /**
     * 配置放行资源的方式
     *      直接放行不会经过filter
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/ws/**"
        );
    }

    /**
     * 自定义登录页面
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //关闭session 不使用
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //认证
                .authorizeRequests()
                // .antMatchers("/login","/logout").permitAll()
                //权限
                .anyRequest().authenticated()
                //禁用缓存
                .and()
                .headers()
                .cacheControl();

        //配置JWT登录授权的token拦截器
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                //权限不足 403
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //未登录或者token过期，登录异常
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    /**
     * 自定义登录逻辑
     * @return
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = loginController.quryAdminByName(username);
            if(admin == null){
                throw new UsernameNotFoundException("用户名或密码不正确");
            }
            return admin;
        };
    }

    @Bean
    public PasswordEncoder createPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
        return new JwtAuthencationTokenFilter();
    }
}
