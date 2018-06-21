package com.qiugaoyang.qgyblog.common.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//收藏的博客 一个收藏甲中不能重复添加同一个博客
@Entity
public class CollectBlog implements Serializable {
    @Id
    @GeneratedValue
    private Integer collectBlogId; // 收藏id
//    @ManyToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "collect_page_id")
//    private CollectPage collectPage;// 收藏夹id

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "blog_id")
    private Blog blog;// 博客id

    private Integer userId; //谁收藏的

    private Date collectBlogTime;  //创建时间

    public Integer getCollectBlogId() {
        return collectBlogId;
    }

    public void setCollectBlogId(Integer collectBlogId) {
        this.collectBlogId = collectBlogId;
    }



    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCollectBlogTime() {
        return collectBlogTime;
    }

    public void setCollectBlogTime(Date collectBlogTime) {
        this.collectBlogTime = collectBlogTime;
    }
}
