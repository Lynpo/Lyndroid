package com.lynpo.lynote.classloading;

/**
 * Create by fujw on 2018/5/6.
 * *
 * NotInitialization
 *
 * 非主动使用类字段演示
 */
public class NotInitialization2 {

    public static void main(String[] args) {
        SuperClass[] scs = new SuperClass[0];

        /*
        上述代码执行没有输出，说明 SuperClass 没有初始化，但出发了另外一个名为
        "[Lcom.lynpo.notelib.sample.classloading.SuperClass" 的类的初始化，
        它是由虚拟机自动生成、直接继承于 java.lang.Object 的子类，创建动作由字节码指令 newarray 触发。
         */
    }
}
