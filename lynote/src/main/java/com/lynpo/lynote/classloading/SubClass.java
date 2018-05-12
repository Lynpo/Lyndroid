package com.lynpo.lynote.classloading;

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
