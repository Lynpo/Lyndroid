package com.lynpo.designpattern.flyweight;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Client
 */
public class Client {

    public static void main(String[] args) {
        Goods goods1 = GoodsFactory.getGoods("iphone7");
        goods1.showGoodsPrice("32G");
        Goods goods2 = GoodsFactory.getGoods("iphone7");
        goods2.showGoodsPrice("32G");
        Goods goods3 = GoodsFactory.getGoods("iphone7");
        goods3.showGoodsPrice("128G");

        // 运行结果：
        // Goods 对象创建 1 次，使用 3 次，其中后两次从缓存 pool 中读取
    }
}
