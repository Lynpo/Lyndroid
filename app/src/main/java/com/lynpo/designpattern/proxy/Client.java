package com.lynpo.designpattern.proxy;

import java.lang.reflect.Proxy;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Client
 */
public class Client {

    public static void main(String[] args) {
        // 静态代理
        IShop lynpo = new Lynpo();
        IShop proxy = new LynpoProxy(lynpo);
        proxy.buy();

        // 动态代理
        // 创建真是主题
        IShop lynpoShop = new Lynpo();
        // 创建动态代理
        DynamicLynpoProxy dynamicLynpoProxy = new DynamicLynpoProxy(lynpoShop);
        // 创建 真是主题 的 ClassLoader
        ClassLoader classLoader = lynpoShop.getClass().getClassLoader();
        // 动态创建代理类
        IShop shopProxy = (IShop) Proxy.newProxyInstance(classLoader, new Class[]{IShop.class}, dynamicLynpoProxy);
        shopProxy.buy();
    }
}
