package com.lynpo.designpattern.decorate;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Client
 */
public class Client {

    public static void mian(String[] args) {
        YangGuo yangGuo = new YangGuo();
        Hong7Gong hong7Gong = new Hong7Gong(yangGuo);
        hong7Gong.attackMagic();
        yangGuo.attackMagic();
    }
}
