package com.lynpo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Create by fujw on 2018/7/10.
 * *
 * ApkUtil
 */
public class ApkUtil {

    private static char[] base_cs =new char[16];

    //将Byte转换成HexString 辅助函数
    static {
        for (int i=0;i < 10;i++) {
            base_cs[i] = (char) ('0' + i);
        }
        for (int i=10;i < 16;i++) {
            base_cs[i] = (char) ('A' + i - 10);
        }
    }

    private static String toHex(byte[] bs) {
        char[] cs=new char[bs.length * 2];
        int x;
        for (int i=0;i < bs.length;i++) {
            x = bs[i] & 0xff;
            cs[2 * i] = base_cs[x / 16];
            cs[2 * i + 1] = base_cs[x % 16];
        }
        return new String(cs);
    }

    public static String getCertSHA1(Context context) {
        try {
            //获取包管理器
            PackageManager packageManager=context.getPackageManager();
            //获取包名
            String packageName=context.getPackageName();
            //获得包信息
            PackageInfo pis = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            //获得签名
            Signature[] signs = pis.signatures; //签名
            //获得签名数组的第一位
            Signature sign=signs[0];
            //获得X.509证书工厂
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            byte[] signBytes=sign.toByteArray();
            ByteArrayInputStream byteIn=new ByteArrayInputStream(signBytes);
            //获取X509证书
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(byteIn);
            //获取证书发行者SHA1
            MessageDigest sha1=MessageDigest.getInstance("SHA1");
            byte[] certByte=cert.getEncoded();
            byte[] bs=sha1.digest (certByte);
            return toHex(bs);
        } catch (Exception ignored) {

        }
        return null;
    }
}
