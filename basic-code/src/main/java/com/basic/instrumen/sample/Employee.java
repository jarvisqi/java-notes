package com.basic.instrumen.sample;

import java.io.Serializable;

/**
 * @author Jarvis
 * @date 2018/9/13
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 5288074570576988476L;
    public String name;
    public String address;
    public transient int SSN;
    public int number;

    public void mailCheck() {
        System.out.println("Mailing a check to " + name + " " + address);
    }
}
