package com.qiugaoyang.qgyblog.common.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

//一级评论的点赞的主键

public class ThumbCommentPK implements Serializable {

    private Integer userId;

    private Integer firstCommentId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFirstCommentId() {
        return firstCommentId;
    }

    public void setFirstCommentId(Integer firstCommentId) {
        this.firstCommentId = firstCommentId;
    }
}
