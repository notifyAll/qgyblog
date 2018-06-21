package com.qiugaoyang.qgyblog.user.scheduled;

import com.qiugaoyang.qgyblog.user.services.PageviewsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 浏览量的定时器类
 */
@Configuration
@EnableScheduling
public class PageviewsConfig {

    @Resource
    PageviewsService pageviewsService;

    /**
     * 每天 将redis中博客浏览量记录到数据库中 并清零
     */
    @Scheduled(cron = "0 30 23 * * ?") // 每天23:30点执行一次 因为使用到日期 直接就成下一天的了
    public void savePageviewBlog(){
         pageviewsService.savePageviewBlog();
    }

    /**
     * 每天 将redis中首页浏览量记录到数据库中 并清零
     */
    @Scheduled(cron = "0 30 23 * * ?") // 每天23:30点执行一次
    public void savePageviewHome(){
        pageviewsService.savePageviewHome();
    }
}
