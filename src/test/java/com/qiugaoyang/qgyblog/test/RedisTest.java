package com.qiugaoyang.qgyblog.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    RedisTemplate<String, String> redisTemplate;
    @Test
    public void  getAndSetMep(){
        HashMap<String, String> map = new HashMap<>();


        map.put("token","hgsahj");
        map.put("name","亚丝娜");

        redisTemplate.opsForHash().putAll("hello",map);
        redisTemplate.expire("hello",100, TimeUnit.SECONDS);

        Set<Object> hello = redisTemplate.opsForHash().keys("hello");
        Iterator<Object> iterator = hello.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        Map<Object, Object> hello1 = redisTemplate.opsForHash().entries("hello");
        System.out.println("---------------------");
        System.out.println(hello1.get("token"));
        System.out.println(hello1.get("name"));

        hello1.put("token","hahahah");

        redisTemplate.opsForHash().putAll("hello",hello1);



    }
}
