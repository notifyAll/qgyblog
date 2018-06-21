package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.FirstComment;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.exception.IllegalOperationException;
import com.qiugaoyang.qgyblog.common.exception.ParamException;
import com.qiugaoyang.qgyblog.common.exception.UserStateException;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import com.qiugaoyang.qgyblog.user.services.BlogService;
import com.qiugaoyang.qgyblog.user.services.FirstCommentService;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 一级评论
 */
@Controller
@RequestMapping("/user/first/comment")
public class FirstCommentContrioller {
    @Resource
    private LoginRegisterService loginRegisterService;

    @Resource
    private FirstCommentService firstCommentService;

    @Resource
    private BlogService blogService;
    /**
     * 添加一条评论
     *
     * @param userKey
     * @param userToken
     * @param firstComment
     * @return
     */
    @PostMapping("/add")
    public String addComment(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @RequestParam(value = "blogId",required = false) Integer blogId,
            @Valid FirstComment firstComment,
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
        firstComment.setUser(user);

        if (blogId == null) {
            return "redirect:/getui/user/404/";
        }
        Blog blog=new Blog();
        blog.setBlogId(blogId);
        firstComment.setBlog(blog);
        String message = null;//    消息
        try {
            //      添加评论
            FirstComment save = firstCommentService.add(firstComment);
        } catch (ParamException e) {
            message = "评论失败请检查评论内容";
        } catch (IllegalOperationException e) {
            message = "" + e.getCode();
            return "redirect:/getui/user/404/";
        } catch (UserStateException e) {
            message = "您当前被禁言无法评论,请联系管理员";
        } finally {
            redirectAttributes.addFlashAttribute("message", message);
        }

//        前往博客详情页
        return "redirect:/user/blog/find/" + firstComment.getBlog().getBlogId()+ "/detail";
    }


    /**
     * 消息页查询自己收到的所有评论 消息
     /qgyblog/user/first/comment/message?page=1
     * @param userKey
     * @param userToken
     * @param page 消息页码
     * @param request
     * @param response
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/message")
    public ModelAndView findPage(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        ModelAndView modelAndView=new ModelAndView();
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        if (user == null) return null;

        PageUtil<List<FirstComment>> pageUtil=firstCommentService.findMyCommentByPage(user,page);
        modelAndView.addObject("pageUtil",pageUtil);

        modelAndView.setViewName("user/message");

        return modelAndView;
    }
}
