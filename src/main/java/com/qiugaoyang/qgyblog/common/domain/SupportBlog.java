package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

//点赞
@Entity
public class SupportBlog implements Serializable {
    @Id
    @GeneratedValue
    private Integer supportBlogId; //
    private Integer userId;//
    private Integer blogId;//
    private Date supportBlogTime;//


    public Integer getSupportBlogId() {
        return supportBlogId;
    }

    public void setSupportBlogId(Integer supportBlogId) {
        this.supportBlogId = supportBlogId;
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

    public Date getSupportBlogTime() {
        return supportBlogTime;
    }

    public void setSupportBlogTime(Date supportBlogTime) {
        this.supportBlogTime = supportBlogTime;
    }
}//
