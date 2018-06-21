package com.qiugaoyang.qgyblog.user.services;

import com.qiugaoyang.qgyblog.common.domain.PageviewsBlog;
import com.qiugaoyang.qgyblog.common.domain.PageviewsHome;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;

import java.util.List;

/**
 * 有关浏览量的接口
 */
public interface PageviewsService {
    /**
     * 添加一条该博客的浏览量
     * @param blogId 博客id
     */
    void addBlogNum(Integer blogId);

    /**
     * 获取前10条浏览量最高的blog 里面存的是博客id 和浏览量
     *  Iterator<ZSetOperations.TypedTuple<Object>> iterator = range.iterator(); 获取数据时迭代
     * @return
     */
    List<BlogResult> getFirst10PageviewsBlog();

    /**
     * 首页访问量
     */
    void addHomeNum();

    /**
     * 获取首页访问量
     * @return  首页访问量
     */
    Integer getHomeNum();

    /**
     * 将redis中的博客访问量保存到数据库中 并清除redis中的值
     */
    void savePageviewBlog();

    /**
     * 保存每日的首页访问记录
     */
    void savePageviewHome();

    /**
     * 获取首页的7日访问量
      * @return
     */
    List<PageviewsHome> get7DayPageviewHome();
    /**
     * 获取博客的7日访问量
     * @param blogId 博客的id
     * @return
     */
    List<PageviewsBlog> get7DayPageviewBlog(Integer blogId);
}
