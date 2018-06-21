package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.common.dao.BlogRepository;
import com.qiugaoyang.qgyblog.common.dao.CollectBlogRepository;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.CollectBlog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.resultbean.CollectBlogNumResult;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import com.qiugaoyang.qgyblog.user.services.CollectBlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 收藏
 */
@Service
public class CollectBlogServiceImpl implements CollectBlogService {
    @Resource
    CollectBlogRepository collectBlogRepository;
    @Resource
    BlogRepository blogRepository;


    /**
     * 添加收藏
     *
     * @param user
     * @param blogId
     * @return
     */
    @Override
    @Transactional
    public CollectBlog save(User user, Integer blogId) {
        Blog blog = blogRepository.findOne(blogId);
        if (user.getUserState() != 1) {
            return null;
        }

        if (blog == null || blog.getBlogState() != 1) {
            return null;
        }

//        查询是否已经收藏
        CollectBlog collectBlog = collectBlogRepository.findByBlogAndUserId(blog, user.getUserId());
        if (collectBlog != null) {
            return null;
        }

//        收藏
        collectBlog = new CollectBlog();
        collectBlog.setBlog(blog);
        collectBlog.setUserId(user.getUserId());
        collectBlog.setCollectBlogTime(new Date());

//
        CollectBlog save = collectBlogRepository.save(collectBlog);

        return save;
    }

    @Override
    @Transactional
    public CollectBlog del(User user, Integer blogId) {
        if (user.getUserState() != 1) {
            return null;
        }
        Blog blog = blogRepository.findOne(blogId);
        if (blog == null) {
            return null;
        }
        //        查询是否已经收藏
        CollectBlog collectBlog = collectBlogRepository.findByBlogAndUserId(blog, user.getUserId());
        if (collectBlog == null) {
            return null;
        }
        collectBlogRepository.delete(collectBlog.getCollectBlogId());
        return collectBlog;
    }

    /**
     * 查询收藏带分页
     * 若博客的状态异常则 前端将其的内容修改 比如这篇博客已被下架 或删除
     * @param userId 用户id
     * @param page 页码
     * @return
     */
    @Override
    public PageUtil<List<CollectBlog>> findPageByUserId(Integer userId, Integer page) {
        Integer size = collectBlogRepository.countByUserId(userId);
        PageUtil<List<CollectBlog>> pageUtil = new PageUtil<>(size, 10);
        pageUtil.setPage(page);

//        查询
        List<CollectBlog> collectBlogs = collectBlogRepository.fingPageByUserId(userId, pageUtil.getStartIndex(), pageUtil.getPageSize());

        pageUtil.setPageDate(collectBlogs);
        return pageUtil;
    }

    /**
     * 查询总收藏量 和该用户是否收藏
     *
     * @param blogId
     * @param userId
     * @return
     */
    @Override
    public CollectBlogNumResult getCollectBlogNumResult(Integer blogId, Integer userId) {
        CollectBlogNumResult collectBlogNumResult=new CollectBlogNumResult();
//        查询总收藏数
        Blog blog=blogRepository.findOne(blogId);
        if (blog==null) return collectBlogNumResult;

        Integer collectBlogNum=collectBlogRepository.countByBlog(blog);
        collectBlogNumResult.setCollectBlogNum(collectBlogNum);

        if (userId==null){
            return collectBlogNumResult;
        }
        CollectBlog collectBlog=collectBlogRepository.findByBlogAndUserId(blog,userId);
        if (collectBlog!=null){
            collectBlogNumResult.setCollect(true);
        }
        return collectBlogNumResult;
    }
}
