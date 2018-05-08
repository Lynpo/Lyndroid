package com.lynpo.lynote.sample.classloading.init;

/**
 * Create by fujw on 2018/5/8.
 * *
 * DeadLoopClass
 */
public class DeadLoopClass {

    static {
        /*如果不加上这个 if 语句，编译器将提示 "Initializer must be able to complete normally"*/
        if (true) {
            System.out.println(Thread.currentThread() + "init DeadLoopClass");
            while (true) {

            }
        }
    }
}
