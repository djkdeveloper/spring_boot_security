package com.djk.config;

import lombok.Data;

/**
 * Created by dujinkai on 2018/8/9.
 * 登录结果实体
 */
@Data
public class LoginResult {

    /**
     * 返回吗  1 成功  -1 失败
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * token
     */
    private String token;

    /**
     * 构造登录失败结果
     *
     * @return 返回登录失败结果
     */
    public static LoginResult buildFail() {
        LoginResult loginResult = new LoginResult();
        loginResult.code = -1;
        loginResult.message = "用户名或密码错误";
        return loginResult;
    }

    /**
     * 构造成功信息
     *
     * @param token token
     * @return 返回成功信息
     */
    public static LoginResult buildSucces(String token) {
        LoginResult loginResult = new LoginResult();
        loginResult.code = 1;
        loginResult.message = "登录成功";
        loginResult.token = token;
        return loginResult;
    }

}
