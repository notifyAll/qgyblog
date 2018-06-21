package com.qiugaoyang.qgyblog.admin.services;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.util.PageUtil;

import java.util.List;

public interface AdminBlogService {
    /**
     * 通过条件查询博客
     * @param blog 条件
     * @param page 页码
     * @return
     */
    PageUtil<List<Blog>> searchBlogs(Blog blog, Integer page);


    /**
     * 通过id查询博客
     * @param blogId  博客id
     * @return
     */
    Blog findOne(Integer blogId);
}
