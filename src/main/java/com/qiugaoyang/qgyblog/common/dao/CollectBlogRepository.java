package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.CollectBlog;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;
import com.qiugaoyang.qgyblog.common.resultbean.CollectBlogResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 收藏
 */
public interface CollectBlogRepository extends JpaRepository<CollectBlog,Integer>{
    /**
     * 查询通过博客
     * @param blog 博客
     * @param userId 用户id
     * @return
     */
     CollectBlog findByBlogAndUserId(Blog blog,Integer userId);

    /**
     * 计数全部 通过用户id
     * @param userId
     * @return
     */
     Integer countByUserId(Integer userId);

    /**
     * 计数通过博客
     * @param blog
     * @return
     */
     Integer countByBlog(Blog blog);
    /**
     * 查询所有带分页 的数据
     */
    @Query(value = "SELECT * FROM collect_blog WHERE user_id =?1 ORDER BY collect_blog_time DESC limit ?2,?3",nativeQuery = true)
    List<CollectBlog> fingPageByUserId(Integer user,Integer startIndex,Integer pageSize);

    /**
     * 查询10条收藏最高的博客 TODO 目前不知道怎么吧返回值映射为其他的类型 只能用CollectBlog  否则会炸 就多查几次来解决吧
     */
    @Query(value = "SELECT collect_blog_id,blog_id,user_id,collect_blog_time,COUNT(collect_blog_id) AS collect_num FROM collect_blog GROUP BY blog_id ORDER BY COUNT(collect_blog_id) DESC,collect_blog_time DESC",nativeQuery = true)
    List<CollectBlog> findFirst10ByCollectNum();


}
