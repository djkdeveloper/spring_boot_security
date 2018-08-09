package com.djk.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by dujinkai on 2018/8/9.
 * 用户数据库接口
 */
@Mapper
public interface UserMapper {

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 成功返回1 失败返回0
     */
    int addUser(User user);

    /**
     * 插入用户和角色的关系
     *
     * @return 成功返回1 失败返回0
     */
    int addUserAndRole(Map<String, Object> params);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名称
     * @return 返回用户信息
     */
    User queryByName(String username);

    /**
     * 查询所有用户信息
     *
     * @return 返回所有用户信息
     */
    List<User> queryAllUsers();

    /**
     * 更新用户名称
     *
     * @param user 用户信息
     * @return 成功返回1 失败返回0
     */
    int updateName(User user);

    /**
     * 查询用户的权限信息
     *
     * @param id 用户id
     * @return 返回用户的权限新
     */
    List<Permission> queryPermissions(long id);
}
