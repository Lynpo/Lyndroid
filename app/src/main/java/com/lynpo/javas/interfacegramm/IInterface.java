package com.lynpo.javas.interfacegramm;

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
}
