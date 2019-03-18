package com.basic.lambda.example;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lambda表达式
 *
 * @author : Jarvis
 * @date : Created in 2018/5/25
 */
public class Lambda {

    /**
     * 基本使用
     */
    public void baseUse() {
        //用lambda表达式实现Runnable
        new Thread(() -> System.out.println("Before Java8, too much code for too little to do")).start();

        new Thread(() -> System.out.println("In Java8, Lambda expression rocks !!")).start();

        //使用Java 8 lambda表达式进行事件处理
        JButton button = new JButton("Show");

        button.addActionListener(e -> System.out.println("Event handling without lambda expression is boring"));
        // Java 8方式：
        button.addActionListener((e) -> System.out.println("Light, Camera, Action !! Lambda expressions Rocks"));


        //使用lambda表达式对列表进行迭代
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : features) {
            System.out.print(feature);
        }
        // Java 8之后：
        features.forEach(System.out::print);
        System.out.println("");

        //使用函数式接口Predicate
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        //字符串过滤
        List<?> newLan = languages.stream().filter(x -> !x.startsWith("J")).collect(Collectors.toList());
        newLan.forEach(x -> System.out.print(x + " "));
        System.out.println();
        //过滤 toList
        List<?> conts = languages.stream().filter(x -> !x.toLowerCase().contains("c")).collect(Collectors.toList());
        conts.forEach(x -> System.out.print(x + " "));
        languages.stream().filter(x -> !x.toLowerCase().contains("c")).forEach(x -> System.out.print(x + " "));
        System.out.println();

        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        costBeforeTax.forEach(n -> System.out.print(n + " "));
        System.out.println();
        costBeforeTax.stream().map(c -> c * 0.1).forEach(n -> System.out.print(n + " "));

        System.out.println("\n求和计算：");
        double bill = costBeforeTax.stream().map(c -> c * 0.1).reduce((sum, cost) -> sum + cost).get();
        System.out.print(bill);

        //获取数字的个数、最小值、最大值、总和以及平均值
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        int stats = primes.stream().mapToInt((x) -> x).sum();
        System.out.println("\n求Sum：" + stats);
        IntSummaryStatistics statsStatis = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("求Sum：" + statsStatis.getSum());
        System.out.println("求Avg：" + statsStatis.getAverage());
        System.out.println("求Count：" + statsStatis.getCount());
        System.out.println("求Max：" + statsStatis.getMax());
        System.out.println("求Min：" + statsStatis.getMin());

        //排序
        List<Integer> arrs = Arrays.asList(1, 12, 21, 3, 13, 66, 40);
        System.out.println("使用lambda排序前：" + arrs);
        Collections.sort(arrs, Comparator.reverseOrder());
        System.out.println("使用lambda降序排序后：" + arrs);
        Collections.sort(arrs, Comparator.naturalOrder());
        Collections.sort(arrs, Comparator.naturalOrder());
        System.out.println("使用lambda升序排序后：" + arrs);

        //自定义对象排序
        List<Human> humans = new ArrayList();
        humans.add(new Human(12, "aa"));
        humans.add(new Human(22, "BB"));
        humans.add(new Human(5, "cc"));

        humans.sort((Human h1, Human h2) -> h1.getAge());
        humans.sort(Comparator.comparing(Human::getAge));
        for (Human h : humans) {
            System.out.print("Age升序：" + h.name + " " + h.getAge() + " ");
        }

        humans.sort((Human h1, Human h2) -> h1.getName().compareToIgnoreCase(h2.getName()));
        for (Human h : humans) {
            System.out.print("Name升序：" + h.name + " " + h.getAge() + " ");
        }

    }

    /**
     * 排序使用
     */
    class Human {

        public Human(int age, String name) {
            super();
            this.name = name;
            this.age = age;
        }

        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static void main(String[] args) {
        //使用::关键字引用构造函数
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Ents", "Lee");
        System.out.println(person.firstName);

        String a = "hello2";
        //变量b被final修饰，因此会被当做编译器常量，所以在使用到b的地方会直接将变量b 替换为它的  值
        final String b = "hello";
        String d = "hello";
        String c = b + 2;
        String e = d + 2;
        System.out.println(a == c);
        System.out.println(a == e);
    }
}

/**
 * 接口
 */
interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}

class Person {
    Person() {
    }

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    String firstName;
    String lastName;


}

