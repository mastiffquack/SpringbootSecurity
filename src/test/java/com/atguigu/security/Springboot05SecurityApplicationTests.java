package com.atguigu.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot05SecurityApplicationTests {

    @Test
    public void contextLoads() {
        //进行加密
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
        System.out.println(encoder.encode("123456"));
        System.out.println(encoder.encode("123456"));
    }

}
