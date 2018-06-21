package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.ThumbBlog;
import com.qiugaoyang.qgyblog.common.domain.ThumbCommentPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbBlogRepository extends JpaRepository<ThumbBlog,ThumbCommentPK>{


    /**
     * 查询点赞数
     * @param blogId
     * @return
     */
    Integer countByBlogId(Integer blogId);

    /**
     * 查询记录
     * @param userId
     * @param blogId
     * @return
     */
    ThumbBlog findFirstByUserIdAndBlogId(Integer userId,Integer blogId);

//

}
