package com.djk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

/**
 * Created by dujinkai on 2018/8/9.
 * 自定义认证
 */
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {


    /**
     * 查询用户接口
     */
    private UserDetailsService userDetailsService;

    /**
     * 密码工具类
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 用户输入的用户名
        String name = authentication.getName();

        // 用户输入的密码
        String password = authentication.getCredentials().toString();

        // 根据用户名查询用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);

        if (Objects.isNull(userDetails)) {
            throw new UsernameNotFoundException("用户不存在");
        }

        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }
        return new UsernamePasswordAuthenticationToken(name, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
