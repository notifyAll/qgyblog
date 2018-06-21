package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
//主题消息 是否已读 存在redis中 值的时效到结束时间  由于该平台主要使用邮箱 可以给一个邮箱发送功能
@Entity
public class ThemeMessage implements Serializable {
    @Id
    @GeneratedValue
    private Integer themeMessageId;//
    private String themeMessageTitle; //消息标题
    private String themeMessageDesc;// 描述
    private String themeMessageText;// 主题消息正文

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "admin_id")
    private Admin admin;// 管理员id
    private Date themeMessageCreateTime;// 创建时间
    private Date themeMessageEndTime;// 结束时间
    private Integer themeMessageState;// 0用户不可见 1推送中 当到结束时间 或者 管理员手动将其关闭 变为用户不可见
    private Integer themeMessageIsEmail;// 是否已经发送了全局邮件 0否 1 是

    public Integer getThemeMessageId() {
        return themeMessageId;
    }

    public void setThemeMessageId(Integer themeMessageId) {
        this.themeMessageId = themeMessageId;
    }

    public String getThemeMessageTitle() {
        return themeMessageTitle;
    }

    public void setThemeMessageTitle(String themeMessageTitle) {
        this.themeMessageTitle = themeMessageTitle;
    }

    public String getThemeMessageDesc() {
        return themeMessageDesc;
    }

    public void setThemeMessageDesc(String themeMessageDesc) {
        this.themeMessageDesc = themeMessageDesc;
    }

    public String getThemeMessageText() {
        return themeMessageText;
    }

    public void setThemeMessageText(String themeMessageText) {
        this.themeMessageText = themeMessageText;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Date getThemeMessageCreateTime() {
        return themeMessageCreateTime;
    }

    public void setThemeMessageCreateTime(Date themeMessageCreateTime) {
        this.themeMessageCreateTime = themeMessageCreateTime;
    }

    public Date getThemeMessageEndTime() {
        return themeMessageEndTime;
    }

    public void setThemeMessageEndTime(Date themeMessageEndTime) {
        this.themeMessageEndTime = themeMessageEndTime;
    }

    public Integer getThemeMessageState() {
        return themeMessageState;
    }

    public void setThemeMessageState(Integer themeMessageState) {
        this.themeMessageState = themeMessageState;
    }

    public Integer getThemeMessageIsEmail() {
        return themeMessageIsEmail;
    }

    public void setThemeMessageIsEmail(Integer themeMessageIsEmail) {
        this.themeMessageIsEmail = themeMessageIsEmail;
    }
}
