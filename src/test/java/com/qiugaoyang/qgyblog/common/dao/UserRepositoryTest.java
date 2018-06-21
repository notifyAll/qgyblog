package com.qiugaoyang.qgyblog.common.dao;

import com.qiugaoyang.qgyblog.common.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.sql.Date;


@EnableTransactionManagement
@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(value = false)
public class UserRepositoryTest {

    @Resource
    UserRepository userRepository;

    @Test
    @Transactional
    public void saveUser() {

        User user = new User();
        user.setUserName("hello");
        user.setUserEmail("12222@qq.com");
        user.setUserPassword("123");
        user.setUserCreateTime(new Date(new java.util.Date().getTime()));
        user.setUserState(1);

        userRepository.save(user);

    }

}