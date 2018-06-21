package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.domain.CollectBlog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import com.qiugaoyang.qgyblog.user.services.BlogService;
import com.qiugaoyang.qgyblog.user.services.CollectBlogService;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 收藏
 */
@Controller
@RequestMapping("/user/cb")
public class CollectBlogContrioller {

    @Resource
    private LoginRegisterService loginRegisterService;

    @Resource
    private CollectBlogService collectBlogService;

    @Resource
    private BlogService blogService;

    /**
     * 我的收藏
     */
    @GetMapping("/find/myself")
    public ModelAndView myCollectBlog(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("message") String message,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page
    ) {
        ModelAndView modelAndView = new ModelAndView();
        //        校验登录 是否
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        if (user == null) {
//            前往登录
            modelAndView.setViewName("user/login");
            modelAndView.addObject("messgae", "请先登录");
            return modelAndView;
        }
//查询数据
        PageUtil<List<CollectBlog>> pageUtil = collectBlogService.findPageByUserId(user.getUserId(), page);
        modelAndView.setViewName("user/userCollectblogs"); // todo
        modelAndView.addObject("pageBlogs",pageUtil);
        return modelAndView;
    }

    /**
     * 收藏 博客
     *
     * @param userKey
     * @param userToken
     * @param blogId
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/add/{blogId}/blog")
    public String add(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @PathVariable(value = "blogId") Integer blogId,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        //        校验登录 是否
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        if (user == null) {
//            前往登录
            return "redirect:/getui/user/login/";
        }

        if (blogId == null || blogId < 0) {
            redirectAttributes.addFlashAttribute("message", "收藏失败");
            return "redirect:/getui/user/404/";
        }

        CollectBlog collectBlog = collectBlogService.save(user, blogId);

        if (collectBlog == null) {
            redirectAttributes.addFlashAttribute("message", "收藏失败");
            return "redirect:/getui/user/404/";
        }
//        成功还返回博客详情页面
        return "redirect:/user/blog/find/" + collectBlog.getBlog().getBlogId() + "/detail";
    }


    /**
     * 删除收藏
     *
     * @param userKey
     * @param userToken
     * @param blogId
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/del/{blogId}/blog")
    public String del(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @PathVariable(value = "blogId") Integer blogId,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        //        校验登录 是否
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        if (user == null) {
//            前往登录
            return "redirect:/getui/user/login/";
        }
        if (blogId == null || blogId < 0) {
            redirectAttributes.addFlashAttribute("message", "删除收藏失败");
            return "redirect:/getui/user/404/";
        }
        CollectBlog collectBlog = collectBlogService.del(user, blogId);
        if (collectBlog == null) {
            redirectAttributes.addFlashAttribute("message", "删除收藏失败");
        }

        //判读这是那里请求的 就回到那里
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
