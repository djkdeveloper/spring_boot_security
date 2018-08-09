package com.djk.config;

import com.alibaba.fastjson.JSONObject;
import com.djk.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dujinkai on 2018/8/9.
 * 该过滤的主要目的有2个
 * 1：获得用户输入的用户名和密码
 * 2：将认证成功的后的用户信息生产token返回给前端
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 认证管理器
     */
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * 组装前端用户输入的用户名和密码 然后调用AuthenticationManager 尝试登录
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // 用户前端输入的用户名和密码转化成用户对象
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            // 进行认证
            return authenticationManager.authenticate(user.convert());
        } catch (IOException e) {
            log.error("resolve params to user fail....", e);
            throw new RuntimeException("resolve params to user fail");
        }
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {


        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSONObject.toJSON(LoginResult.buildFail()));
        response.getWriter().close();
    }

    /**
     * 登录认证成功后把用户信息生成jwt信息
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName() + "-" + JSONObject.toJSON(authResult.getAuthorities()))
                .setExpiration(Date.from(Instant.now().plus(7, ChronoUnit.DAYS))) // 有效期 1个星期
                .signWith(SignatureAlgorithm.HS512, "aCVjc2hvcK9qd3Rf12VjcmV0X2tleQ==")
                .compact();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().print(JSONObject.toJSON(LoginResult.buildSucces(token)));
        response.getWriter().close();
    }
}
