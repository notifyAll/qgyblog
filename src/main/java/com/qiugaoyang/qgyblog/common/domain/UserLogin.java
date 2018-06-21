package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class UserLogin implements Serializable {
    @Id
    @GeneratedValue
    private Integer userLoginId;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

    private String userLoginIp;

    private Date userLoginTime;


    public UserLogin() {
    }



    public Integer getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Integer userLoginId) {
        this.userLoginId = userLoginId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserLoginIp() {
        return userLoginIp;
    }

    public void setUserLoginIp(String userLoginIp) {
        this.userLoginIp = userLoginIp;
    }

    public Date getUserLoginTime() {
        return userLoginTime;
    }

    public void setUserLoginTime(Date userLoginTime) {
        this.userLoginTime = userLoginTime;
    }
}
