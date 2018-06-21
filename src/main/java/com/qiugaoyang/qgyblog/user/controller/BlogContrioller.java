package com.qiugaoyang.qgyblog.user.controller;


import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.domain.*;
import com.qiugaoyang.qgyblog.common.exception.BaseException;
import com.qiugaoyang.qgyblog.common.exception.IllegalOperationException;
import com.qiugaoyang.qgyblog.common.exception.UserStateException;
import com.qiugaoyang.qgyblog.common.resultbean.CollectBlogNumResult;
import com.qiugaoyang.qgyblog.common.resultbean.EchartsResult;
import com.qiugaoyang.qgyblog.common.resultbean.ThumbBlogNumResult;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import com.qiugaoyang.qgyblog.user.services.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user/blog")
public class BlogContrioller {
    @Resource
    private BlogService blogService;

    @Resource
    private LoginRegisterService loginRegisterService;

    @Resource
    private FirstCommentService firstCommentService;

    @Resource
    private ThumbBlogService thumbBlogService;

    @Resource
    private PageviewsService pageviewsService;

    @Resource
    private CollectBlogService collectBlogService;

    @Resource
    private  AllCalssService allCalssService;
    //  新增blog
    @PostMapping("/add")
    public String addBlog(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @Valid Blog blog,
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

        blog.setUser(user);
        if (blog.getBlogId() == null || blog.getBlogId() < 0) {
            //        保存 数据
            blog = blogService.addBlog(blog);
        } else {
//             修改 数据
            try {
                blog = blogService.updBlog(blog);
                if (blog == null) {
                    redirectAttributes.addFlashAttribute("message", "修改失败 请稍后再试");
                }
            } catch (IllegalOperationException e) {
//                去404 页面
                redirectAttributes.addFlashAttribute("message", e.getCode());
                return "redirect:/getui/user/404/";
            } catch (UserStateException e) {
                redirectAttributes.addFlashAttribute("message", "您的帐号存在问题");
            }
        }

//        前往博客详情页 进行提交审核的请求
        return "redirect:/user/blog/find/" + blog.getBlogId() + "/detail";
    }


    /**
     * 查询一条blog  blog详情 博客详情页
     * <p>
     * 博客的访问量  当天的存在rdsis 中 使用定时器 00：00点将其存入数据库
     *
     * @param userKey
     * @param userToken
     * @param blogId
     * @param request
     * @param response
     * @param message
     * @param firstCommentPage 评论区的分页 默认第一页
     * @return
     */
    @GetMapping("/find/{blogId}/detail")
    public ModelAndView fingBlogById(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @PathVariable("blogId") Integer blogId,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("message") String message,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer firstCommentPage

    ) {
//        判断是否为本人操作  如果是本人 页面会显示 修改 提交之类的按钮
//        操作这个会在redis中添加一次浏览记录
//        查询 blog的时候 会自带博主信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", message);

        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        Blog blog = null;
//        查询blog
        try {
            blog = blogService.fingBlogById(blogId, user);
            if (blog == null) {
//                前往404页面 TODO
                modelAndView.addObject("message", "blog不存在或者下架");
                modelAndView.setViewName("404");
                return modelAndView;
            }
        } catch (UserStateException e) {
            //              前往404页面
            modelAndView.addObject("message", "" + e.getCode());
            modelAndView.setViewName("404");
            return modelAndView;
        }
        modelAndView.addObject(Params.BLOG_DETAIL, blog);

//        博客的点赞 量 (user==null)?null:user.getUserId() 防止user抛出空指针异常
        ThumbBlogNumResult thumbBlogNumResult = thumbBlogService.getThumbBlogNumResult(blogId, (user == null) ? null : user.getUserId());
        modelAndView.addObject(Params.THUMB_BLOG_NUM_RESULT, thumbBlogNumResult);

//      博客的收藏量 和是否已经收藏
        CollectBlogNumResult collectBlogNumResult=collectBlogService.getCollectBlogNumResult(blogId,(user == null) ? null : user.getUserId());
        modelAndView.addObject(Params.COLLECT_BLOG_NUM_RESULT, collectBlogNumResult);

//       评论信息(评论数据采用ajax 获取)
        PageUtil<List<FirstComment>> pageUtil = firstCommentService.findListByBlogIdAndPage(blog.getBlogId(), firstCommentPage);
        modelAndView.addObject(Params.PAGE_FIRST_COMMENTS, pageUtil);
//        向redis中为该blog 加1访问量 TODO
        pageviewsService.addBlogNum(blogId);

//        博客详情页
        modelAndView.setViewName("user/detailblog");

        return modelAndView;
    }


    /**
     * 修改blog 回到addblog 页面 将数据填入
     * /qgyblog/user/blog/toadd?blogId=1
     */
    @GetMapping("/toadd")
    public ModelAndView toAddBlog(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @RequestParam(value = "blogId", required = false) Integer blogId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/user/addblog");
//        查询博客类目
        List<AllClass> allClasses=allCalssService.findAllBlogClass();
        modelAndView.addObject("allBlogClass",allClasses);
//        如果不传id 择代表只是添加
        if (blogId == null || blogId < 0) {
            return modelAndView;
        }
        //        校验登录
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
//        查询blog
        Blog blog = null;
        try {
            blog = blogService.fingBlogById(blogId, user);
            if (blog == null) {
                return modelAndView;
            }
        } catch (UserStateException e) {
            //              前往404页面
//            modelAndView.addObject("message", "" + e.getCode());
//            modelAndView.setViewName("404");
            return modelAndView;
        }

//        判断是否为博主本人
        if (blog.getUser().getUserId() != user.getUserId())
            return modelAndView;

        modelAndView.addObject(Params.BLOG_DETAIL, blog);

        return modelAndView;
    }

    /**
     * 修改blog的状态 提交审核or删除
     * <p>
     * 跳转详情页
     * <p>
     * 状态 -1删除 0待审核 1审核通过 2提交审核
     */
    @GetMapping("/upd/{blogId}/state/{state}")
    public String updState(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @PathVariable(value = "blogId") Integer blogId,
            @PathVariable(value = "state") Integer state,
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

        Blog blog = null;
        try {
            switch (state) {
                case -1://               删除
                case 0://                转为私有
                case 2://                提交审核
                    blog = blogService.updBlogState(user, blogId, state);
                    break;
                default:
                    return "redirect:/getui/user/404/";
            }
        } catch (BaseException e) {
            redirectAttributes.addFlashAttribute("message", e.getCode());
            return "redirect:/getui/user/404/";
        }

        if (blog == null) {
            return "redirect:/getui/user/404/";
        }

        //        前往博客详情页 进行提交审核的请求
        return "redirect:/user/blog/find/" + blog.getBlogId() + "/detail";
    }

    /**
     * 查询某位用户所有的博客 带分页
     * /qgyblog/user/blog/find/1/list?page=1
     *
     * @param userKey
     * @param userToken
     * @param userId    博主的id 和查询用户的id是不一样的
     * @param page      当前页码  从1开始
     * @param request
     * @param response
     * @return
     */
    @GetMapping("find/{userId}/list")
    public ModelAndView findUserBlogs(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @PathVariable(value = "userId") Integer userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/userblogs");
        //        获取登录的用户数据 可为空 用于判断是否为博主
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
//        博主
        User blogUser = loginRegisterService.getByUserId(userId);
        if (blogUser != null && blogUser.getUserState() == 1) {
            modelAndView.addObject(Params.BLOG_USER, blogUser);
        }
//        查询
        PageUtil<List<Blog>> pageUtil = blogService.findBlogsByUserAndPage(user, userId, page);

        modelAndView.addObject(Params.PAGE_BLOGS, pageUtil);

        return modelAndView;
    }


//    收藏


    /**
     *获取博客7天浏览量的 折线图数据
     */
    @GetMapping("/get/7day/pageviews")
    @ResponseBody
    public EchartsResult get7dayPageviewsBlog(
            @CookieValue(value = "userKey", required = false) String userKey,
            @CookieValue(value = "userToken", required = false) String userToken,
            @RequestParam(value = "blogId", defaultValue = "1") Integer blogId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (blogId == null || blogId < 0) return null;
        //        校验登录 是否
        User user = loginRegisterService.checkLogin(userKey, userToken, request, response);
        if (user == null) return null;

        Blog blog = blogService.fingBlogById(blogId, user);
        if (blog.getUser().getUserId() != user.getUserId()) return null;

        List<PageviewsBlog> dayPageviewBlog = pageviewsService.get7DayPageviewBlog(blogId);

        EchartsResult echartsResult = new EchartsResult();
        List categories = new ArrayList();
        List data = new ArrayList();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < dayPageviewBlog.size(); i++) {
            categories.add(simpleDateFormat.format(dayPageviewBlog.get(i).getPageviewsBlogTime()));
            data.add(dayPageviewBlog.get(i).getPageviewsBlogNum());
        }
        echartsResult.setCategories(categories);
        echartsResult.setData(data);

        return echartsResult;
    }
}
