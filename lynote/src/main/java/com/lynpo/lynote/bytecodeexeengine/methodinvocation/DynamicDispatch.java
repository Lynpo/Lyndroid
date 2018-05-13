package com.lynpo.lynote.bytecodeexeengine.methodinvocation;

/**
 * Create by fujw on 2018/5/13.
 * *
 * DynamicDispatch
 */
public class DynamicDispatch {

    static abstract class Human {
        protected abstract void sayHello();
    }
    static class Man extends Human {
        protected void sayHello() {
            System.out.println("man say hello");
        }
    }
    static class Woman extends Human {
        protected void sayHello() {
            System.out.println("woman say hello");
        }
    }
    public static void main(String[] args) {
        // 执行片段 1：
        Human man = new Man();
        Human woman = new Woman();

        man.sayHello();
        woman.sayHello();

        man = new Woman();
        man.sayHello();

        // run result:
        // man say hello
        // woman say hello
        // woman say hello
    }
}
