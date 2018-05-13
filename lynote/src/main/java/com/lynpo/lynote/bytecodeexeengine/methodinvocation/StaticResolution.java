package com.lynpo.lynote.bytecodeexeengine.methodinvocation;

/**
 * Create by fujw on 2018/5/12.
 * *
 * StaticResolution
 */
public class StaticResolution {

    public static void sayHello() {
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}
