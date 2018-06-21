package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.dao.BlogRepository;
import com.qiugaoyang.qgyblog.common.dao.FirstCommentRepository;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.FirstComment;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.enums.ResultEnums;
import com.qiugaoyang.qgyblog.common.exception.IllegalOperationException;
import com.qiugaoyang.qgyblog.common.exception.ParamException;
import com.qiugaoyang.qgyblog.common.exception.UserStateException;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import com.qiugaoyang.qgyblog.user.services.FirstCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class FirstCommentServiceImpl implements FirstCommentService {

    @Resource
    BlogRepository blogRepository;
    @Resource
    FirstCommentRepository firstCommentRepository;

    @Override
    public FirstComment add(FirstComment firstComment) {
//       1 校验参数
        if (firstComment.getBlog().getBlogId() == null || firstComment.getBlog().getBlogId() < 0) {
            throw new ParamException(ResultEnums.PARAM_IS_NULL);
        }
//     2 校验评论人的状态是否正常
        if (firstComment.getUser().getUserState() != 1) {
            throw new UserStateException(ResultEnums.USER_START_ERROR);
        }
//      3  校验博客是否存在且状态正常
        Blog one = blogRepository.findOne(firstComment.getBlog().getBlogId());
        if (one == null) throw new IllegalOperationException(ResultEnums.ILLEGAL_OPERATION_NOT_FIND_DATA);
        //      4  校验当前用户是否为博主
        if (firstComment.getUser().getUserId() == one.getUser().getUserId()) {
//            是博主
            Integer blogState = one.getBlogState();
            if (blogState != 1 && blogState != 2 && blogState != 0) {
                throw new IllegalOperationException(ResultEnums.ILLEGAL_OPERATION);
            }
//            如果是博主本人则评论为已读状态
            firstComment.setFirstCommentRead(1);
        } else {
            if (one.getBlogState() == 1) {
//                如果不是博主 则评论为未读
                firstComment.setFirstCommentRead(0);
            } else {
                throw new IllegalOperationException(ResultEnums.ILLEGAL_OPERATION);
            }
        }
        firstComment.setFirstCommentState(1);
        firstComment.setFirstCommentTime(new Date());
        firstComment.setFirstCommentId(null);
//  5 保存
        FirstComment save = firstCommentRepository.save(firstComment);

        return save;
    }


    /**
     * 查询 带分页
     *
     * @param blogId           博客id
     * @param firstCommentPage 页码
     * @return
     */
    @Override
    public PageUtil<List<FirstComment>> findListByBlogIdAndPage(Integer blogId, Integer firstCommentPage) {
        if (blogId == null || blogId < 0) return null;
//        评论总数
        Blog blog = blogRepository.findOne(blogId);
        Integer size = firstCommentRepository.countByBlogAndFirstCommentState(blog, 1);
        PageUtil<List<FirstComment>> pageUtil = new PageUtil<>(size, Params.PAGE_FIRST_COMMENTS_SIZE);
        pageUtil.setPage(firstCommentPage);

        List<FirstComment> byBlogIdAndLimit = firstCommentRepository.findByBlogIdAndLimit(blogId, pageUtil.getStartIndex(), pageUtil.getPageSize());

        pageUtil.setPageDate(byBlogIdAndLimit);

        return pageUtil;
    }

    /**
     * 查询所有评论我的博客的评论
     *
     * @param user 我
     * @param page 页码
     * @return
     */
    @Override
    @Transactional
    public PageUtil<List<FirstComment>> findMyCommentByPage(User user, Integer page) {
//       1 查询所有我的博客;
        List<Integer> blogIds = blogRepository.fingMyAllBlogId(user.getUserId());

//       2 查询所有评论
        Integer size = firstCommentRepository.countByBlogs(blogIds);
        PageUtil<List<FirstComment>> pageUtil = new PageUtil<>(size, 15);
        pageUtil.setPage(page);
        List<FirstComment> list = firstCommentRepository.findByBlogIdsAndLimit(blogIds, pageUtil.getStartIndex(), pageUtil.getPageSize());
        pageUtil.setPageDate(list);
//       3 将这些评论设置为已读 todo 目前设置为点击全部已读按钮 才将其设置已读


        return pageUtil;
    }

    /**
     * 查询所有未读评论的数量
     *
     * @param user 要查询的用户
     * @return
     */
    @Override
    public Integer fingNoReadNum(User user) {
        //       1 查询所有我的博客;
        List<Integer> blogIds = blogRepository.fingMyAllBlogId(user.getUserId());

        Integer size = firstCommentRepository.countNoReadByBlogIds(blogIds);

        return size;
    }

    /**
     * 将所有 未读的评论设为已读
     *
     * @param user
     */
    @Override
    @Transactional
    public Integer updReadState(User user) {
        //       1 查询所有我的博客;
        List<Integer> blogIds = blogRepository.fingMyAllBlogId(user.getUserId());
//        修改 是否阅读状态
        Integer line = firstCommentRepository.updAllReagStateByBlogIds(blogIds);
        return line;
    }

}
