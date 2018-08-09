package com.djk.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by dujinkai on 2018/8/9.
 * 用户服务接口实现
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     * 注入用户数据库接口
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 注入加密工具类
     */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public int addUser(User user) {
        if (Objects.isNull(user)) {
            log.error("addUser fail due to user user is empty..");
            return 0;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // 插入用户信息
        userMapper.addUser(user);

        // 插入用户和角色的关联信息
        userMapper.addUserAndRole(user.getUserAndRoleMap());
        return 1;
    }

    @Override
    public User queryByName(String username) {
        return userMapper.queryByName(username);
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.queryAllUsers();
    }

    @Override
    public int updateName(User user) {
        return userMapper.updateName(user);
    }

    @Override
    public List<Permission> queryPermissions(long userId) {
        return userMapper.queryPermissions(userId);
    }
}
