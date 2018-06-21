package com.qiugaoyang.qgyblog.admin.services;

import com.qiugaoyang.qgyblog.common.domain.Admin;
import com.qiugaoyang.qgyblog.common.domain.AdminLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public interface AdminLoginService {
    /**
     * 管理员登录
     * @param adminEmail 账户
     * @param adminPassword 密码
     * @return
     */
    Admin login(String adminEmail, String adminPassword);

    /**
     * 添加管理员登录记录
     * @param request 用于获取ip
     * @param admin 管理员信息
     * @return
     */
    AdminLogin addLogin(HttpServletRequest request, Admin admin);

    /**
     * 将登录信息保存到redis中
     * @param admin
     * @param adminLogin
     * @return
     */
    HashMap<String,String> getRedisAdminToken(Admin admin, AdminLogin adminLogin);

    /**
     * 管理员退出登录
     * @param adminKey 管理员key
     * @param adminToken 管理员token
     * @param request
     * @param response
     * @return
     */
    boolean exitLogin(String adminKey, String adminToken, HttpServletRequest request, HttpServletResponse response);

    Admin checkLogin(String adminKey, String adminToken, HttpServletRequest request, HttpServletResponse response);
}
