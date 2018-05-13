package com.lynpo.lynote.bytecodeexeengine.methodinvocation;

/**
 * Create by fujw on 2018/5/13.
 * *
 * Dispatch
 */
public class Dispatch {
    static class QQ {
    }
    static class _360 {
    }
    public static class Father {
        public void hardChoice(QQ arg) {
            System.out.println("father choose qq");
        }
        public void hardChoice(_360 arg) {
            System.out.println("father choose _360");
        }
    }
    public static class Son extends Father {
        public void hardChoice(QQ arg) {
            System.out.println("son choose qq");
        }
        public void hardChoice(_360 arg) {
            System.out.println("son choose _360");
        }
    }

    public void println(String a) {
        System.out.println(a);
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();

        father.hardChoice(new _360());
        son.hardChoice(new QQ());

        new Dispatch().println("println");
        // RESULTS:
        // father choose _360
        // son choose qq
    }
}
