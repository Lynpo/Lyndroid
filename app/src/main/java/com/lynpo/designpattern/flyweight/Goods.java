package com.lynpo.designpattern.flyweight;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Goods
 */
public class Goods implements IGoods {

    private String name;    // 内部状态
    private String version; // 外部状态

    Goods(String name) {
        this.name = name;
    }

    @Override
    public void showGoodsPrice(String name) {
        switch (version) {
            case "32":
                System.out.println("价格5199");
                break;
            case "128G":
                System.out.println("价格 7899");
                break;
        }
    }
}
