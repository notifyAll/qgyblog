package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.PageviewsBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageviewsBlogRepository extends JpaRepository<PageviewsBlog,Integer>{
    /**
     * 获取指定博客的 最新6天的访问量
     * @param blogId
     * @return
     */
    List<PageviewsBlog> findFirst6ByBlogIdOrderByPageviewsBlogTimeDesc(Integer blogId);

}
