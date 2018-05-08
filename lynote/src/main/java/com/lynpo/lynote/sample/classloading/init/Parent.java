package com.lynpo.lynote.sample.classloading.init;

/**
 * Create by fujw on 2018/5/8.
 * *
 * Parent
 */
public class Parent {

    public static int A = 1;
    static {
        A = 2;
    }

    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);  //   输出 2，而不是 1
    }
}
