package com.lynpo.lynote.sample.classloading;

/**
 * Create by fujw on 2018/5/6.
 * *
 * SubClass
 */
public class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }
}
