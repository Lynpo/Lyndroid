package com.lynpo.lynote.sample.classloading;

/**
 * Create by fujw on 2018/5/6.
 * *
 * NotInitialization
 *
 * 非主动使用类字段演示
 */
public class NotInitialization {

    public static void main(String[] args) {
        System.out.println(SubClass.value);
        // 输出：
        // SuperClass init!
        // 123
    }
}
