package com.lynpo.dagger2.component;

import com.lynpo.dagger2.model.Swordman;
import com.lynpo.dagger2.module.SwordmanModule;

import dagger.Component;

/**
 * Create by fujw on 2018/4/1.
 * *
 * SwordmanComponent
 */
@Component(modules = {SwordmanModule.class})
public interface SwordmanComponent {

    Swordman getSwordman();
}
