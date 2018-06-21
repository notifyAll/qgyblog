package com.qiugaoyang.qgyblog.user.services;

import com.qiugaoyang.qgyblog.common.domain.CollectBlog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.resultbean.CollectBlogNumResult;
import com.qiugaoyang.qgyblog.common.util.PageUtil;

import java.util.List;

/**
 * 收藏
 */
public interface CollectBlogService {
    /**
     * 添加收藏
     * @param user
     * @param blogId
     * @return
     */
    CollectBlog save(User user, Integer blogId);

    /**
     * 删除 某个收藏
     * @param user 用户
     * @param blogId 博客
     * @return
     */
    CollectBlog del(User user, Integer blogId);

    /**
     * 查询收藏带分页
     * @param userId
     * @param page
     * @return
     */
    PageUtil<List<CollectBlog>> findPageByUserId(Integer userId, Integer page);

    /**
     * 查询总收藏量 和该用户是否收藏
     * @param blogId
     * @param userId
     * @return
     */
    CollectBlogNumResult getCollectBlogNumResult(Integer blogId, Integer userId);
}
