package com.lynpo.lynjava.apis.type;

/**
 * Create by fujw on 2018/10/17.
 * *
 * User
 */
public class User extends Human implements Named, Roar<String> {

    int id;
    String name;

    @Override
    public String who() {
        return name;
    }

    @Override
    public void roar(String s) {

    }
}
