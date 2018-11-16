package com.lynpo.dagger2.directproviderlazyinjectiondiff;

/**
 * Create by fujw on 2018/11/7.
 * *
 * MainClass
 */
public class MainClass {

    public static void main(String[] args) {
        DirectCounter directCounter = new DirectCounter();
        System.out.println("MainClass before DirectCounter.inject...");
        directCounter.inject();
        System.out.println("MainClass after DirectCounter.inject...");
        directCounter.print();

        ProviderCounter providerCounter = new ProviderCounter();
        providerCounter.inject();
        providerCounter.print();

        LazyCounter lazyCounter = new LazyCounter();
        lazyCounter.inject();
        lazyCounter.print();
        System.out.println("MainClass after lazyCounter.print()...");

        LazyCounters lazyCounters = new LazyCounters();
        lazyCounters.inject();
//        lazyCounters.print();
    }
}
