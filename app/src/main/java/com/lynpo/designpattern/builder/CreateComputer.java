package com.lynpo.designpattern.builder;

/**
 * Create by fujw on 2018/4/1.
 * *
 * CreateComputer
 */
public class CreateComputer {

    public static void main(String[] args) {
        Builder builder = new AppleComputerBuilder();
        Director director = new Director(builder);
        director.createComputer("cpur", "name", "card", "screen");
    }
}
