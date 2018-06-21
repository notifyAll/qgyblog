package com.qiugaoyang.qgyblog.common.resultbean;

import java.util.ArrayList;

public class ImgPathResult {
//    错误代码

    private Integer errno;
//    图片的地址

    private ArrayList<String> data;

    public ImgPathResult(Integer errno, ArrayList<String> data) {
        this.errno = errno;
        this.data = data;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
