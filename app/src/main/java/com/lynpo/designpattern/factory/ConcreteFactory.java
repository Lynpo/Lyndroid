package com.lynpo.designpattern.factory;

/**
 * Create by fujw on 2018/4/1.
 * *
 * ConcreteFactory
 */
public class ConcreteFactory extends Factory {

    @Override
    public <T extends IProduct> T produce(Class<T> cls) {
        IProduct product = null;
        String className = cls.getName();
        try {
            product = ((IProduct) Class.forName(className).newInstance());
        } catch (Exception e) {

        }
        return (T) product;
    }
}
