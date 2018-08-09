package com.djk.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dujinkai on 2018/8/9.
 * 用户控制器
 */
@RestController
public class UserController {

    /**
     * 注入用户服务接口
     */
    @Autowired
    private UserService userService;

    /**
     * 查询当前用户信息（此接口不需要权限，但是要登录）
     *
     * @return 返回当前用户信息
     */
    @GetMapping("/current")
    public Authentication currentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 查询所有会有信息
     *
     * @return 返回所有会有信息
     */
    @GetMapping("/allusers")
    @PreAuthorize("hasAuthority('allusers')")
    public List<User> allUsers() {
        return userService.queryAllUsers();
    }

    /**
     * 更新用户信息
     *
     * @return 成功返回1 失败返回0
     */
    @GetMapping("/updatename")
    @PreAuthorize("hasAuthority('updatename')")
    public String updateName() {
        return "成功";
    }

    /**
     * 新增用户信息（此接口不需要登录 更不需要权限）
     *
     * @return 成功返回1 失败返回0
     */
    @PostMapping("/adduser")
    public int addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
