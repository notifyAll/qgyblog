package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
public class AllClass implements Serializable {
    @Id
    @GeneratedValue
    private Integer allClassId;

    private String allClassName;

    private String allClassDesc;

    private Integer allClassState;

    private Date allClassCreateTime;

    private Date allClassUpdateTime;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "class_mapping_id")
    private ClassMapping classMapping;

    public AllClass() {
    }



    public Integer getAllClassId() {
        return allClassId;
    }

    public void setAllClassId(Integer allClassId) {
        this.allClassId = allClassId;
    }

    public String getAllClassName() {
        return allClassName;
    }

    public void setAllClassName(String allClassName) {
        this.allClassName = allClassName;
    }

    public String getAllClassDesc() {
        return allClassDesc;
    }

    public void setAllClassDesc(String allClassDesc) {
        this.allClassDesc = allClassDesc;
    }

    public Integer getAllClassState() {
        return allClassState;
    }

    public void setAllClassState(Integer allClassState) {
        this.allClassState = allClassState;
    }

    public Date getAllClassCreateTime() {
        return allClassCreateTime;
    }

    public void setAllClassCreateTime(Date allClassCreateTime) {
        this.allClassCreateTime = allClassCreateTime;
    }

    public Date getAllClassUpdateTime() {
        return allClassUpdateTime;
    }

    public void setAllClassUpdateTime(Date allClassUpdateTime) {
        this.allClassUpdateTime = allClassUpdateTime;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public ClassMapping getClassMapping() {
        return classMapping;
    }

    public void setClassMapping(ClassMapping classMapping) {
        this.classMapping = classMapping;
    }
}
