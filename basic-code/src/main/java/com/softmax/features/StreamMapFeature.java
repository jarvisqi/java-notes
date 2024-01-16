package com.softmax.features;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StreamMapFeature {

    static StreamMapFeature mapFeature = new StreamMapFeature();

    public List<Student> initData() {
        List<Student> students = new ArrayList<>();
        Student s1 = new Student();
        s1.setName("王五");
        s1.setSchoolClass("一年级");
        s1.setChineseScore(100);
        s1.setMathScore(100);
        students.add(s1);

        Student s2 = new Student();
        s2.setName("李四");
        s2.setSchoolClass("一年级");
        s2.setChineseScore(100);
        s2.setMathScore(100);
        students.add(s2);

        Student s3 = new Student();
        s3.setName("李思");
        s3.setSchoolClass("二年级");
        s3.setChineseScore(100);
        s3.setMathScore(100);
        students.add(s3);

        Student s4 = new Student();
        s4.setName("王五");
        s4.setSchoolClass("三年级");
        s4.setChineseScore(100);
        s4.setMathScore(100);
        students.add(s4);

        Student s5 = new Student();
        s5.setName("赵三");
        s5.setSchoolClass("一年级");
        s5.setChineseScore(100);
        s5.setMathScore(100);
        students.add(s5);
        return students;
    }

    public static void main(String[] args) {
        mapFeature.findValue();


    }

    private void findValue() {

        //找出所有的学生姓名 打印
        List<Student> students = mapFeature.initData();
        students.stream().map(student -> student.getName()).forEach(System.out::println);


    }


    class Student implements Serializable {
        /**
         * 姓名
         */
        private String name;
        /**
         * 班级
         */
        private String schoolClass;

        /**
         * 语文成绩
         */
        private Integer chineseScore;
        /**
         * 数学成绩
         */
        private Integer MathScore;

        public Student(String name, String schoolClass, Integer chineseScore, Integer mathScore) {
            this.name = name;
            this.schoolClass = schoolClass;
            this.chineseScore = chineseScore;
            MathScore = mathScore;
        }

        public Student() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchoolClass() {
            return schoolClass;
        }

        public void setSchoolClass(String schoolClass) {
            this.schoolClass = schoolClass;
        }

        public Integer getChineseScore() {
            return chineseScore;
        }

        public void setChineseScore(Integer chineseScore) {
            this.chineseScore = chineseScore;
        }

        public Integer getMathScore() {
            return MathScore;
        }

        public void setMathScore(Integer mathScore) {
            MathScore = mathScore;
        }

        //省略get、set方法
    }
}
