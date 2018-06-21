package com.qiugaoyang.qgyblog.user.services;

import com.qiugaoyang.qgyblog.common.domain.FirstComment;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.util.PageUtil;

import java.util.List;

public interface FirstCommentService {
    /**
     * 添加评论
     * @param firstComment
     * @return
     */
    FirstComment add(FirstComment firstComment);

    /**
     * 博客详情页中的评论
     * @param blogId
     * @param firstCommentPage
     * @return
     */
    PageUtil<List<FirstComment>> findListByBlogIdAndPage(Integer blogId, Integer firstCommentPage);

    /**
     * 查询所有评论我的博客的评论
     * @param user
     * @param page
     * @return
     */
    PageUtil<List<FirstComment>> findMyCommentByPage(User user, Integer page);

    /**
     * 查询所有未读评论的数量
      * @param user
     * @return
     */
    Integer fingNoReadNum(User user);

    /**
     * 将所有 未读的评论设为已读
     */
    Integer updReadState(User user);
}
