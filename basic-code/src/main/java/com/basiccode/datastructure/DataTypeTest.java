package com.basiccode.datastructure;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jarvis
 * @date 2018/7/25
 */
public class DataTypeTest {

    public static void main(String[] args) {

        short s1 = 1;
        //由于1是int类型，因此s1+1运算结果也是int 型，需要强制转换类型才能赋值给short型
        s1 = (short) (s1 + 1);
        //相当于s1 = (short)(s1 + 1);其中有隐含的强制类型转换
        s1 += 1;

        //12
        System.out.println(Math.round(11.5));
        //-11
        System.out.println(Math.round(-11.5));

        //expr类型：byte、short、char、int、enum、String
        int expr = 0;
        switch (expr) {
            case 0:
                System.out.println(expr);
                break;
            default:
                System.out.println("default");
        }

        List<Student> list = new ArrayList<Student>();
        list.add(new Student(1, "Mahesh", 12));
        list.add(new Student(2, "Suresh", 15));
        list.add(new Student(3, "Nilesh", 10));
        list.add(new Student(4, "Nick", 8));
        list.add(new Student(5, "Nick", 12));

        System.out.println("\n\n集合排序");
        var ages = list.stream().sorted(Comparator.comparing(Student::getAge)).collect(Collectors.toList());
        ages.forEach(student -> System.out.print(student.getId() + "-" + student.getAge() + " "));
        System.out.println("Age正序");
        ages = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        ages.forEach(student -> System.out.print(student.getId() + "-" + student.getAge() + " "));
        System.out.println("Age倒序");
        ages = list.stream().sorted(Comparator.comparing(Student::getAge).reversed()
                .thenComparing(Student::getId).reversed())
                .collect(Collectors.toList());
        System.out.println("Age倒序，Id倒序");
        ages.forEach(student -> System.out.print(student.getId() + "-" + student.getAge() + " "));

        // 数组排序
        System.out.println("\n\n数组排序");
        int[] arrs = {1, 7, 2, 4, 9, 11, 6};
        Integer[] newArr = new Integer[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            newArr[i] = arrs[i];
        }
        List<Integer> listArr = Arrays.asList(newArr);
        listArr.sort((a, b) -> b.compareTo(a));
        listArr.sort(Comparator.reverseOrder());
        System.out.println(listArr);

        listArr.sort((a, b) -> a.compareTo(b));
        listArr.sort(Comparator.naturalOrder());
        System.out.println(listArr);

        System.out.println("\n使用Collections.sort");
        Collections.sort(listArr, (a, b) -> b.compareTo(a));
        Collections.sort(listArr, Comparator.reverseOrder());
        System.out.println(listArr);
        Collections.sort(listArr, Comparator.naturalOrder());
        System.out.println(listArr);

        System.out.println("\n使用parallelStream");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        //并行无序输出
        numbers.parallelStream().forEach(x -> System.out.print(x + " "));
        //并行有序输出
        System.out.println();
        numbers.parallelStream().forEachOrdered(x -> System.out.print(x + " "));

        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
        nums = Arrays.stream(nums).distinct().toArray();
    }


}

class Student {

    public Student(Integer id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    private Integer id;
    private String name;
    private Integer age;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
