package com.softmax.features;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamCollectors {
    static StreamCollectors streamCollectors = new StreamCollectors();
    final List<Student> students = Lists.newArrayList();

    public StreamCollectors() {

        students.add(new Student("1", "张三", LocalDate.of(2021, Month.APRIL, 1), 12, 12.123));
        students.add(new Student("2", "李四", LocalDate.of(2021, Month.MAY, 1), 11, 22.123));
        students.add(new Student("3", "王五", LocalDate.of(2022, Month.JULY, 1), 10, 32.233));
        students.add(new Student("4", "赵六", LocalDate.of(2023, Month.JUNE, 1), 13, 39.231));
        students.add(new Student("4", "文七", LocalDate.of(2024, Month.AUGUST, 1), 14, 28.234));
    }

    public static void main(String[] args) {

//        streamCollectors.collectPrint();

        streamCollectors.collectMaxOrMin();
    }

    private void collectPrint() {

        //统计
        long count = students.stream().count();

        //平均值：averagingDouble、averagingInt、averagingLong

        Double avgScore = students.stream().collect(Collectors.averagingDouble(s -> s.score));
        Double avgScore0 = students.stream().collect(Collectors.averagingDouble(Student::getScore));
        Double collect = students.stream().collect(Collectors.averagingInt(s -> (int) s.getScore()));

        System.out.println(avgScore);
        System.out.println(avgScore0);
        System.out.println(collect);


    }

    /**
     * 最大值/最小值元素：maxBy、minBy
     */
    private void collectMaxOrMin() {

        Optional<Student> minStudent = students.stream().min(Comparator.comparing(Student::getAge));
        minStudent.ifPresent(s -> System.out.println(s.name + "—" + s.getAge()));

        Optional<Student> collectSorce = students.stream().max(Comparator.comparing(Student::getScore));
        collectSorce.ifPresent(s -> System.out.println(s.name + "—" + s.getScore()));
    }

    class Student {
        private String id;
        private String name;
        private LocalDate birthday;
        private int age;
        private double score;

        public Student(String id, String name, LocalDate birthday, int age, double score) {
            this.id = id;
            this.name = name;
            this.birthday = birthday;
            this.age = age;
            this.score = score;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirthday() {
            return birthday;
        }

        public void setBirthday(LocalDate birthday) {
            this.birthday = birthday;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
