package com.lynpo.lynote.bytecodeexeengine.localvariable;

/**
 * Create by fujw on 2018/5/10.
 * *
 * LocalVariableSlotTest
 */
public class LocalVariableSlotTest {

    // 示例一：placeHolder 在作用域内 gc 不回收
//    public static void main(String[] args) {
//        byte[] placeHolder = new byte[64 * 1024 * 1024];
//        System.gc();
//    }

    // 示例二：placeHolder 作用于域限制在大括号内，
    // 就逻辑而言，gc 执行，placeHolder 不能再被访问了
    // 而执行结果依然是未回收内存（经过 JIT 编译后，可以正确回收）
    // 原因：没有任何局部变量表读写操作，placeHolder 所占用 Slot 未被其他变量复用，有 GC Roots 关联
//    public static void main(String[] args) {
//        {
//            byte[] placeHolder = new byte[64 * 1024 * 1024];
//        }
//        System.gc();
//    }

    // 示例三：加入一行 赋值语句
    // gc 正确回收了 placeHolder
    public static void main(String[] args) {
        {
            byte[] placeHolder = new byte[64 * 1024 * 1024];
        }
        int a = 1;
        System.gc();
    }
}
