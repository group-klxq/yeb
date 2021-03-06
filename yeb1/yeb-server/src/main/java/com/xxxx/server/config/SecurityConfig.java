package com.xxxx.server.config;

import com.xxxx.server.config.compoent.CustomFilter;
import com.xxxx.server.config.compoent.CustomUrlDecisionManager;
import com.xxxx.server.config.compoent.JwtAuthencationTokenFilter;
import com.xxxx.server.controller.LoginController;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
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
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private LoginController loginController;
    @Resource
    private AccessDeniedHandler restfulAccessDeniedHandler;
    @Resource
    private AuthenticationEntryPoint restAuthorizationEntryPoint;
    @Resource
    private IAdminService adminService;
    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    /**
     * ???????????????????????????
     *      ????????????????????????filter
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
     * ?????????????????????
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //??????JWT????????????csrf
        http.csrf()
                .disable()
                //??????token????????????session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //??????
                .authorizeRequests()
                // .antMatchers("/login","/logout").permitAll()
                //??????
                .anyRequest().authenticated()
                //??????????????????
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
                .and()
                //????????????
                .headers()
                .cacheControl();

        //??????JWT???????????????token?????????
        //??????jwt ?????????????????????
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //????????????????????????????????????????????????
        http.exceptionHandling()
                //403
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //403
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    /**
     * ?????????????????????
     * @return
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = loginController.quryAdminByName(username);
            if(admin == null){
                throw new UsernameNotFoundException("???????????????????????????");
            }
            //????????????????????????
            List<Role> roles = adminService.quryRoles(admin.getId());
            admin.setRoles(roles);
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
