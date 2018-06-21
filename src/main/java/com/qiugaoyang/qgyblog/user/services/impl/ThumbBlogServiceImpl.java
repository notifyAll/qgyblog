package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.common.dao.BlogRepository;
import com.qiugaoyang.qgyblog.common.dao.ThumbBlogRepository;
import com.qiugaoyang.qgyblog.common.dao.UserRepository;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.ThumbBlog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.enums.ResultEnums;
import com.qiugaoyang.qgyblog.common.exception.ParamException;
import com.qiugaoyang.qgyblog.common.exception.UserStateException;
import com.qiugaoyang.qgyblog.common.resultbean.ThumbBlogNumResult;
import com.qiugaoyang.qgyblog.user.services.ThumbBlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ThumbBlogServiceImpl implements ThumbBlogService {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    ThumbBlogRepository thumbBlogRepository;
    @Resource
    UserRepository userRepository;
    @Resource
    BlogRepository blogRepository;

    /**
     * 博客 点赞
     *
     * @param userId 点赞人
     * @param blogId 点赞博客
     * @return
     */
    @Override
    public ThumbBlog thumbBlog(Integer userId, Integer blogId) {
        if (userId == null || blogId == null) {
            throw new ParamException(ResultEnums.PARAM_IS_NULL);
        }
//        判断参数
        User user = userRepository.findOne(userId);
        Blog blog = blogRepository.findOne(blogId);
        if (user == null || blog == null) {
            logger.debug("userId:" + userId + " blogId:" + blogId + "参数异常");
            throw new ParamException(ResultEnums.PARAM_IS_NULL);
        }

        if (user.getUserState() != 1) {
            logger.debug("userId:" + userId + " blogId:" + blogId + "用户状态异常");
            throw new UserStateException(ResultEnums.USER_START_ERROR);
        }
        if (blog.getBlogState() == -1) {
            logger.debug("userId:" + userId + " blogId:" + blogId + "博客已经被删除");
            throw new ParamException(ResultEnums.PARAM_IS_NULL);
        }

        ThumbBlog thumbBlog = new ThumbBlog();
        thumbBlog.setUserId(userId);
        thumbBlog.setBlogId(blogId);
//        是博主
        ThumbBlog save = null;
        if (blog.getUser().getUserId() == userId) {
            save = thumbBlogRepository.save(thumbBlog);
        } else {
            if (blog.getBlogState() == 1) {
                save = thumbBlogRepository.save(thumbBlog);
            }
        }


        return save;
    }

    /**
     * 查询点赞数目
     *
     * @param blogId 博客id 必须
     * @param userId 用户id 可为null 如果为null 则是否点赞为false
     * @return
     */
    @Override
    public ThumbBlogNumResult getThumbBlogNumResult(Integer blogId, Integer userId) {
        ThumbBlogNumResult thumbBlogNumResult = new ThumbBlogNumResult();

//        赞数量
        thumbBlogNumResult.setThumbBlogNum(thumbBlogRepository.countByBlogId(blogId));

//        判断该用户是否已经点赞
        if (userId != null) {
//            ThumbBlogPK thumbBlogPK=new ThumbBlogPK(userId,blogId);
            ThumbBlog firstByUserIdAndBlogId = thumbBlogRepository.findFirstByUserIdAndBlogId(userId, blogId);
            if (firstByUserIdAndBlogId != null) {
                thumbBlogNumResult.setThumb(true);
            }
        }
        return thumbBlogNumResult;
    }


}
