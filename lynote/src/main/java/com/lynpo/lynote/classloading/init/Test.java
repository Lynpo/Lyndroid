package com.lynpo.lynote.classloading.init;

/**
 * Create by fujw on 2018/5/8.
 * *
 * Test
 */
public class Test {

    static {
        i = 0;      // 给变量赋值可以正常编译通过
//        System.out.println(i);  // 这句编译器会提示"非法向前引用" Illegal forward reference
    }
    static int i = 1;
}
