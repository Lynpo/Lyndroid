package com.lynpo.designpattern.builder;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Director
 */
public class Director {

    Builder mBuilder = null;

    public Director(Builder builder) {
        mBuilder = builder;
    }

    public Computer createComputer(String cpu, String name ,String card, String screen) {
        this.mBuilder.buildCpu(cpu);
        this.mBuilder.buildCard(card);
        this.mBuilder.buildName(name);
        this.mBuilder.buildScreen(screen);
        return mBuilder.create();
    }
}
