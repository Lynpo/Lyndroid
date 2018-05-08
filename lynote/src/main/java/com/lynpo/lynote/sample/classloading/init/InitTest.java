package com.lynpo.lynote.sample.classloading.init;

/**
 * Create by fujw on 2018/5/8.
 * *
 * InitTest
 */
public class InitTest {

    public static void main(String[] args) {
        Runnable script = () -> {
            System.out.println(Thread.currentThread() + "start");
            DeadLoopClass dlc = new DeadLoopClass();
            System.out.println(Thread.currentThread() + " RUN OVER");
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();

        // 运行输出：
        // Thread[Thread-1,5,main]start
        // Thread[Thread-0,5,main]start
        // Thread[Thread-1,5,main]init DeadLoopClass

        // 一条线程在死循环以模拟长时间操作，另外一条线程阻塞等待。
        // 只会有一个线程去执行类 DeadLoopClass 的 <clinit>() 方法
    }
}
