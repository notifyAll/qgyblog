package com.qiugaoyang.qgyblog.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 获取页面资源
 */
@Controller
@RequestMapping("/")
public class UiController {
    @GetMapping("/getui/user/{resource}/")
    public ModelAndView getUserUi(@PathVariable("resource") String resource,
                                  @ModelAttribute("message") String message
    ) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("user/"+resource);
        modelAndView.addObject("message",message);
        return modelAndView;
    }
    @GetMapping("/getui/admin/{resource}/")
    public ModelAndView getAdminUi(@PathVariable("resource") String resource) {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("admin/"+resource);
        return modelAndView;
    }
}
