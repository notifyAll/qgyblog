package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Admin implements Serializable {
    @Id
    @GeneratedValue
    private Integer adminId;

    private String adminName;

    private String adminPhone;

    private String adminEmail;

    private Integer adminGrade;

    private String adminPassword;

    private Date adminCreateTime;

    private Integer adminState;

    public Admin() {
    }

    public Admin(String adminEmail, String adminPassword) {
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public Admin(String adminName, String adminPhone, String adminEmail, Integer adminGrade, String adminPassword, Date adminCreateData, Integer adminState) {
        this.adminName = adminName;
        this.adminPhone = adminPhone;
        this.adminEmail = adminEmail;
        this.adminGrade = adminGrade;
        this.adminPassword = adminPassword;
        this.adminCreateTime = adminCreateData;
        this.adminState = adminState;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Integer getAdminGrade() {
        return adminGrade;
    }

    public void setAdminGrade(Integer adminGrade) {
        this.adminGrade = adminGrade;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Date getAdminCreateTime() {
        return adminCreateTime;
    }

    public void setAdminCreateTime(Date adminCreateTime) {
        this.adminCreateTime = adminCreateTime;
    }

    public Integer getAdminState() {
        return adminState;
    }

    public void setAdminState(Integer adminState) {
        this.adminState = adminState;
    }
}
