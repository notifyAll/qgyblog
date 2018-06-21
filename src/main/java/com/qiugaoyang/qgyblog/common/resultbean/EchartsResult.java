package com.qiugaoyang.qgyblog.common.resultbean;

import java.util.List;
/**
 * 报表的数据
 */
public class EchartsResult {
   private List categories; //横坐标
   private List data; //纵坐标

    public List getCategories() {
        return categories;
    }

    public void setCategories(List categories) {
        this.categories = categories;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
