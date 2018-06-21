package com.qiugaoyang.qgyblog.common.resultbean;

/**
 * 收藏量
 * 和是否已经收藏
 */
public class CollectBlogNumResult {
    private Integer collectBlogNum=0;//收藏数
    private boolean collect=false; //是否我已经收藏

    public Integer getCollectBlogNum() {
        return collectBlogNum;
    }

    public void setCollectBlogNum(Integer collectBlogNum) {
        this.collectBlogNum = collectBlogNum;
    }

    public boolean getCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
