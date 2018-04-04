package com.lynpo.designpattern.proxy;

import android.util.Log;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Lynpo
 */
public class Lynpo implements IShop {

    @Override
    public void buy() {
        Log.d("debug_info", "buy a book");
    }
}
