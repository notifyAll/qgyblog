package com.qiugaoyang.qgyblog.user.services;

import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.domain.UserLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public interface LoginRegisterService {
//       登录
    User login(String userEmail, String userPassword);
// 添加一条登录信息
    UserLogin addLogin(HttpServletRequest request, User user);
//获取redis中的登录信息
    HashMap<String, String> getRedisUserToken(User user, UserLogin userLogin);
//注册
    User register(User user);
    //校验登录
    User checkLogin(String userKey, String userToken, HttpServletRequest request, HttpServletResponse response);
    //将用户信息放入session中
//    void addUserInfoToSession(HttpSession session, Integer userId)

//    通过id查询用户
    User getByUserId(Integer userId);

    boolean exitLogin(String userKey, String userToken, HttpServletRequest request, HttpServletResponse response);

    User updUser(User user);

    /**
     * 查询首页人气博主
     * 通过博客的收藏数量 当收藏数相同时 博客数少的在上面
     * @return
     */
    List<User> findRenQiUser();
}
