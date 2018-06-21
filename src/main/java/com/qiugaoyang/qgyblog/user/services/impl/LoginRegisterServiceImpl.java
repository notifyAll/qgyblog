package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.domain.UserLogin;
import com.qiugaoyang.qgyblog.common.enums.ResultEnums;
import com.qiugaoyang.qgyblog.common.exception.LoginException;
import com.qiugaoyang.qgyblog.common.exception.ParamException;

import com.qiugaoyang.qgyblog.common.exception.RegisterFailException;
import com.qiugaoyang.qgyblog.common.exception.UserIsExistException;
import com.qiugaoyang.qgyblog.common.tools.IPCheck;
import com.qiugaoyang.qgyblog.common.util.MD5Util;

import com.qiugaoyang.qgyblog.common.util.StringUtil;
import com.qiugaoyang.qgyblog.common.util.TokenUtil;
import com.qiugaoyang.qgyblog.common.dao.UserLoginRepository;
import com.qiugaoyang.qgyblog.common.dao.UserRepository;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginRegisterServiceImpl implements LoginRegisterService {

    @Resource
    UserRepository userRepository;

    @Resource
    UserLoginRepository userLoginRepository;

    @Resource
    RedisTemplate<String, String> redisTemplate;

    /**
     * 用户登录
     *
     * @param userEmail
     * @param userPassword
     * @return
     */
    @Override
    public User login(String userEmail, String userPassword) {
//        参数校验
        if (userEmail == null || !StringUtil.isEmail(userEmail) || userPassword == null || userEmail.trim().equals("")) {
            throw new ParamException(ResultEnums.PARAM_IS_NULL);
        }
//        加密密码
        userPassword=MD5Util.GetMD5Code2(userPassword);
//       1 验证登录
        User user = userRepository.findByUserEmailAndAndUserPassword(userEmail,userPassword);

        if (user == null) { //登录失败
            throw new LoginException(ResultEnums.LOGIN_FAIL);
        }

        return user;
    }

    /**
     * 添加一条登录记录
     *
     * @param request 用于获取ip
     * @param user    用户数据
     */
    @Transactional
    @Override
    public UserLogin addLogin(HttpServletRequest request, User user) {
        String ip = IPCheck.getIP(request);

        UserLogin userLogin = new UserLogin();

        userLogin.setUser(userRepository.findOne(user.getUserId()));
        userLogin.setUserLoginIp(ip);
        userLogin.setUserLoginTime(new Date());

// 保存登录信息
        UserLogin userLogin1 = userLoginRepository.save(userLogin);

        return userLogin1;
    }


    /**
     * 将用户的登录信息放到redis中
     * 在每次提交修改请求是这个 token 会变化
     * key 的策略 user  USER_LOGIN_KEY_ + userId
     * token 的生成
     *
     * @param user
     * @param userLogin
     * @return
     */
    @Override
    public HashMap<String, String> getRedisUserToken(User user, UserLogin userLogin) {

        HashMap<String, String> map = new HashMap<>();

        //ken token
        String key = Params.USER_lOGIN_KEY + user.getUserId();
        map.put(Params.USER_KEY, key);
        map.put(Params.USER_TOKEN, new TokenUtil().createToken(userLogin.getUserLoginIp()));
        map.put("userId", user.getUserId().toString());
        map.put("userState", user.getUserState().toString()); //加入用户状态方便以后的操作
//        map.put("userEmail", user.getUserEmail());

//         存到redis中
        redisTemplate.opsForHash().putAll(key, map);
//        设置超时时间 1天
        redisTemplate.expire(key, Params.USER_KEY_EXPIRE, TimeUnit.DAYS);


//        返回将其保存到浏览器的cookis中
        return map;
    }


    /**
     * 校验用户是否登录
     * 先去redis中获取数据
     * 如果没有 则清理用户端cookie
     * 如果有则存到session中
     * @param userKey
     * @param userToken
     * @param request
     * @param response
     * @return
     */
    @Override
    public User checkLogin(String userKey, String userToken, HttpServletRequest request, HttpServletResponse response) {

        if (userKey == null || userKey.trim().equals("")) return null;

//        查询redis是否有数据
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(userKey);
        if (entries == null||entries.size()==0) return null;
        String redisUserToken = (String) entries.get(Params.USER_TOKEN);
        if (redisUserToken==null||redisUserToken.trim().equals("")) return null;

        if (!redisUserToken.equals(userToken)) {
//            不 清除redis上的登录信息  防止吧其他用户的删除了
//            redisTemplate.opsForHash().delete(userKey);
//            清除只清除用户端的cokiee
            clearUserCookie(request,response);
            return null;
        }
        //将用户信息放入session中
        User user = addUserInfoToSession(request.getSession(), Integer.valueOf((String)entries.get("userId")));
        return user;
    }
    /**
     * 在session中保存用户信息
     * @param session
     * @param userId
     */
    private User addUserInfoToSession(HttpSession session, Integer userId) {
        User user = (User) session.getAttribute(Params.USER_SESSION_LOGIN_KEY);
        if (user == null) {
            user = userRepository.findOne(userId);
            user.setUserPassword(null);
            session.setAttribute(Params.USER_SESSION_LOGIN_KEY, user);
        }
        return user;
    }

    /**
     * 清理用户端含有登录信息的cookie
     * @param response
     */
    private void  clearUserCookie(HttpServletRequest request,HttpServletResponse response){
//        Cookie cookie1 = new Cookie(Params.USER_KEY, null);
//        Cookie cookie2 = new Cookie(Params.USER_TOKEN,null);
//
//
//        response.addCookie(cookie1);
//        response.addCookie(cookie2);
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie=cookies[i];
            if (cookie.getName().equals(Params.USER_KEY)||cookie.getName().equals(Params.USER_TOKEN)){
                Cookie cookie1=new Cookie(cookie.getName(),null);
                cookie1.setMaxAge(0);
//                重要
                cookie1.setPath("/qgyblog/user");
                response.addCookie(cookie1);
            }
        }

    }
    /**
     * 注册
     *
     * @param user
     */
    @Transactional
    @Override
    public User register(User user) {
//1校验重要数据是否为空
        if (user.getUserName() == null ||
                user.getUserName().trim().equals("") ||
                user.getUserEmail() == null ||
                user.getUserEmail().trim().trim().equals("") ||
                user.getUserPassword().trim().equals("") ||
                user.getUserPassword() == null
                ) throw new ParamException(ResultEnums.PARAM_IS_NULL);

//2校验用户是否存在 判断邮箱和用户名
        List<User> userList = userRepository.findByUserEmailOrAndUserName(user.getUserEmail(), user.getUserName());
        if (userList.size() > 0) throw new UserIsExistException(ResultEnums.USER_IS_EXIST);

//3设置数据 密码 md5加密 状态为未激活 注册日期（今后可以设个定时器 几天內没激活就删除这个用户）
        user.setUserPassword(MD5Util.GetMD5Code2(user.getUserPassword()));
        user.setUserState(1); //TODO 此处的用户状态测试用 需为0
        user.setUserCreateTime(new Date());

//4写入数据库
        User save = userRepository.save(user);
        if (save == null) throw new RegisterFailException(ResultEnums.REGISTER_FAIL);

        return save;
    }



    //查询单个用户

    public User getByUserId(Integer userId){
        User one = userRepository.findOne(userId);
        return one;
    }

    /**
     * 退出登录
     * @param userKey
     * @param userToken
     * @param request
     * @param response
     * @return
     */
    @Override
    public boolean exitLogin(String userKey, String userToken, HttpServletRequest request, HttpServletResponse response) {
        if (userKey == null || userKey.trim().equals("")) return false;

//        查询redis是否有数据
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(userKey);
        if (entries == null) return false;
        String redisUserToken = (String) entries.get(Params.USER_TOKEN);
        if (redisUserToken.equals(userToken)) {
            // 清除cookie
            clearUserCookie(request,response);
            //清除session
            request.getSession().removeAttribute(Params.USER_SESSION_LOGIN_KEY);
            //删除redis 用户key
            redisTemplate.delete(userKey);
        }
        return true;
    }

    @Override
    public User updUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 查询首页人气博主
     * 通过博客的收藏数量 当收藏数相同时 博客数少的在上面
     *
     * @return
     */
    @Override
    public List<User> findRenQiUser() {

        List<User> renQiUser = userRepository.findRenQiUser();

        return renQiUser;
    }


}
