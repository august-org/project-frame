package com.august;

import com.august.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompanyFrameApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private RedisUtil redisUtil;
    @Test
    public void testRedis(){
        redisUtil.set("L","520");
        System.err.println(redisUtil.get("L"));
    }

}
