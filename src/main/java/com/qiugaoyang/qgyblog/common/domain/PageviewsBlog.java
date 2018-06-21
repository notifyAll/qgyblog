package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

//博客的浏览量
@Entity
public class PageviewsBlog {
    @Id
    @GeneratedValue
    private Integer pageviewsBlogId;

    private Integer blogId;  //博客id

    private Date pageviewsBlogTime; //哪天的

    private Integer pageviewsBlogNum; //浏览量


    public Integer getPageviewsBlogId() {
        return pageviewsBlogId;
    }

    public void setPageviewsBlogId(Integer pageviewsBlogId) {
        this.pageviewsBlogId = pageviewsBlogId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public Date getPageviewsBlogTime() {
        return pageviewsBlogTime;
    }

    public void setPageviewsBlogTime(Date pageviewsBlogTime) {
        this.pageviewsBlogTime = pageviewsBlogTime;
    }

    public Integer getPageviewsBlogNum() {
        return pageviewsBlogNum;
    }

    public void setPageviewsBlogNum(Integer pageviewsBlogNum) {
        this.pageviewsBlogNum = pageviewsBlogNum;
    }
}
