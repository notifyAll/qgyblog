package com.qiugaoyang.qgyblog.user.services;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;
import com.qiugaoyang.qgyblog.common.util.PageUtil;

import java.util.List;

public interface BlogService {
//   获取首页三类博客的数据
     void getIndexBlog();
//  获取首页最新的10条数据
    List<Blog> getNewIndexBlog();
//增加一
    Blog addBlog(Blog blog);
//查询通过id
    Blog fingBlogById(Integer blogId, User user);
//修改blog
    Blog updBlog(Blog blog);
//修改状态
    Blog updBlogState(User user, Integer blogId, Integer state);
//查询某位用户的所有博客
    PageUtil<List<Blog>> findBlogsByUserAndPage(User selectUser,Integer blogUserId, Integer page);
    /**
     * 获取首页上最热博客 为当天浏览量最高的
     * @return
     */
    List<BlogResult> getHotIndexBlog();

    /**
     * 获取首页推荐内容通过 博客的点赞量和收藏量排序
     * @return
     */
    List<BlogResult> getTuiJianBlog();

    /**
     * 搜搜
     * @param param
     * @param page
     * @return
     */
    PageUtil<List<Blog>> searchBlogs(String param, Integer page);
}
