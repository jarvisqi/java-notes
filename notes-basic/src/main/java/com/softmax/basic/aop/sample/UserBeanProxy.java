package com.softmax.basic.aop.sample;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类
 *
 * @author Jarvis
 * @date 2018/8/22
 */
public class UserBeanProxy implements InvocationHandler {

    private final Object targetObject;

    public UserBeanProxy(Object targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * Processes a method invocation on a proxy instance and returns
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        UserBeanImpl userBean = (UserBeanImpl) targetObject;
        String userName = userBean.getUserName();
        Object result = null;
        //方法返回值
        System.out.println("前置代理");
        if (userName != null && !"".equals(userName)) {
            //反射调用方法
            result = method.invoke(targetObject, args);
        }
        //声明结束
        System.out.println("后置代理");
        return result;
    }
}

class ProxyExe {

    public static void main(String[] args) {
        UserBeanImpl targetObject = new UserBeanImpl("Bob Liang");
        UserBeanProxy proxy = new UserBeanProxy(targetObject);
        //生成代理对象
        UserBean object = (UserBean) Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), proxy);
        object.addUser();

    }
}
