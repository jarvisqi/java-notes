package com.softmax.oauth2.security.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * InitializingBean:可以在Bean属性全部改完之后，再做一些定制化操作。
 * AplicationContextAware:用处很大，注入了ApplicationContext到Bean中。
 */
//@Component
public class Student implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean, ApplicationContextAware {

    private String name;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware......");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware......");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean......");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean......");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware......");
    }
}
