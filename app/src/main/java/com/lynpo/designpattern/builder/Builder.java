package com.lynpo.designpattern.builder;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Builder
 */
public abstract class Builder {
    public abstract void buildCpu(String cpu);
    public abstract void buildName(String name);
    public abstract void buildCard(String cpu);
    public abstract void buildScreen(String cpu);
    public abstract Computer create();
}
