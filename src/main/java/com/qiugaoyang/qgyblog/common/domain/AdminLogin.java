package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Entity
public class AdminLogin implements Serializable {
    @Id
    @GeneratedValue
    private Integer adminLoginId;

    private Integer adminId;

    private String adminLoginIp;

    private Date adminLoginTime;

    public AdminLogin() {
    }

    public AdminLogin(Integer adminId, String adminLoginIp, Date adminLoginTime) {
        this.adminId = adminId;
        this.adminLoginIp = adminLoginIp;
        this.adminLoginTime = adminLoginTime;
    }

    public Integer getAdminLoginId() {
        return adminLoginId;
    }

    public void setAdminLoginId(Integer adminLoginId) {
        this.adminLoginId = adminLoginId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminLoginIp() {
        return adminLoginIp;
    }

    public void setAdminLoginIp(String adminLoginIp) {
        this.adminLoginIp = adminLoginIp;
    }

    public Date getAdminLoginTime() {
        return adminLoginTime;
    }

    public void setAdminLoginTime(Date adminLoginTime) {
        this.adminLoginTime = adminLoginTime;
    }
}
