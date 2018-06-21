package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 首页的每日浏览量
 */
@Entity
@Table(name = "pageviews_home")
public class PageviewsHome {
    @Id
    @GeneratedValue
    private Integer pageviewsHomeId;

    private Date pageviewsHomeTime;

    private Integer pageviewsHomeNum;

    public Integer getPageviewsHomeId() {
        return pageviewsHomeId;
    }

    public void setPageviewsHomeId(Integer pageviewsHomeId) {
        this.pageviewsHomeId = pageviewsHomeId;
    }

    public Date getPageviewsHomeTime() {
        return pageviewsHomeTime;
    }

    public void setPageviewsHomeTime(Date pageviewsHomeTime) {
        this.pageviewsHomeTime = pageviewsHomeTime;
    }

    public Integer getPageviewsHomeNum() {
        return pageviewsHomeNum;
    }

    public void setPageviewsHomeNum(Integer pageviewsHomeNum) {
        this.pageviewsHomeNum = pageviewsHomeNum;
    }
}
