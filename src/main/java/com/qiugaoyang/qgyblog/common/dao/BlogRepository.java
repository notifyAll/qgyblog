package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer>,JpaSpecificationExecutor<Blog>{

    //   @Query("select * from Blog ")
//    查询最新的10条blog 状态要为1 审核通过的
    List<Blog> findFirst10ByBlogStateOrderByBlogCreateTimeDesc(Integer blogState);

    //    查询最新的10条blog 状态要为1 审核通过的
    List<Blog> findFirst10ByBlogStateOrderByBlogUpdateTimeDesc(Integer blogState);

    //    计数 通过用户 还有博客状态
    Integer countAllByUserAndBlogStateIn(User user, List<Integer> blogState);

    //    查询用户所有的blog 通过用户id 博客状态 分页
    @Modifying
//    @Query(value = "select b from Blog b where b.user.userId= ?1 and b.blogState in ?2 order by b.blogCreateTime desc limit 1,10")
    @Query(value = "select * from blog where user_id= ?1 and blog.blog_state in ?2 order by blog_update_time desc limit ?3,?4", nativeQuery = true)
    List<Blog> findBlogsByUserAndPage(Integer userId, List<Integer> blogState, Integer startIndex, Integer pageSize);

    //查询所有我的博客id 通过userid  状态要为已经审核的
    @Modifying
    @Query(value = "SELECT blog_id FROM blog WHERE user_id=?1 AND blog_state=1", nativeQuery = true)
    List<Integer> fingMyAllBlogId(Integer userId);

    @Query(value = "SELECT * FROM blog " +
            "LEFT JOIN " +
            "(SELECT blog_id,count(user_id) thumb_num FROM thumb_blog GROUP BY blog_id) as thumb " +
            "ON blog.blog_id =thumb.blog_id " +
            "LEFT JOIN " +
            "(SELECT blog_id,COUNT(collect_blog_id) collect_num FROM collect_blog GROUP BY blog_id ) as collect " +
            "ON blog.blog_id=collect.blog_id  " +
            "WHERE blog_state=1 ORDER BY (thumb.thumb_num+collect.collect_num) DESC,blog.blog_update_time DESC limit 0,10", nativeQuery = true)
    List<Blog> fingMyAllByCollectAndThumb();

    Page<Blog> findByBlogTitleLikeOrBlogDescLikeOrderByBlogUpdateTimeDesc(String param, String param1, Pageable pageable);
}
