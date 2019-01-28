package com.lynpo.eternal.annos;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by fujw on 2018/7/3.
 * *
 * ActivityConfig
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ActivityConfig {

    String name();
}
