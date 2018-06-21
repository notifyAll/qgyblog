package com.qiugaoyang.qgyblog.admin.services.impl;

import com.qiugaoyang.qgyblog.admin.services.AdminBlogService;
import com.qiugaoyang.qgyblog.common.dao.BlogRepository;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.util.PageUtil;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminBlogServiceImpl implements AdminBlogService {
    @Resource
    private BlogRepository blogRepository;
    /**
     * 通过条件查询博客
     *
     * @param blog 条件
     * @param page 页码
     * @return
     */
    @Override
    public PageUtil<List<Blog>> searchBlogs(Blog blog, Integer page) {
//        String param1 = "%" + param + "%"; //模糊查询需要带%
//它的页码从0开始
        Pageable pageable = new PageRequest(page - 1, 10);

        Specification<Blog> specification=new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Path blogId = root.get("blogId");
                Path blogTitle = root.get("blogTitle");
                Path blogDesc = root.get("blogDesc");
                Path blogState=root.get("blogState");
                List<Predicate> predicates=new ArrayList<>();
                if (blog.getBlogId()!=null&&blog.getBlogId()>0){
                    Predicate equal = criteriaBuilder.equal(blogId, blog.getBlogId());
                    predicates.add(equal);
                }
                if (blog.getBlogTitle()!=null&&!blog.getBlogTitle().trim().equals("")){
                    Predicate like = criteriaBuilder.like(blogTitle, "%" + blog.getBlogTitle() + "%");
                    predicates.add(like);
                }

                if (blog.getBlogDesc()!=null&&!blog.getBlogDesc().trim().equals("")){
                    Predicate like = criteriaBuilder.like(blogDesc, "%" + blog.getBlogDesc() + "%");
                    predicates.add(like);
                }
                if (blog.getBlogState()!=null){
                    Predicate equal = criteriaBuilder.equal(blogState, blog.getBlogState());
                    predicates.add(equal);
                }
                Predicate[] p = new Predicate[predicates.size()];
                return criteriaBuilder.and(predicates.toArray(p));
            }
        };
        Page<Blog> page1 = blogRepository.findAll(specification, pageable);
        Long l = page1.getTotalElements(); //总记录数
        PageUtil<List<Blog>> pageUtil = new PageUtil(l.intValue(), page1.getSize());
        pageUtil.setPage(page);
        pageUtil.setPageDate(page1.getContent());

        return pageUtil;
    }

    /**
     * 通过id查询博客
     *
     * @param blogId 博客id
     * @return
     */
    @Override
    public Blog findOne(Integer blogId) {
        if (blogId==null||blogId<0) return null;

        Blog one = blogRepository.findOne(blogId);
        return one;
    }
}
