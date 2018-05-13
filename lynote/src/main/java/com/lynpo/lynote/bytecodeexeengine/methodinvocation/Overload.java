package com.lynpo.lynote.bytecodeexeengine.methodinvocation;

import java.io.Serializable;

/**
 * Create by fujw on 2018/5/12.
 * *
 * Overload
 */
public class Overload {

//    public static void sayHello(Object arg) {
//        System.out.println("hello Object");
//    }
//    public static void sayHello(int arg) {
//        System.out.println("hello int");
//    }
//    public static void sayHello(long arg) {
//        System.out.println("hello long");
//    }
//    public static void sayHello(Character arg) {
//        System.out.println("hello Character");
//    }
//    public static void sayHello(char arg) {
//        System.out.println("hello char");
//    }
    public static void sayHello(char... arg) {
        System.out.println("hello char ...");
    }
    public static void sayHello(Serializable arg) {
        System.out.println("hello Serializable");
    }
//    public static void sayHello(Comparable arg) {
//        System.out.println("hello Serializable");
//    }
    public static void main(String[] args) {
        sayHello('a');

        // 运行输出：hello char

        // 按优先执行顺序逐步注释以上方法，依次输出：
        // hello int
        // hello long

        // 以上按顺序：char -> int -> long -> float -> double
        // 不会匹配 byte, short ，因为 char 到 byte 或 short 转型是不安全的。

        // hello Character
        // hello Serializable

        // 类 -> 接口
        // 如果同时出现两个被实现的接口，优先级相同，拒绝编译。或程序调用时显式地指定字面量的静态类型，如：
        // sayHell((Comparable)'a')，能通过编译。

        // hello Object

        // 父类，多个父类：按继承关系从下往上搜索，越接近上层优先级越低。
        // 即使方法参数为 null 时，这个规则仍然适用。

        // hello char ...

        // 参数个数，重载优先级最低。

    }
}
