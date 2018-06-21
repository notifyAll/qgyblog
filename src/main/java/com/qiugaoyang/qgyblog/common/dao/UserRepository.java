package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
//用户登录使用
    User findByUserEmailAndAndUserPassword(String userEmail,String userPassword);

    //查找用户是否已经存在
    List<User> findByUserEmailOrAndUserName(String userEmail,String userName);

    /**
     *   获取首页 人气博主 通过手下博客收藏数最多的 用户
      */
    @Query(value = "SELECT * FROM user as a " +
            "LEFT JOIN (SELECT blog.user_id,count(blog.blog_id) blog_num,sum(c.collect_num) collect_num FROM blog " +
            "LEFT JOIN (SELECT blog_id,count(collect_blog_id) collect_num FROM collect_blog  GROUP BY blog_id ) AS c " +
            "ON c.blog_id=blog.blog_id   WHERE blog_state=1 GROUP BY blog.user_id) AS b " +
            "ON b.user_id=a.user_id " +
            "WHERE user_state=1 ORDER BY b.collect_num DESC,b.blog_num DESC limit 0,10 "
            ,nativeQuery = true)
    List<User> findRenQiUser();
}
