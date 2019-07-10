package com.lynpo.lynjava.basic;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Create by fujw on 2018/10/24.
 * *
 * StringOfIdentityHashMapKey
 */
public class StringOfIdentityHashMapKey {

    public static void main(String[] args) {
        String name = "abc";
        String value = "abc";
        String name1 = new String("abc");
        String value1 = new String("abc");
        Map<String, String> kv = new IdentityHashMap<>();
        kv.put(name, "value1");
        kv.put(value, "value2");// key-value 覆盖 key-name for: name==value is true
        kv.put(name1, "value3");
        kv.put(value1, "value4");

        System.out.println("value1:" + kv.get(name));
        System.out.println("value2:" + kv.get(value));
        System.out.println("for: name==value ? " + (name==value));
        System.out.println("value3:" + kv.get(name1));
        System.out.println("value4:" + kv.get(value1));
        System.out.println("for: name1==value1 ? " + (name1==value1));
    }
}
