package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class DbConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String className;

    @Value("${spring.datasource.druid.master.url}")
    private String url;
    @Value("${spring.datasource.druid.master.username}")
    private String username;
    @Value("${spring.datasource.druid.master.password}")
    private String password;

    @Override
    public String toString() {
        return "DbConfig{" +
                "className='" + className + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}