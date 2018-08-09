package com.djk.user;

import lombok.Data;

/**
 * Created by dujinkai on 2018/8/9.
 * 权限实体类
 */
@Data
public class Permission {

    /**
     * 主键id
     */
    private long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限名称 对应 应用中注解的值
     */
    private String authority;
}
