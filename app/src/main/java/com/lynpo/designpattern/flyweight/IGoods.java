package com.lynpo.designpattern.flyweight;

/**
 * Create by fujw on 2018/4/1.
 * *
 * IGoods, 享元模式：适合大量相似对象；需要缓冲池
 */
public interface IGoods {

    void showGoodsPrice(String name);
}
