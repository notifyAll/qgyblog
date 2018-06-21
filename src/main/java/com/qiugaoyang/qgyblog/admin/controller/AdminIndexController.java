package com.qiugaoyang.qgyblog.admin.controller;

import com.qiugaoyang.qgyblog.admin.services.AdminLoginService;
import com.qiugaoyang.qgyblog.common.domain.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {
    @Resource
    AdminLoginService adminLoginService;

    @GetMapping("/home")
    public ModelAndView getHome(
            @CookieValue(value = "adminKey", required = false) String adminKey,
            @CookieValue(value = "adminToken", required = false) String adminToken,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            @ModelAttribute(value = "message") String message
    ){
        ModelAndView modelAndView = new ModelAndView();

//     1   校验是否已经登录 获取用户数据
        Admin admin = adminLoginService.checkLogin(adminKey, adminToken, request, response);
//        modelAndView.addObject(Params.Admin_SESSION_LOGIN_KEY, Admin);

//    获取首页数据 7

//     3   重定向的参数处理
//        message="ceshi1"; 此处貌似不需要接收 这个message  页面都可以获取到message
        if (message != null && !message.trim().equals("")) {
            modelAndView.addObject("message", message);
        }

//     4   填充数据返回首页
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }
}
