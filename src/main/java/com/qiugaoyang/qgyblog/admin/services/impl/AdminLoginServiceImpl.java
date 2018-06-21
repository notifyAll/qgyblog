package com.qiugaoyang.qgyblog.admin.services.impl;

import com.qiugaoyang.qgyblog.admin.services.AdminLoginService;
import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.dao.AdminLoginRepository;
import com.qiugaoyang.qgyblog.common.dao.AdminRepository;
import com.qiugaoyang.qgyblog.common.domain.Admin;
import com.qiugaoyang.qgyblog.common.domain.AdminLogin;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.enums.ResultEnums;
import com.qiugaoyang.qgyblog.common.exception.LoginException;
import com.qiugaoyang.qgyblog.common.exception.ParamException;
import com.qiugaoyang.qgyblog.common.tools.IPCheck;
import com.qiugaoyang.qgyblog.common.util.MD5Util;
import com.qiugaoyang.qgyblog.common.util.TokenUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Resource
    private AdminLoginRepository adminLoginRepository;
    @Resource
    private AdminRepository adminRepository;

    @Resource
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 管理员登录
     *
     * @param adminEmail    账户
     * @param adminPassword 密码
     * @return
     */
    @Override
    public Admin login(String adminEmail, String adminPassword) {
        if (adminEmail == null || adminEmail.trim().equals("") || adminPassword == null || adminPassword.trim().equals("")) {
            throw new ParamException(ResultEnums.PARAM_IS_NULL);
        }
//        密码md5加密
        adminPassword = MD5Util.GetMD5Code2(adminPassword);

//        登录校验
        Admin admin = adminRepository.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);

        if (admin == null) {
            throw new LoginException(ResultEnums.LOGIN_FAIL);
        }

        return admin;
    }

    /**
     * 添加管理员登录记录
     *
     * @param request 用于获取ip
     * @param admin   管理员信息
     * @return
     */
    @Override
    public AdminLogin addLogin(HttpServletRequest request, Admin admin) {
        // 获取ip
        String ip = IPCheck.getIP(request);

        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setAdminId(admin.getAdminId());
        adminLogin.setAdminLoginIp(ip);
        adminLogin.setAdminLoginTime(new Date());
// 保存登录信息
        AdminLogin save = adminLoginRepository.save(adminLogin);
        return save;
    }

    /**
     * 将登录信息保存到redis中
     *
     * @param admin
     * @param adminLogin
     * @return
     */
    @Override
    public HashMap<String, String> getRedisAdminToken(Admin admin, AdminLogin adminLogin) {

        HashMap<String, String> map = new HashMap<>();

        //ken token
        String key = Params.ADMIN_lOGIN_KEY + admin.getAdminId();
        map.put(Params.ADMIN_KEY, key);
        map.put(Params.ADMIN_TOKEN, new TokenUtil().createToken(adminLogin.getAdminLoginIp()));
        map.put("adminId", admin.getAdminId().toString());
        map.put("adminState", admin.getAdminState().toString()); //加入管理员状态方便以后的操作

//         存到redis中
        redisTemplate.opsForHash().putAll(key, map);
//        设置超时时间 1天
        redisTemplate.expire(key, Params.ADMIN_KEY_EXPIRE, TimeUnit.DAYS);


//        返回将其保存到浏览器的cookis中
        return map;
    }

    /**
     * 管理员退出登录
     *
     * @param adminKey   管理员key
     * @param adminToken 管理员token
     * @param request
     * @param response
     * @return
     */
    @Override
    public boolean exitLogin(String adminKey, String adminToken, HttpServletRequest request, HttpServletResponse response) {
        if (adminKey == null || adminKey.trim().equals("")) return false;

//        查询redis是否有数据
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(adminKey);
        if (entries == null) return false;
        String redisUserToken = (String) entries.get(Params.ADMIN_TOKEN);
        if (redisUserToken.equals(adminToken)) {
            // 清除cookie
            clearAdminCookie(request,response);
            //清除session
            request.getSession().removeAttribute(Params.ADMIN_SESSION_LOGIN_KEY);
            //删除redis 用户key
            redisTemplate.delete(adminKey);
        }
        return true;
    }

    @Override
    public Admin checkLogin(String adminKey, String adminToken, HttpServletRequest request, HttpServletResponse response) {
        if (adminKey == null || adminKey.trim().equals("")) return null;

//        查询redis是否有数据
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(adminKey);
        if (entries == null||entries.size()==0) return null;
        String redisAdminToken = (String) entries.get(Params.ADMIN_TOKEN);
        if (redisAdminToken==null||redisAdminToken.trim().equals("")) return null;

        if (!redisAdminToken.equals(adminToken)) {
//            不 清除redis上的登录信息  防止吧其他用户的删除了
//            redisTemplate.opsForHash().delete(userKey);
//            清除只清除用户端的cokiee
            clearAdminCookie(request,response);
            return null;
        }
        //将用户信息放入session中
        Admin admin = addUserInfoToSession(request.getSession(), Integer.valueOf((String)entries.get("adminId")));
        return admin;
    }

    /**
     * 从session中保存或者获取数据
     * @param session
     * @param adminId
     * @return
     */
    private Admin addUserInfoToSession(HttpSession session, Integer adminId) {
        Admin admin = (Admin) session.getAttribute(Params.ADMIN_SESSION_LOGIN_KEY);
        if (admin == null) {
            admin = adminRepository.findOne(adminId);
            admin.setAdminPassword(null);
            session.setAttribute(Params.ADMIN_SESSION_LOGIN_KEY, admin);
        }
        return admin;
    }

    /**
     * 清除admin登录的cookie
     * @param request
     * @param response
     */
    private void clearAdminCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie=cookies[i];
            if (cookie.getName().equals(Params.ADMIN_KEY)||cookie.getName().equals(Params.ADMIN_TOKEN)){
                Cookie cookie1=new Cookie(cookie.getName(),null);
                cookie1.setMaxAge(0);
//                重要
                cookie1.setPath("/qgyblog/admin");
                response.addCookie(cookie1);
            }
        }
    }
}
