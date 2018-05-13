package com.lynpo.lynote.bytecodeexeengine.methodinvocation;

/**
 * Create by fujw on 2018/5/12.
 * *
 * StaticDispatch
 */
public class StaticDispatch {

    static abstract class Human {
    }
    static class Man extends Human {
    }
    static class Woman extends Human {
    }

    public void sayHello(Human guy) {
        System.out.println("hello, guy");
    }

    public void sayHello(Man guy) {
        System.out.println("hello, gentleman");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello, lady");
    }

    public static void main(String[] args) {
        // 执行片段 1：
        Human man = new Man();
        Human woman = new Woman();

        StaticDispatch dispatch = new StaticDispatch();
        dispatch.sayHello(man);
        dispatch.sayHello(woman);

        // 执行片段 2：
        Man man1 = new Man();
        Woman woman1 = new Woman();
        dispatch.sayHello(man1);
        dispatch.sayHello(woman1);

        // 执行片段 1：运行输出：
        // hello, guy
        // hello, guy

        // 执行片段 2：输出
        // hello, gentleman
        // hello, lady

    }
}
