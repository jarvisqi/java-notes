package com.designpatterns.builder;

/**
 * 建造者模式
 *
 * @author Jarvis
 * @date 2018/7/26
 */
public class User {

    private String name;
    private String password;
    private String nickName;
    private int age;

    /**
     * step 1 ,构造函数私有化
     */
    private User(String name, String password, String nickName, int age) {
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.age = age;
    }

    public static UserBuilder userBuilder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private String name;
        private String password;
        private String nickName;
        private int age;

        private UserBuilder() {
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }

        /**
         * // build() 方法负责将 UserBuilder 中设置好的属性“复制”到 User 中。
         * // 当然，可以在 “复制” 之前做点检验
         *
         * @return
         */
        public User build() {
            if (name == null || password == null) {
                throw new RuntimeException("用户名和密码必填");
            }
            if (age <= 0 || age >= 150) {
                throw new RuntimeException("年龄不合法");
            }
            // 还可以做赋予”默认值“的功能
            if (nickName == null) {
                nickName = name;
            }
            return new User(name, password, nickName, age);
        }

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "姓名：" + getName() + " 昵称：" + getNickName() + " 年龄：" + getAge();
    }
}

class AppBuild {
    public static void main(String[] args) {
        //建造者模式 构造一个user
        User user = User.userBuilder()
                .name("Trump")
                .nickName("King")
                .password("12345")
                .age(77)
                .build();
        var info = user.toString();
        System.out.println(info);
    }
}
