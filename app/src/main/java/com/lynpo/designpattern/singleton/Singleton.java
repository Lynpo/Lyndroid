package com.lynpo.designpattern.singleton;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Singleton
 */
public class Singleton {

    private Singleton() {
    }

    public static Singleton getInstance() {
        // 类加载时不会初始化 INSTANCE，方法调用时加载 SingletonHolder
        // 同时初始化 INSTANCE
        // 线程安全
        // INSTANCE 唯一性保证
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
}
