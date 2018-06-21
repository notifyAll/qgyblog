package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.dao.BlogRepository;
import com.qiugaoyang.qgyblog.common.dao.PageviewsBlogRepository;
import com.qiugaoyang.qgyblog.common.dao.PageviewsHomeRepository;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.PageviewsBlog;
import com.qiugaoyang.qgyblog.common.domain.PageviewsHome;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;
import com.qiugaoyang.qgyblog.common.util.DataUtil;
import com.qiugaoyang.qgyblog.user.services.PageviewsService;
import freemarker.template.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 有关网站浏览量
 */
@Service
public class PageviewsServiceImpl implements PageviewsService {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    RedisTemplate<String, Object> redisTemplate; //redis

    @Resource
    BlogRepository blogRepository;

    @Resource
    PageviewsBlogRepository pageviewsBlogRepository;

    @Resource
    PageviewsHomeRepository pageviewsHomeRepository;

    /**
     * 添加一条该博客的浏览量
     * 存入有序的zSet中 便于统计
     *
     * @param blogId 博客id
     */
    @Override
    public void addBlogNum(Integer blogId) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add(Params.REDIS_PAGEVIEWS_BLOG,blogId,);
        Double aDouble = zSetOperations.incrementScore(Params.REDIS_PAGEVIEWS_BLOG, "" + blogId, 1);//分值加一
    }

    /**
     * 获取某个博客的当日访问量
      * @param blogId 博客id
     * @return
     */
    public Integer getBlogNum(Integer blogId) {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Integer aDouble = zSetOperations.score(Params.REDIS_PAGEVIEWS_BLOG, "" + blogId).intValue();//获取分值
        if (aDouble==null){
            return 0;
        }
        return aDouble;
    }

    /**
     * 将redis中的博客访问量保存到数据库中 并清除redis中的值
     */
    @Transactional
    @Override
    public void savePageviewBlog() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set range = zSetOperations.reverseRange(Params.REDIS_PAGEVIEWS_BLOG, 0, -1);
        List<PageviewsBlog> pageviewsBlogList = new ArrayList<>();
//        填充数据
        Iterator<String> iterator = range.iterator();
        while (iterator.hasNext()) {
            PageviewsBlog pageviewsBlog = new PageviewsBlog();
            String blogId = iterator.next();
            pageviewsBlog.setBlogId(Integer.parseInt(blogId));
//            获取分数
            Integer score = zSetOperations.score(Params.REDIS_PAGEVIEWS_BLOG, blogId).intValue();
            pageviewsBlog.setPageviewsBlogNum(score);
//            将分数清0
            zSetOperations.add(Params.REDIS_PAGEVIEWS_BLOG, blogId, 0);
//            设置日期
            pageviewsBlog.setPageviewsBlogTime(new Date());

            pageviewsBlogList.add(pageviewsBlog);
        }
//向数据库保存数据
        List<PageviewsBlog> save = pageviewsBlogRepository.save(pageviewsBlogList);
    }


    /**
     * 获取前10条浏览量最高的blog 里面存的是博客id 和浏览量
     * Iterator<ZSetOperations.TypedTuple<Object>> iterator = range.iterator(); 获取数据时迭代
     *
     * @return
     */
    @Override
    public List<BlogResult> getFirst10PageviewsBlog() {
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        Set range = zSetOperations.reverseRange(Params.REDIS_PAGEVIEWS_BLOG, 0, 20);

        List<BlogResult> blogResults = new ArrayList<>();
//        填充数据
        Iterator<String> iterator = range.iterator();
        while (iterator.hasNext()) {
            BlogResult blogResult = new BlogResult();
            String blogId = iterator.next();
            Blog blog = blogRepository.findOne(Integer.parseInt(blogId));
            if (blog.getBlogState()!=1){
                continue;
            }
            blogResult.setBlog(blog);
//            获取分数
            Integer score = zSetOperations.score(Params.REDIS_PAGEVIEWS_BLOG, blogId).intValue();
            blogResult.setPageviewsBlogNum(score);
            blogResults.add(blogResult);
        }
        if (blogResults.size()<=10){
            return blogResults;
        }
        return blogResults.subList(0,10);
    }

    /**
     * 首页访问量
     */
    @Override
    public void addHomeNum() {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
//       首页 访问量加1
        zSetOperations.incrementScore(Params.REDIS_PAGEVIEWS_HOME, Params.REDIS_PAGEVIEWS_HOME_HOME, 1);
    }

    /**
     * 保存每日的首页访问记录
     */
    @Override
    @Transactional
    public void savePageviewHome() {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        PageviewsHome pageviewsHome = new PageviewsHome();

        Integer homeNum = zSetOperations.score(Params.REDIS_PAGEVIEWS_HOME, Params.REDIS_PAGEVIEWS_HOME_HOME).intValue();

        pageviewsHome.setPageviewsHomeNum(homeNum);
        pageviewsHome.setPageviewsHomeTime(new Date());

//        redis清0
        zSetOperations.add(Params.REDIS_PAGEVIEWS_HOME, Params.REDIS_PAGEVIEWS_HOME_HOME, 0);
//       保存数据
        pageviewsHomeRepository.save(pageviewsHome);
    }


    /**
     * 获取首页访问量
     *
     * @return 首页访问量
     */
    @Override
    public Integer getHomeNum() {
        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        Integer homeNum = zSetOperations.score(Params.REDIS_PAGEVIEWS_HOME, Params.REDIS_PAGEVIEWS_HOME_HOME).intValue();
        return homeNum;
    }

    /**
     * 获取首页的7日访问量
     *
     * @return
     */
    @Override
    public List<PageviewsHome> get7DayPageviewHome() {
//        获取今天的访问数据
        Integer homeNum = getHomeNum();
        PageviewsHome nowDayHome = new PageviewsHome();
        nowDayHome.setPageviewsHomeNum(homeNum);
//        设置时间 不带时分秒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = simpleDateFormat.getCalendar();
        nowDayHome.setPageviewsHomeTime(calendar.getTime());

//        获取前6天的数据 最新的用添加到头 如果天数不足 自动填充
        List<PageviewsHome> list6Day = pageviewsHomeRepository.get7DayPageviewHome();
        List<PageviewsHome> list7Day = new ArrayList<>(); //返回的集合
//       设置当天
        list7Day.add(nowDayHome);
        //添加后6天
        list7Day.addAll(list6Day);

        int size=list7Day.size();
        //设置时间
        if (size < 7) {
            calendar.setTime(list7Day.get(list7Day.size() - 1).getPageviewsHomeTime());
        }
//判断是否已经满7天 不足添0
        for (int i = 0; i < 7 - size; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
            PageviewsHome pageviewsHome = new PageviewsHome();
            pageviewsHome.setPageviewsHomeNum(0);
            pageviewsHome.setPageviewsHomeTime(calendar.getTime());
            list7Day.add(pageviewsHome);
        }
//反转集合
        Collections.reverse(list7Day);
        return list7Day;
    }

    /**
     * 获取博客的7日访问量
     *
     * @param blogId 博客的id
     * @return
     */
    @Override
    public List<PageviewsBlog> get7DayPageviewBlog(Integer blogId) {
        //        设置时间 不带时分秒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = simpleDateFormat.getCalendar();
        calendar.setTime(new Date());
//     获取今日的访问量
        PageviewsBlog nowDayPageviewsBlog=new PageviewsBlog();
        nowDayPageviewsBlog.setPageviewsBlogNum(getBlogNum(blogId));
        nowDayPageviewsBlog.setPageviewsBlogTime(calendar.getTime());

//        获取数据库中的前6天数据
        List<PageviewsBlog> list6Day=pageviewsBlogRepository.findFirst6ByBlogIdOrderByPageviewsBlogTimeDesc(blogId);
        List<PageviewsBlog> list7Day=new ArrayList<>();
        list7Day.add(nowDayPageviewsBlog);
        list7Day.addAll(list6Day);

        int size=list7Day.size();

        if (size<7){
            calendar.setTime(list7Day.get(list7Day.size() - 1).getPageviewsBlogTime());
        }
        //判断是否已经满7天 不足添0

        for (int i = 0; i < 7 - size; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
            PageviewsBlog pageviewsBlog=new PageviewsBlog();
            pageviewsBlog.setPageviewsBlogNum(0);
            pageviewsBlog.setPageviewsBlogTime(calendar.getTime());
            list7Day.add(pageviewsBlog);
        }
        //反转集合
        Collections.reverse(list7Day);

        return list7Day;
    }
}
