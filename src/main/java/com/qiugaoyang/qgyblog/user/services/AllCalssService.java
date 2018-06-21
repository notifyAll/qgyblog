package com.qiugaoyang.qgyblog.user.services;

import com.qiugaoyang.qgyblog.common.domain.AllClass;

import java.util.List;

/**
 * 类目
 */
public interface AllCalssService {
    /**
     * 查询所有可用的类目
     * @return 可用的类目
     */
    List<AllClass> findAllBlogClass();
}
