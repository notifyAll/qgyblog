package com.qiugaoyang.qgyblog.common.resultbean;

//点赞博客
public class ThumbBlogNumResult {
    private Integer thumbBlogNum=0; //总点攒数
    private Boolean thumb=false; //是否已经点赞

    public Integer getThumbBlogNum() {
        return thumbBlogNum;
    }

    public void setThumbBlogNum(Integer thumbBlogNum) {
        this.thumbBlogNum = thumbBlogNum;
    }

    public Boolean getThumb() {
        return thumb;
    }

    public void setThumb(Boolean thumb) {
        this.thumb = thumb;
    }
}
