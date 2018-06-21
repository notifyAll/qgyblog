package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

//点赞表 点赞blog 的主键
public class ThumbBlogPK implements Serializable {

    private Integer userId;

    private Integer blogId;

    public ThumbBlogPK() {
    }

    public ThumbBlogPK(Integer userId, Integer blogId) {
        this.userId = userId;
        this.blogId = blogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }
}
