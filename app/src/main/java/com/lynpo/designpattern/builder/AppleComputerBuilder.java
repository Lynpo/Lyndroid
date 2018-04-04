package com.lynpo.designpattern.builder;

/**
 * Create by fujw on 2018/4/1.
 * *
 * AppleComputerBuilder
 */
public class AppleComputerBuilder extends Builder {

    private Computer mComputer = new Computer();

    @Override
    public void buildCpu(String cpu) {
        mComputer.setmCpu(cpu);
    }

    @Override
    public void buildName(String name) {
        mComputer.setmCpu(name);
    }

    @Override
    public void buildCard(String cpu) {
        mComputer.setmCpu(cpu);
    }

    @Override
    public void buildScreen(String cpu) {
        mComputer.setmCpu(cpu);
    }

    @Override
    public Computer create() {
        return mComputer;
    }
}
