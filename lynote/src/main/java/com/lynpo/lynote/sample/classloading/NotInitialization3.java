package com.lynpo.lynote.sample.classloading;

/**
 * Create by fujw on 2018/5/6.
 * *
 * NotInitialization
 *
 * 非主动使用类字段演示
 */
public class NotInitialization3 {

    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO_WORLD);

        /*
        上述代码执行， 输出 Hello World
        没有输出，"ConstClass init!"。
        常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。

        实际上 NotInitialization3 的 Class 文件之中并没有 ConstClass 类的符号引用入口，
        这两个雷在编译成 Class 文件后就不存在任何联系了。
         */
    }
}
