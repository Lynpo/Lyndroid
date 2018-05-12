package com.lynpo.lynote.classloading.init;

/**
 * Create by fujw on 2018/5/8.
 * *
 * Interface
 */
public interface Interface {
//    int b;  // variable might not have been initialized
    int PARENT_VAR = 10;
    // not allowed in interface 接口中不能使用静态语句块
//    static {
//
//    }
}
