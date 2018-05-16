package com.lynpo.lynote.bytecodeexeengine.byteexeenginebasedonstack;

/**
 * Create by fujw on 2018/5/16.
 * *
 * ProcedureOfExecutionOnStack
 */
public class ProcedureOfExecutionOnStack {

    public static int calc() {
        int a = 100;
        int b = 200;
        int c = 300;
        return (a + b) * c;
    }

    public static void main(String[] args) {
        int cal = calc();
        System.out.println("result: " + cal);
    }
}
