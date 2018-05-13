package com.lynpo.lynote.bytecodeexeengine.methodinvocation;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Create by fujw on 2018/5/13.
 * *
 * MethodInvokeTest
 */
public class MethodInvokeTest {

    class GrandFather {
        void thinking() {
            System.out.println("i am grandfather");
        }
    }

    class Father extends GrandFather {
        void thinking() {
            System.out.println("i am father");
        }
    }

    class Son extends Father {
        void thinking() {
//            System.out.println("i am Son");

//            GrandFather gFather = new GrandFather();
//            gFather.thinking();

            try {
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);
            } catch (Throwable e) {

            }
        }
    }

    public static void main(String[] args) {
//        Son son = new MethodInvokeTest().new Son();
//        son.thinking();
        (new MethodInvokeTest().new Son()).thinking();

        // result:
        // i am father
        // 与书上示例运行结果("i am grandfather")不同
    }
}
