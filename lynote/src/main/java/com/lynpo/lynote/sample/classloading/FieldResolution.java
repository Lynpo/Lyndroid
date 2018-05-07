package com.lynpo.lynote.sample.classloading;

/**
 * Create by fujw on 2018/5/7.
 * *
 * FieldResolution
 */
public class FieldResolution {

    interface Interface0 {
        int A = 0;
    }

    interface Interface1 extends Interface0 {
        int A = 1;
    }

    interface Interface2 {
        int A = 2;
    }

    static class Parent implements Interface1 {
        public static int A = 3;
    }

    static class Sub extends Parent implements Interface2 {
//        public static int A = 4;
        public static int A = 4;
    }

    public static void main(String[] args) {
        System.out.println(Sub.A);

        // 如果注释掉 Sub 类中的 public static int A = 4;
        // 不能编译 Reference to 'A' is ambiguous, ...
    }
}
