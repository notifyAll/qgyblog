package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.common.config.Params;
import com.qiugaoyang.qgyblog.common.dao.*;
import com.qiugaoyang.qgyblog.common.domain.AllClass;
import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.CollectBlog;
import com.qiugaoyang.qgyblog.common.domain.User;
import com.qiugaoyang.qgyblog.common.enums.ResultEnums;
import com.qiugaoyang.qgyblog.common.exception.IllegalOperationException;
import com.qiugaoyang.qgyblog.common.exception.ParamException;
import com.qiugaoyang.qgyblog.common.exception.UserStateException;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;
import com.qiugaoyang.qgyblog.common.util.PageUtil;
import com.qiugaoyang.qgyblog.user.services.BlogService;
import com.qiugaoyang.qgyblog.user.services.PageviewsService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource(name = "redisTemplate")
    RedisTemplate<String, BlogResult> redisTemplateBlogResult;
    @Resource
    RedisTemplate<String, Blog> redisTemplate;

    @Resource
    RedisConnectionFactory redisConnectionFactory;

    @Resource
    BlogRepository blogRepository;

    @Resource
    AllClassRepository allClassRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    PageviewsService pageviewsService;

    @Resource
    CollectBlogRepository collectBlogRepository;

    @Resource
    ThumbBlogRepository thumbBlogRepository;

    @Resource
    SearchBlogRepository searchBlogRepository;

    @Override
    public void getIndexBlog() {

    }

    /**
     * 获取首页10条最新的数据
     * 首先去redis中获取（时效5分钟） 没有则查询数据库 查询到的结果 放到redis中
     *
     * @return
     */
    @Override
    public List<Blog> getNewIndexBlog() {
        List<Blog> blogs = redisTemplate.opsForList().range(Params.INDEX_NEW_BLOG, 0L, -1L);
        if (blogs == null || blogs.size() == 0) {
//            去数据库查询
            blogs = blogRepository.findFirst10ByBlogStateOrderByBlogUpdateTimeDesc(1);
            if(blogs!=null&&blogs.size()>0){
                //            存入redis中
                redisTemplate.opsForList().rightPushAll(Params.INDEX_NEW_BLOG, blogs);
//            设置超时时间
                redisTemplate.expire(Params.INDEX_NEW_BLOG, Params.INDEX_REDIS_OUT_TIME, TimeUnit.MINUTES);
            }
        }
        return blogs;
    }

    @Override
    @Transactional
    public Blog addBlog(Blog blog) {
        //        类型
        AllClass allClass = allClassRepository.findOne(blog.getAllClass().getAllClassId());
//        校验是否为blog 类型 如果不是禁止保存
// 状态 0不可使用 1可使用的类型 已经设置的无关系可照常使用 只影响新增和修改
        if (!allClass.getClassMapping().getClassMappingName().equals(Params.CLASS_MAPPERING_BLOG) || allClass.getAllClassState() != 1
                ) {
//            非法操作
            throw new IllegalOperationException(ResultEnums.ILLEGAL_OPERATION);
        }

        blog.setAllClass(allClass);
//        状态 为待审核状态
        blog.setBlogState(0);
        blog.setBlogCreateTime(new Date());
        blog.setBlogUpdateTime(new Date());

        Blog save = blogRepository.save(blog);
        return save;
    }

    //    通过id 查询blog
    @Override
    public Blog fingBlogById(Integer blogId, User user) {
//     空异常
        if (blogId == null || blogId < 0) return null;
//        查询
        Blog one = blogRepository.findOne(blogId);
        if (one == null) return null;
//        校验数据

//         博主是否状态异常
        if (one.getUser().getUserState() == 3 || one.getUser().getUserState() == 0)
            throw new UserStateException(ResultEnums.USER_START_ERROR);

        //        是否为博主
        if (user == null || user.getUserId() == null || one.getUser().getUserId() != user.getUserId()) {
//          如果是非博主
//            博客未审核 则不可见
            if (one.getBlogState() != 1)
                return null;
//            博主异常也不可见
            if (one.getUser().getUserState() != 1) return null;
        }
        return one;
    }

    /**
     * 修改blog
     * 确定是否为blog 本人 且博主状态正常 博客状态正常
     * 用户状态 0未激活 1正常 2禁言
     * blog状态 -1删除 0待审核 1审核通过 2提交审核
     *
     * @param blog
     * @return
     */
    @Override
    @Transactional
    public Blog updBlog(Blog blog) {
        Blog dataBlog = blogRepository.findOne(blog.getBlogId());//数据库中的数据
        if (dataBlog == null) {
            return null;
        }
        //        校验用户
        checkBlogAuth(blog.getUser(), dataBlog.getUser());

        if (dataBlog.getBlogState() == -1) {
//            博客已删除
            return null;
        }
        //        类型
        AllClass allClass = allClassRepository.findOne(blog.getAllClass().getAllClassId());
//        校验是否为blog 类型 如果不是禁止保存
//      状态 0不可使用 1可使用的类型 已经设置的无关系可照常使用 只影响新增和修改
        if (!allClass.getClassMapping().getClassMappingName().equals(Params.CLASS_MAPPERING_BLOG) || allClass.getAllClassState() != 1
                ) {
//            非法操作
            throw new IllegalOperationException(ResultEnums.ILLEGAL_OPERATION);
        }
//        修改数据
//        修改blog状态 为未审核状态
        dataBlog.setBlogState(0);
        dataBlog.setBlogUpdateTime(new Date());
        dataBlog.setBlogTitle(blog.getBlogTitle());
        dataBlog.setBlogDesc(blog.getBlogDesc());
        dataBlog.setBlogText(blog.getBlogText());
        dataBlog.setAllClass(allClass);
//      保存修改的数据
        Blog save = blogRepository.save(dataBlog);
        return save;
    }

    //    修改blog状态
    @Override
    public Blog updBlogState(User user, Integer blogId, Integer state) {
        if (blogId == null || blogId < 0) throw new ParamException(ResultEnums.PARAM_IS_NULL);

        Blog dataBlog = blogRepository.findOne(blogId);//数据库中的数据
        if (dataBlog == null) {
            return null;
        }
//        校验用户
        checkBlogAuth(user, dataBlog.getUser());
// 如果状态本身就是一样的 防止重复操作
        if (state == dataBlog.getBlogState()) return null;
        //     修改状态
        dataBlog.setBlogState(state);
        dataBlog.setBlogUpdateTime(new Date());

        Blog save = blogRepository.save(dataBlog);
        return save;
    }

    /**
     * 按userid 查询所有blog
     * blogState 状态 -1删除 0待审核 1审核通过 2提交审核
     *
     * @param selectUser 访问这个接口的用户
     * @param blogUserId 所要查询的用户
     * @param page       分页
     * @return
     */
    @Override
    public PageUtil<List<Blog>> findBlogsByUserAndPage(User selectUser, Integer blogUserId, Integer page) {
        List<Integer> blogState = new ArrayList<>(); //博客状态
        if (blogUserId == null) {
            return null;
        }
//        校验博主状态是否异常
        User blogUser = userRepository.findOne(blogUserId);
        if (blogUser == null || blogUser.getUserState() != 1) {
            return null;
        }
//        非博主操作无法显示私有
        if (selectUser != null && selectUser.getUserId() == blogUserId) {
            blogState.add(0);
            blogState.add(2);
        }
        blogState.add(1);
        //        获取总记录数
        Integer size = blogRepository.countAllByUserAndBlogStateIn(blogUser, blogState);
        // 分页包装类
        PageUtil<List<Blog>> pageUtil = new PageUtil<>(size, Params.USER_BLOGS_PAGE_SIZEE);
        pageUtil.setPage(page);
        //    分页查询
        List<Blog> blogs = blogRepository.findBlogsByUserAndPage(blogUserId, blogState, pageUtil.getStartIndex(), pageUtil.getPageSize());
        pageUtil.setPageDate(blogs);
        return pageUtil;
    }

    /**
     * 获取首页上最热博客 为当天浏览量最高的
     *
     * @return
     */
    @Override
    public List<BlogResult> getHotIndexBlog() {

//        RedisTemplate<String, BlogResult> redisTemplateBlogResult = new RedisTemplate<>();
        redisTemplateBlogResult.setConnectionFactory(redisConnectionFactory);
        //先去redis中获取 如果存在 则返回 如果不存在则查询
        List<BlogResult> blogResults = redisTemplateBlogResult.opsForList().range(Params.INDEX_HOT_BLOG, 0L, -1L);
//        如果数据不存在 查询并放入redis中
        if (blogResults == null || blogResults.size() <= 0) {
            //         返回值

//         查询redis
            blogResults = pageviewsService.getFirst10PageviewsBlog();

            if (blogResults != null && blogResults.size() > 0) {
                //存入redis中
                redisTemplateBlogResult.opsForList().rightPushAll(Params.INDEX_HOT_BLOG, blogResults);
                //            设置超时时间
                redisTemplateBlogResult.expire(Params.INDEX_HOT_BLOG, Params.INDEX_REDIS_OUT_TIME, TimeUnit.MINUTES);
            }
        }
        return blogResults;

    }

    /**
     * 获取首页推荐内容通过 博客的点赞量和收藏量排序
     * TODO 目前主要按照收藏量+点赞量
     *
     * @return
     */
    @Override
    public List<BlogResult> getTuiJianBlog() {
        redisTemplateBlogResult.setConnectionFactory(redisConnectionFactory);
        //先去redis中获取 如果存在 则返回 如果不存在则查询
        List<BlogResult> blogResults = redisTemplateBlogResult.opsForList().range(Params.INDEX_TUI_JIAN_BLOG, 0L, -1L);
//        如果不存在
        if (blogResults == null || blogResults.size() <= 0) {
            blogResults = new ArrayList<>();
////        查选收藏量最高的10条记录
//        List<CollectBlog> first10ByCollectNum = collectBlogRepository.findFirst10ByCollectNum();
//        查询前10 收藏加点赞 的博客
            List<Blog> blogs = blogRepository.fingMyAllByCollectAndThumb();
            for (int i = 0; i < blogs.size(); i++) {
                BlogResult blogResult = new BlogResult();
                blogResult.setBlog(blogs.get(i));
                //            查询收藏量
                Integer integer = collectBlogRepository.countByBlog(blogs.get(i));
                blogResult.setCollectNum(integer);
//            查询点赞量
                Integer integer1 = thumbBlogRepository.countByBlogId(blogs.get(i).getBlogId());
                blogResult.setThumbBlogNum(integer1);

                blogResults.add(blogResult);
            }
//            保存到Redis中
            if (blogResults != null && blogResults.size() > 0) {
                //存入redis中
                redisTemplateBlogResult.opsForList().rightPushAll(Params.INDEX_TUI_JIAN_BLOG, blogResults);
                //            设置超时时间
                redisTemplateBlogResult.expire(Params.INDEX_TUI_JIAN_BLOG, Params.INDEX_REDIS_OUT_TIME, TimeUnit.MINUTES);
            }
        }
        return blogResults;
    }

    /**
     * 搜搜
     *
     * @param param
     * @param page
     * @return
     */
    @Override
    public PageUtil<List<Blog>> searchBlogs(String param, Integer page) {

        String param1 = "%" + param + "%"; //模糊查询需要带%
//它的页码从0开始
        Pageable pageable = new PageRequest(page - 1, 10);
        Page<Blog> page1 = blogRepository.findByBlogTitleLikeOrBlogDescLikeOrderByBlogUpdateTimeDesc(param1, param1, pageable);
//        Specification<Blog> specification=new Specification<Blog>() {
//            @Override
//            public Predicate toPredicate(Root<Blog> root,
//                                         CriteriaQuery<?> criteriaQuery,
//                                         CriteriaBuilder criteriaBuilder) {
//                Path blogTitle = root.get("blogTitle");
//                Path blogDesc = root.get("blogDesc");
//
////                Path userName = root.get("user.userName");
//                Predicate like = criteriaBuilder.like(blogTitle, param);
//                Predicate like1 = criteriaBuilder.like(blogDesc, param);
////                Predicate like2 = criteriaBuilder.like(userName, param);
//                Predicate or = criteriaBuilder.or(like,like1);
//                return or;
//            }
//        };
//        Page<Blog> page1 = blogRepository.findAll(specification, pageable);
        Long l = page1.getTotalElements(); //总记录数
        PageUtil<List<Blog>> pageUtil = new PageUtil(l.intValue(), page1.getSize());
        pageUtil.setPage(page);
        pageUtil.setPageDate(page1.getContent());

//        将关键词设置为红色
        showSearchParam(pageUtil.getPageDate(), param);
        return pageUtil;

    }

    /**
     * 校验是否为博主
     *
     * @param user     访问用户
     * @param blogUser 博客作者
     * @return
     */

    private void checkBlogAuth(User user, User blogUser) {

        if (user.getUserId() != blogUser.getUserId()) {
            //非博主 todo 之后需要记录用户的2XXXX错误操作
            throw new IllegalOperationException(ResultEnums.ILLEGAL_OPERATION_INFRINGEMENT);
        }
        if (blogUser.getUserState() != 1) {
//            博主状态异常
            throw new UserStateException(ResultEnums.USER_START_ERROR);
        }
    }

    /**
     * 将搜索词明显显示
     * @param list  搜索结果
     * @param param 搜索条件
     */
    private void showSearchParam(List<Blog> list, String param) {
        for (int i = 0; i < list.size(); i++) {
            Blog blog = list.get(i);
            blog.setBlogTitle(blog.getBlogTitle().replaceFirst(param, "<span class='text-danger' >" + param + "</span>"));
            blog.setBlogDesc(blog.getBlogDesc().replaceFirst(param, "<span class='text-danger' >" + param + "</span>"));
        }
    }

}
