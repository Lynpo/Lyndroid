package com.lynpo.designpattern.proxy;

/**
 * Create by fujw on 2018/4/1.
 * *
 * LynpoProxy: 静态代理
 */
public class LynpoProxy implements IShop {

    private IShop mShop;

    public LynpoProxy(IShop shop) {
        mShop = shop;
    }

    @Override
    public void buy() {
        mShop.buy();
    }
}
