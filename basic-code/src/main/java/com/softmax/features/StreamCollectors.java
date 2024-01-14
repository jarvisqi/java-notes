package com.softmax.features;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public class StreamCollectors {


    static StreamCollectors streamCollectors = new StreamCollectors();

    public static void main(String[] args) {

        streamCollectors.collectPrint();

    }

    private void collectPrint() {
        final List<Student> students = Lists.newArrayList();
        students.add(new Student("1", "张三", LocalDate.of(2021, Month.APRIL, 1), 12, 12.123));
        students.add(new Student("2", "李四", LocalDate.of(2021, Month.MAY, 1), 11, 22.123));
        students.add(new Student("3", "王五", LocalDate.of(2021, Month.JULY, 1), 10, 32.123));


        //统计
        students.stream().collect(Collectors.counting());

        //平均值：averagingDouble、averagingInt、averagingLong

        Double avgScore = students.stream().collect(Collectors.averagingDouble(s -> s.score));
        Double avgScore0 = students.stream().collect(Collectors.averagingDouble(Student::getScore));
        Double collect = students.stream().collect(Collectors.averagingInt(s -> (int) s.getScore()));

        System.out.println(avgScore);
        System.out.println(avgScore0);
        System.out.println(collect);


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
