package com.lynpo.lynjava.basic.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Create by fujw on 2018/10/26.
 * *
 * NumberWith2Dot
 */
public class NumberDotStuff {

    public static void main(String[] args) {
//        long st = System.currentTimeMillis();
//        String result1 = getPrice2NoZero(1);
//        String result11 = getPrice2NoZero(12);
//        String result10 = getPrice2NoZero(7920);
//        String result2 = getPrice2NoZero(321);
//        String result = getPrice2NoZero(3115);
//        String result12 = getPrice2NoZero(3800);
//        System.out.println("result1:" + result1);
//        System.out.println("result11:" + result11);
//        System.out.println("result10:" + result10);
//        System.out.println("result2:" + result2);
//        System.out.println("result:" + result);
//        System.out.println("result12:" + result12);
//
//        long time1 = System.currentTimeMillis() - st;
//        System.out.println("method-getPrice2NoZero time:" + time1);

        long st2 = System.currentTimeMillis();
//        String result3 = getPriceByStringConcatenation(1);
        String result20 = getPriceByStringConcatenation(20);
//        String result31 = getPriceByStringConcatenation(12);
        String result30 = getPriceByStringConcatenation(7920);
//        String result4 = getPriceByStringConcatenation(321);
//        String result5 = getPriceByStringConcatenation(3115);
//        String result51 = getPriceByStringConcatenation(3800);
//        System.out.println("result3:" + result3);
        System.out.println("result20:" + result20);
//        System.out.println("result31:" + result31);
        System.out.println("result30:" + result30);
//        System.out.println("result4:" + result4);
//        System.out.println("result5:" + result5);
//        System.out.println("result51:" + result51);

//        long time2 = System.currentTimeMillis() - st2;
//        System.out.println("method-getPriceByStringConcatenation time:" + time2);

//        System.out.println("method-" + (time1 > time2 ? "getPriceByStringConcatenation" : "getPrice2NoZero") + "is more sufficient");
    }

    public static String getPrice2NoZero(int price) {
        BigDecimal priceBig = new BigDecimal(price);
        BigDecimal ratioBig = new BigDecimal(100);
        BigDecimal priceBigYuan = priceBig.divide(ratioBig);
        DecimalFormat decimalFormat = new DecimalFormat("##########.##");
        return decimalFormat.format(priceBigYuan);
    }

    public static String getPriceByCharOperate(int price) {
        if (price <= 0) {
            return "0";
        }

        String priceStr = String.valueOf(price);

        char[] chars = priceStr.toCharArray();
        int len = chars.length;

        if (price % 100 == 0) {
            return String.valueOf(price / 100);
        } else if (priceStr.length() == 1) {
            priceStr = "0.0" + priceStr;
        } else if (priceStr.length() == 2) {
            priceStr = "0." + priceStr;
        } else if (price % 10 == 0) {
            return String.valueOf(price / 100);
        } else {

        }
        return priceStr;
    }

    public static String getPriceByStringConcatenation(int price) {
        if (price <= 0) {
            return "0";
        }

        String priceStr = String.valueOf(price);

        if (price % 100 == 0) {
            return String.valueOf(price / 100);
        }

        int bit_to_move = 2;
        if (price % 10 == 0) {
            bit_to_move = 1;
            priceStr = String.valueOf(price / 10);

            if (priceStr.length() == 1) {
                priceStr = "0." + priceStr;
            } else {
                int len = priceStr.length();
                String preStr = priceStr.substring(0, len - bit_to_move);
                String nexStr = priceStr.substring(len - bit_to_move);
                priceStr = preStr + "." + nexStr;
            }
            return priceStr;
        }

        if (priceStr.length() == 1) {
            priceStr = "0.0" + priceStr;
        } else if (priceStr.length() == 2) {
            priceStr = "0." + priceStr;
        } else {
            int len = priceStr.length();
            String preStr = priceStr.substring(0, len - bit_to_move);
            String nexStr = priceStr.substring(len - bit_to_move);
            priceStr = preStr + "." + nexStr;
        }
        return priceStr;
    }
}
