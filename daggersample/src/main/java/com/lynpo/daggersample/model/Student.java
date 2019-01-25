package com.lynpo.daggersample.model;

import javax.inject.Inject;

/**
 * Student
 * *
 * Create by fujw on 2019/1/21.
 */
public class Student {

    public String name;

    @Inject
    public Student(String name) {
        this.name = name;
    }

    public String sayHello() {
        return "Hello, this is " + name;
    }
}
