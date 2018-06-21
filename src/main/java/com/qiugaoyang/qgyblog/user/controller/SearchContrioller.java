package com.qiugaoyang.qgyblog.user.controller;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import com.qiugaoyang.qgyblog.user.services.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 搜搜功能
 */
@Controller
@RequestMapping("/user/search")
public class SearchContrioller {


    @Resource
    private BlogService blogService;

    /**
     * 搜搜功能
     /user/search/blog?param=&page=
     * @param request
     * @param response
     * @param param    搜索参数
     * @param page     分页
     * @param message
     * @return
     */
    @GetMapping("/blog")
    public ModelAndView search(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "param", required = false) String param,
            @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
            @ModelAttribute("message") String message
    ) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/searchblogs");
        if (param==null||param.trim().equals("")){
            return modelAndView;
        }
        if (page<=0) page=1;
//        查询数据
        PageUtil<List<Blog>> pageUtil = blogService.searchBlogs(param.trim(), page);
        modelAndView.addObject("pageBlogs", pageUtil);

        modelAndView.addObject("param",param.trim());
        return modelAndView;
    }
}
