package com.djk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by dujinkai on 2018/8/9.
 * 安全配置文件
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入加密工具类
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 注入查询用户接口
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 设置http的安全认证规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()   // 前后端分离 不需要csrf
                .exceptionHandling().authenticationEntryPoint(new NoLoginAuthenticationEntryPoint()).and() // 设置没有登录认证时候的错误提醒为401
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  // 使用jwt  不需要session
                .authorizeRequests().antMatchers("/adduser").permitAll().anyRequest().authenticated().and()   // 除了adduser这个接口不需要认证 其他接口全部需要认证
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))             // 重写 UsernamePasswordAuthenticationFilter
                .addFilter(new JwtBasicAuthenticationFilter(authenticationManager()));    // 重写BasicAuthenticationFilter

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义认证
        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService, bCryptPasswordEncoder));
    }
}
