package com.ruoyi.web.controller.system;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasypApplicationTests {



    @Autowired
    private StringEncryptor encryptor;

    @Autowired
    private DbConfig dbConfig;

    @Test
    public void test(){
        String url = encryptor.encrypt("jdbc:mysql://localhost:3306/vue-pro?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
        String username = encryptor.encrypt("vue-pro");
        String password = encryptor.encrypt("1234");
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
    }

    @Test
    public void show(){
        System.out.println(dbConfig);
    }
}