package com.djk.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by dujinkai on 2018/8/9.
 * 查询用户接口实现
 */
@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    /**
     * 注入用户服务接口
     */
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 查询数据库中用户的信息
        User user = userService.queryByName(s);

        if (Objects.isNull(user)) {
            return null;
        }

        // 用户的权限
        List<Permission> permissions = userService.queryPermissions(user.getId());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (!CollectionUtils.isEmpty(permissions)) {
            grantedAuthorities = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission.getAuthority())).collect(Collectors.toList());
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);

    }
}
