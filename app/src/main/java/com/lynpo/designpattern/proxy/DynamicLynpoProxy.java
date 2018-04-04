package com.lynpo.designpattern.proxy;

import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * Create by fujw on 2018/4/1.
 * *
 * DynamicLynpoProxy
 */
public class DynamicLynpoProxy implements InvocationHandler {

    private Object object;

    public DynamicLynpoProxy(Object obj) {
        this.object = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(proxy, args);
        if (TextUtils.equals(method.getName(), "buy")) {
            // do something
            Log.d("debug_info", "some one is buying");
        }
        return null;
    }
}
