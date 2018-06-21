package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//一级评论
@Entity
@Table(name = "first_comment")
public class FirstComment implements Serializable {
    @Id
    @GeneratedValue
    private Integer firstCommentId;// 一级评论id
    private String firstCommentDesc;// 评论内容
    private Date firstCommentTime;// 评论时间
    private Integer firstCommentState;// 评论状态 0不可用（删除） 1可用

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;// 评论人 需要附带评论人的信息

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "blog_id")
    private Blog blog; //被评论的blog
    private Integer firstCommentRead;// 该评论 作者是否已读 0未读 1已读

    public Integer getFirstCommentId() {
        return firstCommentId;
    }

    public void setFirstCommentId(Integer firstCommentId) {
        this.firstCommentId = firstCommentId;
    }

    public String getFirstCommentDesc() {
        return firstCommentDesc;
    }

    public void setFirstCommentDesc(String firstCommentDesc) {
        this.firstCommentDesc = firstCommentDesc;
    }

    public Date getFirstCommentTime() {
        return firstCommentTime;
    }

    public void setFirstCommentTime(Date firstCommentTime) {
        this.firstCommentTime = firstCommentTime;
    }

    public Integer getFirstCommentState() {
        return firstCommentState;
    }

    public void setFirstCommentState(Integer firstCommentState) {
        this.firstCommentState = firstCommentState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Integer getFirstCommentRead() {
        return firstCommentRead;
    }

    public void setFirstCommentRead(Integer firstCommentRead) {
        this.firstCommentRead = firstCommentRead;
    }
}


