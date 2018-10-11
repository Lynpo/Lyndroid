package com.lynpo.java.operator;

/**
 * Create by fujw on 2018/9/30.
 * *
 * BitOperator
 */
public class BitOperator {

    private static final int MAXIMUM_CAPACITY = 1 << 30;

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

        bitWiseAndBitShiftOperators();
    }

    private static void bitWiseAndBitShiftOperators() {
        // 00101011 01010101 01110111 01010110  hash
        // 00010101 10101010 10111011 10101011  hash >>> 1
        // 00001010 11010101 01011101 11010101  hash >>> 2
        // 00000010 10110101 01010111 01110101  hash >>> 4
        // 00000000 00101011 01010101 01110111  hash >>> 8
        // 00000000 00000000 00101011 01010101  hash >>> 16

        // 00101011 01010101 01110111 01010110  hash
        // 00000000 00000000 00101011 01010101  hash >>> 16
        // 00101011 01010101 01011100 01000011  hash ^ hash >>> 16

        int a = 3;  // 0000 ... 0011
        int b = 5;  // 0000 ... 0101
        int c = -9; // 0000 ... 1001(9 的反码 + 1) -> 1111 ... 0110 + 1 -> 1111 ... 0111
        int complement_a = ~a;              // 11111111111111111111111111111100    -4   符号意义：按位取反
        int complement_b = ~b;              // 1111 ... 1010    -6
        int complement_c = ~c;              // 0000 ... 1000    8
        int signedLeftShift = a << 2;       // 0000 ... 1100    带符号左移
        int signedLeftShift_c = c << 2;     // 11111111111111111111111111011100   -36
        int signedRightShift = a >> 2;      // 0000 ... 0000    带符号右移
        int signedRightShift_b = b >> 2;    // 0000 ... 0000    带符号右移
        int signedRightShift_c = c >> 2;    // 11111111111111111111111111111101    -3
        int unsignedRightShift = a >>> 2;   // 0000 ... 0000    无符号右移
        int unsignedRightShift_b = b >>> 2; // 0000 ... 0001    无符号右移
        int unsignedRightShift_c = c >>> 2; // 00111111111111111111111111111101    1073741821
        int and = a & b;                    // 0000 ... 0001
        int exclusiveOr = a ^ b;            // 0000 ... 0110    异或
        int inclusiveOr = a | b;            // 0000 ... 0111    或

        int n_1 = 1;
        int n_1_signedLeftShift = n_1 << 31;
        int n_1_signedLeftShift32 = n_1 << 32;
        int n_1_signedLeftShift33 = n_1 << 33;
        int n_1_signedLeftShift_2 = n_1 << -2;
        int n_1_signedRightShift_2 = n_1 >> 2;
        System.out.println("1 signed left shift by 31 bit:" + n_1_signedLeftShift);     // -2147483648, 即 -2^32
        System.out.println("1 signed left shift by 32 bit:" + n_1_signedLeftShift32);   // 1
        System.out.println("1 signed left shift by 33 bit:" + n_1_signedLeftShift33);   // 2, 相当于环形移位；类推，n_1 << 34 = 4，n_1 << 5...
        System.out.println("1 signed left shift by -2 bit:" + n_1_signedLeftShift_2);   // 1073741824, 2^31, 相当于环形移位，反向移动：n_1 >> 2
        System.out.println("1 signed right shift by 2 bit:" + n_1_signedRightShift_2);  // 0

        System.out.println("a in binary:" + Integer.toBinaryString(a));
        System.out.println("b in binary:" + Integer.toBinaryString(b));
        System.out.println("c in binary:" + Integer.toBinaryString(c));

        System.out.println("complement_a:" + complement_a);
        System.out.println("complement_b:" + complement_b);
        System.out.println("complement_c:" + complement_c);

        System.out.println("complement_a in binary:" + Integer.toBinaryString(complement_a));
        System.out.println("complement_b in binary:" + Integer.toBinaryString(complement_b));
        System.out.println("complement_c in binary:" + Integer.toBinaryString(complement_c));

        System.out.println("signedLeftShift:" + signedLeftShift);
        System.out.println("signedLeftShift_c:" + signedLeftShift_c);
        System.out.println("signedLeftShift_c in binary:" + Integer.toBinaryString(signedLeftShift_c));

        System.out.println("signedRightShift:" + signedRightShift);
        System.out.println("signedRightShift_b:" + signedRightShift_b);
        System.out.println("signedRightShift_c:" + signedRightShift_c);
        System.out.println("signedRightShift_c in binary:" + Integer.toBinaryString(signedRightShift_c));

        System.out.println("unsignedRightShift:" + unsignedRightShift);
        System.out.println("unsignedRightShift_b:" + unsignedRightShift_b);
        System.out.println("unsignedRightShift_c:" + unsignedRightShift_c);
        System.out.println("unsignedRightShift_c in binary:" + Integer.toBinaryString(unsignedRightShift_c));

        System.out.println("and:" + and);
        System.out.println("exclusiveOr:" + exclusiveOr);
        System.out.println("inclusiveOr:" + inclusiveOr);

        int tableSizeAfterResize = tableSizeFor((1 << 29) + 1);
        System.out.println("tableSizeAfterResize:" + tableSizeAfterResize);
    }

    /**
     * Returns a power of two size for the given target capacity.
     * @param cap 返回值是大于 {@param cap} 的一个数，
     *            这个数满足：其值为 2 的 x 次幂，x 是满足 2^x > {@param cap} 的最小值
     *            ，且这个值的最大值为 MAXIMUM_CAPACITY
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;

        // what's the magic:
        // 00101011 00000000 00000000 00000110  capacity(capacity > 0)，中间共有 21 个 0
        // 00010101 10000000 00000000 00000011  capacity >>> 1，最高位 1 右移
        // 00111111 10000000 00000000 00000111  capacity |= capacity >>> 1，少了 1 个 0
        // 00001111 11100000 00000000 00000001  capacity >>> 2
        // 00111111 11100000 00000000 00000111  capacity |= capacity >>> 2，少了 2 个 0，共少了 3 个 0
        // 00000011 11111110 00000000 00000000  capacity >>> 4
        // 00111111 11111110 00000000 00000111  capacity |= capacity >>> 4，少了 4 个 0，共少了 7 个 0
        // 00000000 00111111 11111110 00000000  capacity >>> 8
        // 00111111 11111111 11111110 00000111  capacity |= capacity >>> 8，少了 8 个 0，共少了 15 个 0，还剩 6 个 0
        // 00000000 00000000 00111111 11111111  capacity >>> 16
        // 00111111 11111111 11111111 11111111  capacity |= capacity >>> 16，0 都没了

        // 01000000 00000000 00000000 00000000  capacity += 1, a power of two value
    }
}
