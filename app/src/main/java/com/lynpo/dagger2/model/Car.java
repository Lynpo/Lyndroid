package com.lynpo.dagger2.model;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Car
 */
public class Car {

    private Engine engine;

    @Inject
    public Car(@Named("DieselEngine") Engine engine) {
        this.engine = engine;
    }

    // 另一种写法
//    @Inject
//    public Car(@Diesel Engine engine, boolean a) {
//        this.engine = engine;
//    }

    public void run() {
        engine.start();
    }
}
