package com.lynpo.designpattern.decorate;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Master
 */
public abstract class Master extends Swordman {

    private Swordman mSwordman;

    public Master(Swordman swordman) {
        mSwordman = swordman;
    }

    @Override
    public void attackMagic() {
        mSwordman.attackMagic();
    }
}
