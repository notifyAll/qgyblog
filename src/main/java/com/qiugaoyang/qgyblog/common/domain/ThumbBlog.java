package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

//点赞表 点赞blog
@Entity
@Table(name = "thumb_blog")
@IdClass(com.qiugaoyang.qgyblog.common.domain.ThumbBlogPK.class)
public class ThumbBlog implements Serializable {
    @Id
    private Integer userId;
    @Id
    private Integer blogId;

    public ThumbBlog() {
    }

    public ThumbBlog(Integer userId, Integer blogId) {
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
