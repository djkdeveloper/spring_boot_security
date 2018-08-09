package com.djk.user;

import java.util.List;

/**
 * Created by dujinkai on 2018/8/9.
 * 用户服务接口
 */
public interface UserService {

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 成功返回1 失败返回0
     */
    int addUser(User user);

    /**
     * 根据用户名称查询用户信息
     *
     * @param username 用户名称
     * @return 返回用户信息
     */
    User queryByName(String username);

    /**
     * 查询所有用户信息
     *
     * @return 返沪所有用户信息
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
     * @param userId 用户id
     * @return 返回用户的权限信息
     */
    List<Permission> queryPermissions(long userId);
}
