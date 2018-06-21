package com.qiugaoyang.qgyblog.admin.controller;

import com.qiugaoyang.qgyblog.admin.services.AdminBlogService;
import com.qiugaoyang.qgyblog.admin.services.AdminLoginService;
import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.Admin;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/blog")
public class AdminBlogController {

    @Resource
    private AdminBlogService adminBlogService;
    @Resource
    private AdminLoginService adminLoginService;

    /**
     * 查询博客列表
     * 返回博客管理列表
     *
     * @param adminKey
     * @param adminToken
     * @param blog
     * @param page
     * @param request
     * @param response
     * @param redirectAttributes
     * @param message
     * @return
     */
    @GetMapping("/list")
    public ModelAndView userblogs(
            @CookieValue(value = "adminKey", required = false) String adminKey,
            @CookieValue(value = "adminToken", required = false) String adminToken,
            @Valid Blog blog,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            @ModelAttribute(value = "message") String message
    ) {
        ModelAndView modelAndView = new ModelAndView();
//        校验登录
        Admin admin = adminLoginService.checkLogin(adminKey, adminToken, request, response);
        if (admin == null) {
//            登录
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
//        查询 当state 不传时就是查询全部
        PageUtil<List<Blog>> pageUtil = adminBlogService.searchBlogs(blog, page);
        modelAndView.setViewName("admin/blog");
        modelAndView.addObject("pageBlogs", pageUtil);
//        返回查询条件
        modelAndView.addObject("param", blog);
        return modelAndView;
    }

    /**
     * 获取博客详情页 进行管理
     *
     * @param adminKey
     * @param adminToken
     * @param blogId
     * @param request
     * @param response
     * @param redirectAttributes
     * @param message
     * @return
     */
    @GetMapping("/{blogId}/detail")
    public ModelAndView blogDetail(
            @CookieValue(value = "adminKey", required = false) String adminKey,
            @CookieValue(value = "adminToken", required = false) String adminToken,
            @PathVariable(value = "blogId") Integer blogId,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            @ModelAttribute(value = "message") String message
    ) {

        //1校验登录
        ModelAndView modelAndView = new ModelAndView();
//        校验登录
        Admin admin = adminLoginService.checkLogin(adminKey, adminToken, request, response);
        if (admin == null) {
//            登录
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        //查询blog
        Blog blog = adminBlogService.findOne(blogId);
        modelAndView.addObject(Params.BLOG_DETAIL, blog);
//        用户数据 todo 博客提交次数 这片博客被审核次数 拒绝审核次数 之前审核的记录 系统消息

        //     获取之前的url  用于返回  todo
        //       返回页面
        modelAndView.setViewName("admin/detailblog");
        return modelAndView;
    }


    // 博客审核操作 审核通过 审核拒绝 需提示信息
}
