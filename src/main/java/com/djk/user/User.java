package com.djk.user;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dujinkai on 2018/8/9.
 * 用户对象
 */
@Data
public class User {

    /**
     * 用户id
     */
    private long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色id(数据库中默认写死1 和2)
     */
    private int roleId;

    /**
     * 用户的权限信息
     */
    private List<Permission> permissions;

    /**
     * 将用户对象转化成认证对象
     *
     * @return 返回认证对象
     */
    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }

    /**
     * 获得添加用户和角色关联的map
     *
     * @return 返回用户和角色关联的map
     */
    public Map<String, Object> getUserAndRoleMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", this.id);
        params.put("roleId", this.roleId);
        return params;
    }
}
