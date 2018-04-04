package com.lynpo.designpattern.factory;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Factory
 */
public abstract class Factory {

    public abstract <T extends IProduct> T produce(Class<T> cls);
}
