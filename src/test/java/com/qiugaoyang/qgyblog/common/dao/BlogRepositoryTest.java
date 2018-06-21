package com.qiugaoyang.qgyblog.common.dao;


import com.qiugaoyang.qgyblog.common.domain.Blog;
import com.qiugaoyang.qgyblog.common.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = false)
public class BlogRepositoryTest {
    @Resource
    BlogRepository blogRepository;
    @Resource
    UserRepository userRepository;


    @Test
    public void countAllByUserAndBlogStateInAnd() throws Exception {
        User one = userRepository.findOne(1);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(0);
        list.add(2);
        Integer size=blogRepository.countAllByUserAndBlogStateIn(one,list);

    }

    @Test
    public void findBlogsByUserAndPage() throws Exception {
        User one = userRepository.findOne(1);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(0);
        list.add(2);
        List<Blog> list1=blogRepository.findBlogsByUserAndPage(1,list,0,5);

    }



    @Test
    @Transactional
    public void findFirst10ByBlogStateOrderByBlogCreateTime() throws Exception {
        List<Blog> list = blogRepository.findFirst10ByBlogStateOrderByBlogCreateTimeDesc(1);
//        User user = userRepository.findOne(6);
//
//        Blog blog=new Blog();
//        blog.setBlogTitle("日期测试");
//        blog.setBlogDesc("日期测试");
//        blog.setUser(user);
//        blog.setBlogText("<p>hello</p>");
//        blog.setBlogCreateTime(new Date());
//        blog.setBlogUpdateTime(new Date());
//        Blog save = blogRepository.save(blog);
//        blogRepository.flush();
    }

}