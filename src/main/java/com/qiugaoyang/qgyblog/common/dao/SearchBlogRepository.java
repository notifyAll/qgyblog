package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.Blog;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SearchBlogRepository extends PagingAndSortingRepository<Blog,Integer> {
}
