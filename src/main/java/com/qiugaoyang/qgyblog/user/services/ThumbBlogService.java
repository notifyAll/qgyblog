package com.qiugaoyang.qgyblog.user.services;

import com.qiugaoyang.qgyblog.common.domain.ThumbBlog;
import com.qiugaoyang.qgyblog.common.resultbean.ThumbBlogNumResult;

public interface ThumbBlogService {

    ThumbBlog thumbBlog(Integer userId, Integer blogId);

    /**
     * 查询点赞数目
     * @param blogId 博客id 必须
     * @param userId 用户id 可为null 如果为null 则是否点赞为false
     * @return
     */
    ThumbBlogNumResult getThumbBlogNumResult(Integer blogId, Integer userId);
}
