package com.lynpo.lynjava.apis.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Create by fujw on 2018/10/17.
 * *
 * TypeTest
 */
public class TypeTest {

    public static void main(String[] args) {
        User user = new User();
        String uType = user.getClass().getTypeName();
        System.out.println("user type:" + uType);

        Type suType = user.getClass().getGenericSuperclass();
        System.out.println("user Super class Type:" + suType);

        Type[] uTypes = user.getClass().getGenericInterfaces();
        for (Type type : uTypes) {
            String tn = type.getTypeName();
            System.out.println("user interfaces type name:" + tn);

            if (type instanceof ParameterizedType) {
                String ptn = ((ParameterizedType) type).getRawType().getTypeName();
                System.out.println("user parameterized interfaces raw type name:" + ptn);

                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                for (Type t : actualTypeArguments) {
                    String att = t.getTypeName();
                    System.out.println("user parameterized interfaces actual type Argument's type name:" + att);
                }
            }
        }
    }
}
