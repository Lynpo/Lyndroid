package com.lynpo.lynote.sample.classloading;

/**
 * Create by fujw on 2018/5/6.
 * *
 * SuperClass
 *
 * 被动使用类字段演示一：
 * 通过子类引用的静态字段，不会导致子类初始化
 */
public class SuperClass {

    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;
}
