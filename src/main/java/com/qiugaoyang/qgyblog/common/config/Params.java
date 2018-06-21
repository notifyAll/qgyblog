package com.qiugaoyang.qgyblog.common.config;

import java.util.ArrayList;
import java.util.List;

public interface Params {

    //    redis 中用户 登录部分 USER_LOGIN_KEY_
    String USER_lOGIN_KEY = "USER_LOGIN_KEY_"; //用户登录信息的key组成部分
    String USER_KEY = "userKey"; //用户登录map中的key 名
    String USER_TOKEN = "userToken";
    Long USER_KEY_EXPIRE = 1L;  //用户登录token 有效时间 天 每次操作都会刷新token
    String USER_SESSION_LOGIN_KEY = "userLoginInfo"; //用户登录存在session中的信息
    //    redis 中admin 登录部分 ADMIN_LOGIN_KEY_
    String ADMIN_lOGIN_KEY = "ADMIN_LOGIN_KEY_"; //admin登录信息的key组成部分
    String ADMIN_KEY = "adminKey"; //admin登录map中的key 名
    String ADMIN_TOKEN = "adminToken";
    Long ADMIN_KEY_EXPIRE = 1L;  //admin登录token 有效时间 1天 每次操作都会刷新token
    String ADMIN_SESSION_LOGIN_KEY = "adminLoginInfo"; //用户登录存在session中的信息


    //    redis 首页部分
    String INDEX_NEW_BLOG = "userIndexNewBlog"; // 首页最新的blog
    String INDEX_HOT_BLOG = "userIndexHotBlog";
    String INDEX_TUI_JIAN_BLOG="userIndexTuiJianBlog";
    String INDEX_REN_QI_USER="userIndexRenQiUser";
    Long INDEX_REDIS_OUT_TIME = 1L;// redis 数据超时时间  单位分钟
    Integer INDEX_BLOG_PAGE_SIZE = 10;
//
//    String INDEX_

    //    博客
    String BLOG_DETAIL = "blogDetail";
    String PAGE_BLOGS = "pageBlogs";
    String BLOG_USER = "blogUser";

    String THUMB_BLOG_NUM_RESULT="thumbBlogNumResult"; //点赞数量 和是否点赞
    String COLLECT_BLOG_NUM_RESULT="collectBlogNumResult";// 浏览数  和是否收藏

//    浏览量 博客
    String REDIS_PAGEVIEWS_BLOG="REDIS_PAGEVIEWS_BLOG";
//    浏览量
    String REDIS_PAGEVIEWS_HOME="REDIS_PAGEVIEWS_HOME";
    String REDIS_PAGEVIEWS_HOME_HOME="HOME";
    //评论列表
    String PAGE_FIRST_COMMENTS ="pageFirstComments"; //博客详情页的评论列表
    Integer PAGE_FIRST_COMMENTS_SIZE=10; // 博客详情页的评论列表每页限制数


    //    数据库中class_mappering
    String CLASS_MAPPERING_BLOG = "blog";
    String CLASS_MAPPERING_USER_MISTAKE = "user_mistake";


    // 图片保存位置
    String USER_IMG_PATH = "C://qgyblogimg//";

    //    分页值
    Integer USER_BLOGS_PAGE_SIZEE = 10;


//    状态部分
//    ArrayList<Integer> user_state=new ArrayList(){1};
}
