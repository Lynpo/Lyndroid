package com.lynpo.lynote.classloading.loader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create by fujw on 2018/5/9.
 * *
 * ClassLoaderTest
 *
 * 类加载器与 instanceof 关键字演示：
 *
 * 创建一个类加载器去加载一个与自己在同一路径下的 Class 文件并实例化，
 * 这个对象却与 ClassLoaderTest 不同，原因：
 *
 * 虚拟机中存在了两个 ClassLoaderTest 类，一个是由系统应用程序类加载器加载的，另一个是有自定义类加载器加载的。
 */
public class ClassLoaderTest {

//    public ClassLoader getClassLoader() {
//        ClassLoader cl = getClassLoader0();
//        if (cl == null) {
//            return null;
//        }
//        SecurityManager sm = System.getSecurityManager();
//        if (sm != null) {
//            ClassLoader scl = ClassLoader.getSystemClassLoader();
//            if (scl != null && scl != cl) {
//
//            }
//        }
//    }
//
//    private ClassLoader getClassLoader0() {
//        return null;
//    }

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }

                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = classLoader.loadClass("com.lynpo.lynote.classloading.loader.ClassLoaderTest").newInstance();

        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);

        // 运行结果：
        // class com.lynpo.lynote.classloading.loader.ClassLoaderTest
        // false
    }
}
