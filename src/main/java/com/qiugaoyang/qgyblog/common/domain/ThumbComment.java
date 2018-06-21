package com.qiugaoyang.qgyblog.common.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

//一级评论的点赞
@Entity
@IdClass(com.qiugaoyang.qgyblog.common.domain.ThumbCommentPK.class)
public class ThumbComment implements Serializable {
    @Id
    private Integer userId;
    @Id
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
