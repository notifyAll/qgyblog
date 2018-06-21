package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserMistake implements Serializable {
    @Id
    @GeneratedValue
    private Integer userMistakeId;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "all_class_id")
    private AllClass allClass;

    private Date userMistakeCreateTime;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "admin_id")
    private Admin admin;


    public UserMistake() {
    }


    public Integer getUserMistakeId() {
        return userMistakeId;
    }

    public void setUserMistakeId(Integer userMistakeId) {
        this.userMistakeId = userMistakeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AllClass getAllClass() {
        return allClass;
    }

    public void setAllClass(AllClass allClass) {
        this.allClass = allClass;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Date getUserMistakeCreateTime() {
        return userMistakeCreateTime;
    }

    public void setUserMistakeCreateTime(Date userMistakeCreateTime) {
        this.userMistakeCreateTime = userMistakeCreateTime;
    }


}
