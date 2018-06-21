package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.domain.UserLogin;
import com.qiugaoyang.qgyblog.common.enums.ResultEnums;
import com.qiugaoyang.qgyblog.common.exception.BaseException;

import com.qiugaoyang.qgyblog.common.resultbean.Result;
import com.qiugaoyang.qgyblog.common.util.ImgUtil;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

//登录注册
@Controller()
@RequestMapping("/user")
public class LoginRegisterController {

    @Resource
    LoginRegisterService loginRegisterService;

    /**
     * 1 验证登录
     * 2 添加一条登录记录
     * 2 创建key tonken 写入cookie 中 response.addCookie();
     * 3 将key tonken 放入redis中
     * 4 将用户的信息  除密码外的 返回  将key token放入cokie 方便下次访问首页直接登录
     *
     * @param userEmail
     * @param userPassword
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("userEmail") String userEmail,
                        @RequestParam("userPassword") String userPassword,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes
    ) {
        try {
//            1 验证登录
            User user = loginRegisterService.login(userEmail, userPassword);
//            2 添加一条登录记录
            UserLogin userLogin = loginRegisterService.addLogin(request, user);

//            3 将key tonken 放入redis中
            HashMap<String, String> redisUserToken = loginRegisterService.getRedisUserToken(user, userLogin);

//            2 创建key tonken 写入cookie 中 response.addCookie();
            Cookie userKey = new Cookie(Params.USER_KEY, redisUserToken.get(Params.USER_KEY));
            Cookie userToken = new Cookie(Params.USER_TOKEN, redisUserToken.get(Params.USER_TOKEN));
            userKey.setMaxAge(60 * 60 * 24); // 一天过期
            userToken.setMaxAge(60 * 60 * 24);
            response.addCookie(userKey);
            response.addCookie(userToken);

//            4 将用户的信息返回  放到session中
            user.setUserPassword(null);
            request.getSession().setAttribute(Params.USER_SESSION_LOGIN_KEY, user);

            redirectAttributes.addFlashAttribute("message", "登录成功");
//            不管成功还是失败返回首页

        } catch (BaseException e) {
            redirectAttributes.addFlashAttribute("message", "登录失败 用户名或密码错误" + e.getCode() + e.getMessage());
        } finally {
            return "redirect:/user/home";
        }
    }

    @PostMapping("/register")
    public String register(@Valid User user,
                           RedirectAttributes redirectAttributes

    ) {
//        ModelAndView modelAndView = new ModelAndView();
        try {
            //        1 写入数据库
            User register = loginRegisterService.register(user);
//        2 发送邮箱激活信息  TODO

//        3去首页 返回个注册成功的弹出框
//            modelAndView.setViewName("user/index");
//            modelAndView.addObject("message", "注册成功 请登录");
            redirectAttributes.addFlashAttribute("message", "注册成功请去邮箱激活");

        } catch (BaseException e) {
            redirectAttributes.addFlashAttribute("message", "注册失败" + e.getCode() + e.getMessage());

//            modelAndView.addObject("message", "注册失败" + e.getCode());
            //            return modelAndView;
        } finally {

            return "redirect:/user/home";
        }
    }

    /**
     * 退出登录
     *
     * @param userKey
     * @param userToken
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/login/exit")
    public String exitLogin(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        //       退出登录
        boolean a = loginRegisterService.exitLogin(userKey, userToken, request, response);

        if (a == true) {
            redirectAttributes.addFlashAttribute("message", "成功退出登录");
        }

//        去首页
        return "redirect:/user/home";
    }


}
