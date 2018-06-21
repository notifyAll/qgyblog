package com.qiugaoyang.qgyblog.admin.controller;

import com.qiugaoyang.qgyblog.admin.services.AdminLoginService;
import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.Admin;
import com.qiugaoyang.qgyblog.common.domain.AdminLogin;
import com.qiugaoyang.qgyblog.common.exception.BaseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

//管理员登录注册
@Controller()
@RequestMapping("/admin")
public class AdminLoginController {

    @Resource
    AdminLoginService adminLoginService;

    /**
     * 管理员等录
     * 1 验证登录
     * 2 添加一条登录记录
     * 2 创建key tonken 写入cookie 中 response.addCookie();
     * 3 将key tonken 放入redis中
     * 4 将用户的信息  除密码外的 返回  将key token放入cokie 方便下次访问首页直接登录
     *
     * @param adminEmail
     * @param adminPassword
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam("adminEmail") String adminEmail,
                        @RequestParam("adminPassword") String adminPassword,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes redirectAttributes
    ) {
        try {
//            1 验证登录
            Admin admin = adminLoginService.login(adminEmail, adminPassword);
//            2 添加一条登录记录
            AdminLogin adminLogin = adminLoginService.addLogin(request, admin);

//            3 将key tonken 放入redis中
            HashMap<String, String> redisAdminToken = adminLoginService.getRedisAdminToken(admin, adminLogin);

//            2 创建key tonken 写入cookie 中 response.addCookie();
            Cookie adminKey = new Cookie(Params.ADMIN_KEY, redisAdminToken.get(Params.ADMIN_KEY));
            Cookie adminToken = new Cookie(Params.ADMIN_TOKEN, redisAdminToken.get(Params.ADMIN_TOKEN));
            adminKey.setMaxAge(60 * 20); //2十分钟过期
            adminToken.setMaxAge(60 * 20);
            adminKey.setPath("/qgyblog/admin");
            adminToken.setPath("/qgyblog/admin");
            response.addCookie(adminKey);
            response.addCookie(adminToken);

//            4 将用户的信息返回  放到session中
            admin.setAdminPassword(null);
            request.getSession().setAttribute(Params.ADMIN_lOGIN_KEY, admin);

            redirectAttributes.addFlashAttribute("message", "登录成功");
//            成功返回首页
            return "redirect:/admin/home";
        } catch (BaseException e) {
            redirectAttributes.addFlashAttribute("message", "登录失败帐号或密码错误" + e.getCode() + e.getMessage());
//            失败重新登录
//            前上一次请求
            //之前是那里请求的 就回到那里
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
    }


    /**
     * 退出登录
     *
     * @param adminKey
     * @param adminToken
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/login/exit")
    public String exitLogin(
            @CookieValue(value = "adminKey", required = false) String adminKey,
            @CookieValue(value = "adminToken", required = false) String adminToken,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        //       退出登录
        boolean a = adminLoginService.exitLogin(adminKey, adminToken, request, response);

        if (a == true) {
            redirectAttributes.addFlashAttribute("message", "成功退出登录");
        }

//        去登录
        return "redirect:/getui/admin/login/";
    }


}
