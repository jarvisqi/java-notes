package com.softmax.oauth2.security;

import com.softmax.oauth2.security.example.Student;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@MapperScan("com.softmax.oauth2.*")
public class Oauth2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Oauth2Application.class, args);
//
//        Student bean = context.getBean(Student.class);
//        bean.setName("wangwu");
    }

}
