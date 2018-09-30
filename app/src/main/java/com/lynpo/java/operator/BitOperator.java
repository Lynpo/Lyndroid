package com.lynpo.java.operator;

/**
 * Create by fujw on 2018/9/30.
 * *
 * BitOperator
 */
public class BitOperator {

    public static void main(String[] args) {
        int bitmask = 0x000F;   // 0000 0000 0000 1111
        int val = 0x2222;       // 0010 0010 0010 0010
        System.out.println("bitmask:" + bitmask);
        System.out.println("val:" + val);
        // prints "2"
        System.out.println("val & bitmask: " + (val & bitmask));
        System.out.println("==========");
        //bitmask:15
        //val:8738
        //val & bitmask: 2

        System.out.println(0xff);
        // 255          0xff, int

        System.out.println("((byte) 0xff):" + ((byte) 0xff));   //((byte) 0xff):-1      1111 1111 --补码-->   1000 0001
        System.out.println("((byte) 0x8f):" + ((byte) 0x8f));   //((byte) 0x8f):-113    1000 1111 --补码-->   1111 0001
        System.out.println("((byte) 0x80):" + ((byte) 0x80));   //((byte) 0x80):-128    1000 0000 --补码-->   1111 1111 + 1   (1)1000 0000
        System.out.println("((byte) 0x7F):" + ((byte) 0x7F));   //((byte) 0x7F):127     0111 1111

        System.out.println(0xff >> 7);
        System.out.println(((byte) 0xff) >> 7);
        System.out.println(((byte) (((byte) 0xff) >> 7)));
        // 1
        //-1
        //-1

        System.out.println("==========");

        System.out.println(0xff >>> 7);
        System.out.println(((byte) 0xff) >>> 7);
        System.out.println((byte) (((byte) 0xff) >>> 7));
        //1             0xff, 11111111 >>> 7, 00000001, 1
        //33554431
        //-1
    }
}
