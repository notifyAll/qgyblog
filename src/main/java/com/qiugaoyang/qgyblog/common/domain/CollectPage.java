package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

//收藏夹
@Entity
public class CollectPage implements Serializable {

    @Id
    @GeneratedValue
    private Integer collectPageId;//收藏夹id

    private String collectPageName;//收藏甲名字

    private Integer userId;//用户id

    public Integer getCollectPageId() {
        return collectPageId;
    }

    public void setCollectPageId(Integer collectPageId) {
        this.collectPageId = collectPageId;
    }

    public String getCollectPageName() {
        return collectPageName;
    }

    public void setCollectPageName(String collectPageName) {
        this.collectPageName = collectPageName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
