package com.qiugaoyang.qgyblog.common.dao;


import com.qiugaoyang.qgyblog.common.domain.PageviewsHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageviewsHomeRepository extends JpaRepository<PageviewsHome,Integer>{

    /**
     *  查询最近7天首页的访问量
     * @return
     */
    @Modifying
    @Query(value = "SELECT * FROM pageviews_home ORDER BY pageviews_home_time DESC LEFT 0.6",nativeQuery = true)
    List<PageviewsHome> get7DayPageviewHome();
}
