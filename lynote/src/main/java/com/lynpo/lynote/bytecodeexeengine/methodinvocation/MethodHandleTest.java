package com.lynpo.lynote.bytecodeexeengine.methodinvocation;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Create by fujw on 2018/5/13.
 * *
 * MethodHandleTest
 */
public class MethodHandleTest {

    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();

        /*
         * 无论 obj 最终是哪个类型，下面这句都能正确调用到 println() 方法
         */
        getPrintlnMH(obj).invokeExact("hello from MethodHandle invoking...");

        // run result:
        // hello from MethodHandle invoking...
    }

    private static MethodHandle getPrintlnMH(Object receiver) throws Throwable {
        /*
         *  MethodType: 代表"方法类型"，
         *  包含了方法的返回值（methodType() 的第一个参数）
         *  和具体参数（methodType() 第二个其后的参数
         */
        MethodType mt = MethodType.methodType(void.class, String.class);

        /*
         * lookup() 方法来自 MethodHandles.Lookup lookup()，
         * 这句作用是在指定类中查找符合给定方法名称、方法类型，且符合调用权限的方法句柄。
         *
         * 因为这里调用的是一个虚方法，按 Java 语言规则，
         * 方法第一个参数是隐式的，代表该方法的接收者，也即是 this 指向的对象，
         * 而现在提供了 bindTo() 方法来完成这件事情
         */
        return lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
    }
}
