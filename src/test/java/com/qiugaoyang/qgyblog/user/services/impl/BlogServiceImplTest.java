package com.qiugaoyang.qgyblog.user.services.impl;

import com.qiugaoyang.qgyblog.user.services.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = false)
public class BlogServiceImplTest {
    @Resource
    private BlogService blogService;

    @Test
    public void searchBlogs() throws Exception {
        blogService.searchBlogs("%测试%",1);
    }

}