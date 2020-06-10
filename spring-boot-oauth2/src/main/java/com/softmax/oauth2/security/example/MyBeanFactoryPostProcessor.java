package com.softmax.oauth2.security.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * BeanFactoryPostProcessor 主要是在Spring刚加载完配置文件，还没来得及初始化Bean的时候做一些操作。
 * 比如篡改某个Bean在配置文件中配置的内容。
 *
 * @author Jarvis
 */
//@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("这是 BeanFactoryPostProcessor 实现类构造器");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor 调用 postProcessBeanFactory  方法");
        BeanDefinition bd = configurableListableBeanFactory.getBeanDefinition("student");
        MutablePropertyValues propertyValues = bd.getPropertyValues();
        //配置文件中的信息在加载到Spring中后以BeanDefinition的形式存在.在这里又可以更改BeanDefinition,所以可以理解为更改配置文件里面的内容
//        propertyValues.add("zdy","123");
    }


}
