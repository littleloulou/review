package com.lph.ipc.serial;

import java.io.Serializable;

/**
 * Created by lph on 2017/9/3.
 */

public class SerialMode implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
