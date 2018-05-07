# 虚拟机类加载机制

Java 天生可以动态扩展的语言特性就是依赖运行期动态加载和动态连接这个特点实现的。

### 类加载的时机

类从被加载到虚拟机内存开始，到卸载出内存，生命周期包括：

加载 Loading，验证 Verification， 准备 Preparation， 解析 Resolution，

初始化 Initialization，使用 Using，卸载 Unloading

7 个阶段。

加载、验证、准备、初始化、卸载 5 各阶段顺序执行

解析阶段不一定：可以在初始化之后。且以上所有阶段开始后，有可能互相交叉混合式进行，通常会在一个阶段执行过程中调用、激活另一个阶段。

Java 虚拟机规范没有强行约束类加载时机，具体虚拟机自有把握。

对于初始化阶段，虚拟机规范严格规定，**有且只有** 5 种情况必须立即对类进行”初始化“（而加载、验证、准备、自然需要在此之前开始）：

 * 遇到 new, getstatic, putstatic, invokestatic 这 4 条字节码指令时，若未初始化，则需初始化。生成这 4 条指令最常见的 Java 代码场景：使用 new 关键字创建对象、读取或设置一个类的静态字段（被 final 修饰、已在编译期把结果放入常量池的静态字段除外）的时候，以及调用一个类的静态方法时。
 * 使用 java.lang.reflect 包的方法对类进行反射调用时，若未初始化，则需初始化。
 * 当初始化一个类时，如果发现其父类未初始化，则需先触发其父类初始化。
 * 当虚拟机启动时，用户需要指定一个要执行的主类（包含 main（）方法的类），虚拟机会先初始化此类。
 * 当使用 JDK 1.7 的动态语言支持时，如果一个 java.lang.invoke.MethodHandle 实例最后的解析结果 REF_getStatic, REF_putStatic, REF_invokeStatic 的方法句柄，并且这个方法句柄所对应的类没有初始化，则需先触发其初始化。

代码实验：

```
public class SuperClass {

    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;
}

public class SubClass extends SuperClass {

    static {
        System.out.println("SubClass init!");
    }
}

public class NotInitialization {

    public static void main(String[] args) {
        System.out.println(SubClass.value);
        // 输出：
        // SuperClass init!
        // 123
    }
}

```

上述代码运行输出

```
SuperClass init!
123

```

不会输出 "SubClass init!"。结论：对于静态字段，只有直接定义这个字段的类才会被初始化。

















E.O.F
