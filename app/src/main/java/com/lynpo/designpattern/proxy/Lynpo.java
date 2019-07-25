package com.lynpo.designpattern.proxy;

import android.util.Log;

import com.lynpo.eternal.LynConstants;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Lynpo
 */
public class Lynpo implements IShop {

    @Override
    public void buy() {
        Log.d(LynConstants.LOG_TAG, "buy a book");
    }
}
