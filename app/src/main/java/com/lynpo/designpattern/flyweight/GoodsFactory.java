package com.lynpo.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by fujw on 2018/4/1.
 * *
 * GoodsFactory：适合大量对象
 */
public class GoodsFactory {

    //
    private static Map<String, Goods> pool = new HashMap<>();
    public static Goods getGoods(String name) {
        if (pool.containsKey(name)) {
            return pool.get(name);
        } else {
            Goods goods = new Goods(name);
            pool.put(name, goods);
            return goods;
        }
    }
}
