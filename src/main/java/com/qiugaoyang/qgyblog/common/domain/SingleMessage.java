package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//系统对单人的消息通知
@Entity
public class SingleMessage implements Serializable {
    @Id
    @GeneratedValue
    private Integer singleMessageId; //
    private Integer userId;// 被通知的用户
    private String singleMessageDesc;// 返回信息
    private Integer singleMessageState;//消息状态 0删除 1普通消息 2警告
    private String singleMessageUrl;// 链接 当然也可以在返回信息中 写入a标签'
    private Integer singleMessageRead;// 用户是否已读 0未读 1已读

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "admin_id")
    private Admin admin;// 发送消息的管理员
    private Date singleMessageTime;// 发送时间

    public Integer getSingleMessageId() {
        return singleMessageId;
    }

    public void setSingleMessageId(Integer singleMessageId) {
        this.singleMessageId = singleMessageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSingleMessageDesc() {
        return singleMessageDesc;
    }

    public void setSingleMessageDesc(String singleMessageDesc) {
        this.singleMessageDesc = singleMessageDesc;
    }

    public Integer getSingleMessageState() {
        return singleMessageState;
    }

    public void setSingleMessageState(Integer singleMessageState) {
        this.singleMessageState = singleMessageState;
    }

    public String getSingleMessageUrl() {
        return singleMessageUrl;
    }

    public void setSingleMessageUrl(String singleMessageUrl) {
        this.singleMessageUrl = singleMessageUrl;
    }

    public Integer getSingleMessageRead() {
        return singleMessageRead;
    }

    public void setSingleMessageRead(Integer singleMessageRead) {
        this.singleMessageRead = singleMessageRead;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Date getSingleMessageTime() {
        return singleMessageTime;
    }

    public void setSingleMessageTime(Date singleMessageTime) {
        this.singleMessageTime = singleMessageTime;
    }

    //
}
