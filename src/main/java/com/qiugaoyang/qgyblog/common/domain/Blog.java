package com.qiugaoyang.qgyblog.common.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Blog implements Serializable{
    @Id
    @GeneratedValue
    private Integer blogId;

    private String blogTitle;

    private String blogDesc;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    private String blogText;


//    @Column(name = "blog_create_time")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date blogCreateTime;

    private Date blogUpdateTime;

    private Integer blogState;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "all_class_id")
    private AllClass allClass;


    public Blog() {
    }

    public Blog(String blogTitle, String blogDesc, User userId, String blogText, Date blogCreateTime, Date blogUpdateTime, Integer blogState, AllClass allClassId) {
        this.blogTitle = blogTitle;
        this.blogDesc = blogDesc;
        this.user = userId;
        this.blogText = blogText;
        this.blogCreateTime = blogCreateTime;
        this.blogUpdateTime = blogUpdateTime;
        this.blogState = blogState;
        this.allClass = allClassId;
    }

    public Integer getBlogId() {
        return blogId;
    }

    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogDesc() {
        return blogDesc;
    }

    public void setBlogDesc(String blogDesc) {
        this.blogDesc = blogDesc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBlogText() {
        return blogText;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }

    public Date getBlogCreateTime() {
        return blogCreateTime;
    }

    public void setBlogCreateTime(Date blogCreateTime) {
        this.blogCreateTime = blogCreateTime;
    }

    public Date getBlogUpdateTime() {
        return blogUpdateTime;
    }

    public void setBlogUpdateTime(Date blogUpdateTime) {
        this.blogUpdateTime = blogUpdateTime;
    }

    public Integer getBlogState() {
        return blogState;
    }

    public void setBlogState(Integer blogState) {
        this.blogState = blogState;
    }

    public AllClass getAllClass() {
        return allClass;
    }

    public void setAllClass(AllClass allClass) {
        this.allClass = allClass;
    }
}
