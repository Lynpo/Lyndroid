package com.lynpo.java;

/**
 * Create by fujw on 2018/9/12.
 * *
 * IInterface
 */
public interface IInterface {

    IInterface INTERFACE = new IInterface() {
        @Override
        public void getName() {

        }

        private void doNothing() {
            // nothing to do.
        }
    };

    void getName();

    // After Java 8, method with body, default is required
    default void throwException(){
        throw new UnsupportedOperationException("exception");
    }
}
