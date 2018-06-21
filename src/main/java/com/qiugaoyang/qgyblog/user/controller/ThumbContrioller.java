package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.dao.BlogRepository;
import com.qiugaoyang.qgyblog.common.domain.ThumbBlog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.exception.BaseException;
import com.qiugaoyang.qgyblog.common.exception.ParamException;
import com.qiugaoyang.qgyblog.common.exception.UserStateException;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;
import com.qiugaoyang.qgyblog.user.services.ThumbBlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/thumb")
public class ThumbContrioller {

    @Resource
    private LoginRegisterService loginRegisterService;

    @Resource
    private ThumbBlogService thumbBlogService;

    // 点赞
    @GetMapping("/{blogId}/blog")
    public String ThumbBlog(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @PathVariable(value = "blogId") Integer blogId,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        //        校验登录 是否
        User user = loginRegisterService.checkLogin(userKey,userToken, request, response);
        if (user == null) {
//            前往登录
            return "redirect:/getui/user/login/";
        }
//        点赞blog
        ThumbBlog thumbBlog=null;
        try {
            thumbBlog = thumbBlogService.thumbBlog(user.getUserId(), blogId);
        } catch (ParamException e) {
            redirectAttributes.addFlashAttribute("message","点赞失败");
        }catch (UserStateException e){
            redirectAttributes.addFlashAttribute("message","您当前的状态异常无法点赞 请激活帐号或者联系管理员");
        }

        if (thumbBlog==null){
            redirectAttributes.addFlashAttribute("message","点赞失败");
        }

        return "redirect:/user/blog/find/" + blogId+"/detail";
    }

}
