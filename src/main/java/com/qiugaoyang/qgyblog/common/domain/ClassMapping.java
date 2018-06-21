package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ClassMapping implements Serializable {
    @Id
    @GeneratedValue
    private Integer classMappingId;

    private String classMappingName;

    private String classMappingDesc;

    public ClassMapping() {
    }

    public ClassMapping(String classMappingName, String classMappingDesc) {
        this.classMappingName = classMappingName;
        this.classMappingDesc = classMappingDesc;
    }

    public Integer getClassMappingId() {
        return classMappingId;
    }

    public void setClassMappingId(Integer classMappingId) {
        this.classMappingId = classMappingId;
    }

    public String getClassMappingName() {
        return classMappingName;
    }

    public void setClassMappingName(String classMappingName) {
        this.classMappingName = classMappingName;
    }

    public String getClassMappingDesc() {
        return classMappingDesc;
    }

    public void setClassMappingDesc(String classMappingDesc) {
        this.classMappingDesc = classMappingDesc;
    }
}
