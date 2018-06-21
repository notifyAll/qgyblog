package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;
import com.qiugaoyang.qgyblog.user.services.BlogService;
import com.qiugaoyang.qgyblog.user.services.LoginRegisterService;
import com.qiugaoyang.qgyblog.user.services.PageviewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class IndexController {
    @Resource
    LoginRegisterService loginRegisterService;

    @Resource
    PageviewsService pageviewsService;
    @Resource
    BlogService blogService;

    /**
     * @param userKey    用户的登录key
     * @param userToken  用户的token
     * @param attributes
     * @param model
     * @param request
     * @param response
     * @param message    请求跳转后返回的消息
     * @return
     */
    @GetMapping("/home")
    public ModelAndView index(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            RedirectAttributes attributes,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute(value = "message") String message
    ) {
        ModelAndView modelAndView = new ModelAndView();

//     1   校验是否已经登录 获取用户数据
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
//        modelAndView.addObject(Params.USER_SESSION_LOGIN_KEY, user);

//     2   获取首页数据
//         获取最新的10条数据
        List<Blog> newblog = blogService.getNewIndexBlog();
        modelAndView.addObject(Params.INDEX_NEW_BLOG, newblog);
//        获取10条推荐 通过点赞量和收藏量
        List<BlogResult> TuiJianBlog=blogService.getTuiJianBlog();
        modelAndView.addObject(Params.INDEX_TUI_JIAN_BLOG,TuiJianBlog);
//        获取10条热门 当日浏览量最高的
        List<BlogResult> hotblog = blogService.getHotIndexBlog();
        modelAndView.addObject(Params.INDEX_HOT_BLOG, hotblog);
//        获取top10作者 TODO 按照博客被收藏最多的
        List<User> renQiUser = loginRegisterService.findRenQiUser();
        modelAndView.addObject(Params.INDEX_REN_QI_USER, renQiUser);
        // 添加首页访问量
        pageviewsService.addHomeNum();
//     3   重定向的参数处理
//        message="ceshi1"; 此处貌似不需要接收 这个message  页面都可以获取到message
        if (message != null && !message.trim().equals("")) {
            modelAndView.addObject("message", message);
        }

//     4   填充数据返回首页
        modelAndView.setViewName("user/index");
        return modelAndView;
    }
}

