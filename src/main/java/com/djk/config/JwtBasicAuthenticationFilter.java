package com.djk.config;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dujinkai on 2018/8/9.
 * 该过滤器的主要目的是验证请求带的token是否合法
 */
@Slf4j
public class JwtBasicAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.substring(7);

            String json = Jwts.parser()
                    .setSigningKey("aCVjc2hvcK9qd3Rf12VjcmV0X2tleQ==")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            List<SimpleGrantedAuthority> grantedAuthorities = JSONObject.parseArray(json.split("-")[1], SimpleGrantedAuthority.class);

            // 将解析后的认证信息放入上下文
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(json.split("-")[0], null, grantedAuthorities));
        } catch (Exception e) {
            log.error("token error....", e);
            throw new SignatureException("Invalid token.");
        }

        chain.doFilter(request, response);
    }
}
