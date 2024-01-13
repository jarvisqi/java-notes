package com.softmax.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalFuture {

    public static void main(String[] args) {
        // 创建一个测试的用户集合
        List<User> userList = new ArrayList<>();
        // 创建几个测试用户
        User user1 = new User("abc");
        User user2 = new User("efg");
        User user3 = null;
        // 将用户加入集合
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        // 创建用于存储姓名的集合
        List<String> nameList = new ArrayList();
        List<User> nameList03 = new ArrayList();
        List<String> nameList04 = new ArrayList();
        // 循环用户列表获取用户信息，值获取不为空且用户以 a 开头的姓名，
        // 如果不符合条件就设置默认值，最后将符合条件的用户姓名加入姓名集合
        for (User user : userList) {
            nameList.add(Optional.ofNullable(user).map(User::getName).filter(value -> value.startsWith("a")).orElse("未填写"));
        }

        // 输出名字集合中的值
        System.out.println("通过 Optional 过滤的集合输出：");
        System.out.println("nameList.size() = " + nameList.size());
        nameList.stream().forEach(System.out::println);

        Optional.ofNullable(userList)
                .ifPresent(u -> {
                    u.forEach(m -> {
                        Optional<String> stringOptional = Optional.ofNullable(m).map(User::getName).filter(f -> f.startsWith("a"));
                        stringOptional.ifPresent(nameList04::add);
                    });
                });
        System.out.println("nameList04.size() = " + nameList04.size());
        nameList04.forEach(System.err::println);

        Optional.ofNullable(userList).ifPresent(nameList03::addAll);
        System.out.println("nameList03.size() = " + nameList03.size());
        nameList03.stream().forEach(System.err::println);

        User usrOpt = new User("zhangsan11");

        OptionalFuture optionalFuture = new OptionalFuture();

        User usr = optionalFuture.getUser(usrOpt);
        System.out.println(usr.getName());
        User userOptional = optionalFuture.getUserOptional(usrOpt);
        System.out.println(userOptional.getName());

    }


    /**
     * 以前写法
     */
    public User getUser(User user) {
        if (user != null) {
            String name = user.getName();
            if ("zhangsan".equals(name)) {
                return user;
            }
        } else {
            user = new User();
            user.setName("zhangsan");
            return user;
        }
        return user;
    }


    public User getUserOptional(User user) {
        return Optional.ofNullable(user)
                .filter(u -> "zhangsan".equals(u.getName()))
                .orElseGet(() -> {
                    User user1 = new User();
                    user1.setName("zhangsan");
                    return user1;
                });
    }


}

class User {
    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

}
