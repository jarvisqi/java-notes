package com.basic.instrumen.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Jarvis
 * @date 2018/9/13
 */
public class SerializeDemo {

    public static void main(String[] args) {

//        Serialized();

        Deserialize();
    }

    /**
     * 序列化
     */
    private static void Serialized() {
        Employee employee = new Employee();
        employee.name = "Reyan Ali";
        employee.address = "Phokka Kuan, Ambehta Peer";
        employee.SSN = 11122333;
        employee.number = 101;

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\Learning\\java-learning\\java-tutorial\\basic-code\\src\\main\\java\\com\\basic\\instrumencode\\employee.ser");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(employee);

            fileOutputStream.close();
            outputStream.close();
            System.out.printf("Serialized data is saved in /tmp/employee.ser");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化
     */
    private static void Deserialize() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\Learning\\java-learning\\java-tutorial\\basic-code\\src\\main\\java\\com\\basic\\instrumencode\\employee.ser");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            Employee employee = (Employee) inputStream.readObject();

            System.out.println("Deserialized Employee...");
            System.out.println("Name: " + employee.name);
            System.out.println("Address: " + employee.address);
            System.out.println("SSN: " + employee.SSN);
            System.out.println("Number: " + employee.number);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
