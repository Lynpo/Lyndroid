package com.lynpo.designpattern.decorate;

/**
 * Create by fujw on 2018/4/1.
 * *
 * Hong7Gong
 */
public class Hong7Gong extends Master {

    public Hong7Gong(Swordman swordman) {
        super(swordman);
    }

    private void teachAttackMagic() {
        System.out.println("洪七公教授打狗棒法");
        System.out.println("杨过学习使用打狗棒法");
    }

    @Override
    public void attackMagic() {
        super.attackMagic();
        teachAttackMagic();
    }

}
