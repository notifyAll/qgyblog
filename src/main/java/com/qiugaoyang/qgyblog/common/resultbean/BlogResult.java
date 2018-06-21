package com.qiugaoyang.qgyblog.common.resultbean;

import com.qiugaoyang.qgyblog.common.domain.Blog;

import java.io.Serializable;

public class BlogResult implements Serializable {
    private Blog blog;//博客

    Integer pageviewsBlogNum; //浏览量

    Integer thumbBlogNum; // 点赞数

    Integer firstCommentNum; //评论数

    Integer collectNum; //收藏数

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Integer getPageviewsBlogNum() {
        return pageviewsBlogNum;
    }

    public void setPageviewsBlogNum(Integer pageviewsBlogNum) {
        this.pageviewsBlogNum = pageviewsBlogNum;
    }

    public Integer getThumbBlogNum() {
        return thumbBlogNum;
    }

    public void setThumbBlogNum(Integer thumbBlogNum) {
        this.thumbBlogNum = thumbBlogNum;
    }

    public Integer getFirstCommentNum() {
        return firstCommentNum;
    }

    public void setFirstCommentNum(Integer firstCommentNum) {
        this.firstCommentNum = firstCommentNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }
}
