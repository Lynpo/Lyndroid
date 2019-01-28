package com.lynpo.thdlibs.dagger2.sample;

import javax.inject.Inject;

/**
 * Student
 * *
 * Create by fujw on 2019/1/21.
 */
public class Student {

    public String name;

    @Inject
    Student(String name) {
        this.name = name;
    }
}
