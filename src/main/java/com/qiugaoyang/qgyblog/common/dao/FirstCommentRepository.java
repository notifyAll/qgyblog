package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.FirstComment;;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface FirstCommentRepository extends JpaRepository<FirstComment, Integer> {
    //    计数 通过博客id 和状态
    Integer countByBlogAndFirstCommentState(Blog blog, Integer firstCommentState);

    /**
     * 通过博客id查询指定博客的评论
     * @param blogId
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Modifying
    @Query(value = "SELECT * FROM first_comment WHERE blog_id=?1 AND first_comment_state = 1 ORDER BY first_comment_time DESC limit ?2 ,?3", nativeQuery = true)
    List<FirstComment> findByBlogIdAndLimit(Integer blogId, Integer startIndex, Integer pageSize);

    /**
     * 通过博客 id集合 查询所有评论条数
     * @param blogId
     * @return
     */
//    @Modifying 这玩意不能随便乱加
    @Transactional
    @Query(value = "SELECT COUNT(first_comment_id) FROM first_comment WHERE first_comment.blog_id IN ?1 AND first_comment_state=1",nativeQuery = true)
    Integer countByBlogs(List<Integer> blogId);

    /**
     * 通过博客 id集合 查询所有评论
     * @param blogIds 博客id的集合
     * @param startIndex 开始索引
     * @param pageSize 每页限制数
     * @return
     */
    @Modifying
    @Query(value = "SELECT * FROM first_comment WHERE blog_id in ?1 AND first_comment_state = 1 ORDER BY first_comment_time DESC limit ?2 ,?3", nativeQuery = true)
    List<FirstComment> findByBlogIdsAndLimit(List<Integer> blogIds, Integer startIndex, Integer pageSize);


    /**
     * 查询 所有未读评论的条数
     * 该评论 作者是否已读 0未读 1已读
     * 评论状态 0不可用（删除） 1可用
     * @param blogIds
     * @return
     */
    @Modifying
    @Query(value = "SELECT COUNT(first_comment_id) FROM first_comment WHERE blog_id IN ?1 AND first_comment_state=1 AND first_comment_read=0",nativeQuery = true)
    Integer countNoReadByBlogIds(List<Integer> blogIds);

    /**
     * 将所有未读的评论设为已读
     */
    @Modifying
    @Query(value = "UPDATE first_comment SET first_comment_read=1 WHERE blog_id IN ?1 AND first_comment_state=1",nativeQuery = true)
    Integer updAllReagStateByBlogIds(List<Integer> blogIds);


}
