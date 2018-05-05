package com.lynpo.javakeywords.transientcase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Create by fujw on 2018/4/17.
 * *
 * TestTransient
 */
public class TestTransient {
    public static void main(String[] args) {
        // 此处需要改为你要存入的地址，注意 Win 下地址中的 \ 需要转义
        String src = "/Users/fujw/Desktop/demo.object";
        Account kingcos = new Account(62278888L, "kingcos", "123456", 1000.0);
        Account.staticVar = 11;
        System.out.println("序列化之前：");
        System.out.println(kingcos);
        System.out.println("staticVar = " + Account.staticVar);

        write(kingcos, src);
        Account.staticVar = 22;
        Account newKingcos = read(src);
        System.out.println("序列化之后：");
        System.out.println(newKingcos);
        System.out.println("staticVar = " + Account.staticVar);
    }

    static void write(Account acc, String src) {
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            os = new FileOutputStream(src);
            oos = new ObjectOutputStream(os);
            // 写入
            oos.writeObject(acc);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static Account read(String src) {
        Account acc = null;
        InputStream is = null;
        ObjectInputStream ois = null;
        try {
            is = new FileInputStream(src);
            ois = new ObjectInputStream(is);
            // 读取
            acc = (Account) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return acc;
    }
}
