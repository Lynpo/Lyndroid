package com.lynpo.lynote.oom;

/**
 * Create by fujw on 2018/5/19.
 * *
 * JavaVMStackSOF
 *
 * VM Args: -Xss128k
 *
 * $ java -version
 * java version "1.8.0_161"
 * Java(TM) SE Runtime Environment (build 1.8.0_161-b12)
 * Java HotSpot(TM) 64-Bit Server VM (build 25.161-b12, mixed mode)
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }

        // run-result(default):
        // stack length:16338
        // Exception in thread "main" java.lang.StackOverflowError
        //	at com.lynpo.lynote.oom.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:17)
        //	at com.lynpo.lynote.oom.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:17)

        // configs: -Xss128k
        // The stack size specified is too small, Specify at least 160k
        // Error: Could not create the Java Virtual Machine.
        // Error: A fatal exception has occurred. Program will exit.

        // configs: -Xss256k
        // Exception in thread "main" java.lang.StackOverflowError
        // stack length:2449
        //	at com.lynpo.lynote.oom.JavaVMStackSOF.stackLeak(JavaVMStackSOF.java:20)
    }
}
