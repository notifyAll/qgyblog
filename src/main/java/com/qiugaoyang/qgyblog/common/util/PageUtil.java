package com.qiugaoyang.qgyblog.common.util;

import java.util.Collection;


/**
 * Created by vivid on 2017/3/15.
 * 分页类
 */
public class PageUtil<T>{
    //    总记录数
    private int size;
    //    每页的限制数
    private int pageSize;
    //    当前页数 从1开始
    private int page;
    //    总可分页数 只读
    private int maxpage;

    //    分页开始的索引 页码-1*限制 0 10 20.... 只读
    private int startIndex;



    //    附带传送打包数据的功能
    private T pageDate;
    /**
     * 初始化
     * @param size 数据总长度
     * @param pageSize 每页的长度
     */
    public PageUtil(int size, int pageSize) {
        this.size = size;
        this.pageSize = pageSize;

        refresh();
    }

    private void refresh(){
        //        对其他参数进行初始化 如果有余则最大页码加一
        if (size%pageSize!=0){
            maxpage =size/pageSize +1;
        }else {
            maxpage=size/pageSize;
        }
//        页码为小于0时
        if (page<=0) page=1;
//        大于总页数时
        if (page>maxpage) page=maxpage;
//        开始的索引为0
        startIndex=(page-1)*pageSize;
        if (startIndex<0) startIndex=0;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        refresh();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        refresh();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        //        页码为小于0时
        if (page<=0) page=1;
//        大于总页数时
        if (page>maxpage) page=maxpage;
        this.page = page;
        startIndex=(this.page-1)*pageSize;
        if (startIndex<0) startIndex=0;
    }

    public int getMaxpage() {
        return maxpage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public T getPageDate() {
        return pageDate;
    }

    public void setPageDate(T pageDate) {
        this.pageDate = pageDate;
    }
}
