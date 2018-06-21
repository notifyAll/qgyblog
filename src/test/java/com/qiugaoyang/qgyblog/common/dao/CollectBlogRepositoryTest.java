package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.CollectBlog;
import com.qiugaoyang.qgyblog.common.resultbean.BlogResult;
import com.qiugaoyang.qgyblog.common.resultbean.CollectBlogResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = false)
public class CollectBlogRepositoryTest {
    @Resource
    CollectBlogRepository collectBlogRepository;
    @Test
    public void findFirst10ByCollectNum() throws Exception {

        List<CollectBlog> first10ByCollectNum = collectBlogRepository.findFirst10ByCollectNum();
    }

}