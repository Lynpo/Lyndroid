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

示例二：

```
public class NotInitialization2 {

    public static void main(String[] args) {
        SuperClass[] scs = new SuperClass[0];

        /*
        上述代码执行没有输出，说明 SuperClass 没有初始化，但出发了另外一个名为
        "[Lcom.lynpo.notelib.sample.classloading.SuperClass" 的类的初始化，
        它是由虚拟机自动生成、直接继承于 java.lang.Object 的子类，创建动作由字节码指令 newarray 触发。
         */
    }
}

```
通通过数组定义来引用类，不会触发此类的初始化。

示例三

```
public class ConstClass {

    static {
        System.out.println("ConstClass init!");
    }

    public static final String HELLO_WORLD = "Hello World";
}

public class NotInitialization3 {

    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO_WORLD);

        /*
        上述代码执行， 输出 Hello World
        没有输出，"ConstClass init!"。
        常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用到定义常量的类，因此不会触发定义常量的类的初始化。

        实际上 NotInitialization3 的 Class 文件之中并没有 ConstClass 类的符号引用入口，
        这两个雷在编译成 Class 文件后就不存在任何联系了。
         */
    }
}


```

接口的加载过程与类加载过程不同：接口初始化，接口中不能使用 “static{}” 语句块，但编译器会为接口生成“<clinit\>()” 类构造器，用于初始化接口中定义的成员变量。

接口与类真正区别是以上 5 种”有且仅有”需要初始化场景中的第 3 种：一个接口在初始化时，不要求其父接口都完成了初始化，只有在真正使用到父接口的时候（如引用接口中定义的常量）才会初始化。

### 类加载过程

##### 加载

“加载” 是 ”类加载”（Class Loading）过程的一个阶段，此阶段虚拟机需要完成以下 3 件事：
* 通过一个类的全限定名来获取定义此类的二进制字节流
* 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构
* 在内存中生成一个代表这个类的 java.lang.Class 对象，作为方法区这个类的各种数据的访问入口。

获取定义此类的二进制字节流的多种方式：
* 从 ZIP 包中读取，JAR, EAR, WAR 格式
* 从网络获取， Applet
* 运行时计算生成，动态代理技术，在 java.lang.reflect.Proxy 中，用了 ProxyGenerator.generateProxyClass 来为特定的接口生成形式为 "\*$Proxy" 的代理类的二进制字节流。
* 有其他文件生成， JSP 应用
* 从数据库中读取
* ……

相对于类加载过程的其他阶段，一个非数组类的加载阶段（准确地说，是加载阶段中获取类的二进制字节流的动作）是开发人员可控性最强的，因为加载阶段既可以使用系统提供的引导类加载器来完成，也可以由用户自定义的类加载器去完成，自定义类的加载器去控制字节流的获取方式（重写一个类加载器的 loadClass() 方法）。

对于数组类而言，数组类本身不通过类加载器创建，它是由 Java 虚拟机直接创建的。但数组类与类加载器人有密切关系，因为数组类的元素类型（Element Type，指的是数组去掉所有维度的类型）最终是要靠类加载器区创建，一个数组类 C 创建过程遵循规则：
* 如果数组的组件类型（Component Type， 指的是数组去掉一个维度的类型）是引用类型，那就递归采用定义的加载该过程去加载这个组件类型，数组 C 将在加载该组件类型的类加载器名称空间上被标识（一个类必须与类加载器一起确定唯一性）。
* 如果组件类型不是引用类型（如 int[]），Java 虚拟机将会把数组 C 标记为与引导类加载器关联
* 数组类的可见性与组件类型的可见性一致，如果组件类型不是引用类型，数组类的可见性默认为 public

加载阶段完成后，虚拟机外部的二进制字节流就按照虚拟机所需个事存储在方法区。然后在内存中实例化一个 java.lang.Class 对象（对于 HotSpot 而言，Class 对象比较特殊，存放在方法去里面），这个对象将作为程序访问方法区中的这些类型数据的外部接口。

加载阶段与连接阶段的部分内容（如一部分字节码文件格式验证动作）是交叉进行的，加载尚未完成，连接可能已经开始。

##### 验证

验证是连接阶段的第一步，目的是为确保 Class 文件的字节流中包含的信息符合当前虚拟机要求。

java.lang.VerifyError

Java 性对于 C/C++ 来说是相对安全的语言。

验证阶段 4 个验证动作
 1. 文件格式验证
  * 是否以魔数 0xCAFEBABE 开头
  * 主次版本号是否在当前虚拟机处理范围内
  * 常量池的常量中是否有不被支持的常量类型（检查常量 tag 标志）
  * 指向常量的各种索引值是否有指向不存在的常量或不符合类型的常量
  * CONSTANT_Utf8_info 型的常量中是否有不符合 UTF8 编码的数据
  * Class 文件中各个部分及文件本身是否有被删除的或附加的其他信息。
  * ……（不止这些）

  这个阶段的验证是基于二进制字节流进行的，通过后，字节流才会进入内存的方法去中存储。后面的 3 个验证阶段全部是基于方法区的存储结构进行的，不会再直接操作字节流。
 2. 元数据验证，对字节码描述的信息进行语义分析（语言规范），包括：
  * 这个类是否有父类（除 java.lang.Object 外，所有类都应有父类）
  * 这个类的父类是否继承了不被允许集成的类（ final 修饰）
  * 若此类不是抽象类，是否实现了其父类活接口中要求实现的所有方法。
  * 类中的字段、方法是否与父类产生矛盾（如覆盖父类 final 字段，出现不符合规则的方法重载，如方法参数都一致，但返回值类型却不同等）。
  * ……
 3. 字节码验证，最复杂的阶段，主要目的是通过数据流和控制流分析，确定程序语义合法、符合逻辑。在第二阶段对元数据信息中的数据类型校验后，此阶段对类方法体进行校验分析，确保方法运行时不会危害虚拟机安全：
  * 保证任意时刻操作数栈的数据类型与指令代码序列都能配合工作，如避免：在操作栈放置了一个 int 类型数据，使用时却按 long 类型来加载入本地变量表中。
  * 保证跳转指令不会跳转到方法体以外的字节码指令上。
  * 保证方法体中的类型转换是有效的。父类--子类 yes， 子类--父类 no
  * ……
 4. 符号引用验证，发生在虚拟机将符号引用转化为直接引用的时候，这个转化动作将在连接的第三阶段——解析阶段中发生。符号引用验证可看做是对类自身以外（常量池中的各种符号引用）的信息进行匹配性校验，通常需要校验：
  * 符号因中庸通过字符串描述的全限定名是否能找到对应的类。
  * 在指定类中是否存在符合方法的字段描述符以及简单名称所描述的方法和字段。
  * 符号引用中的类、字段、方法的访问性（private, protected, public, default) 是否可被当前类访问。
  * ……

  抛出异常：IncompatibleClassChangeError, IllegalAccessError, NoSuchFieldError, NoSuchMethodError 等。

  -Xverify:none 参数可关闭大部分类验证措施。

##### 准备

准备阶段正式为 **类变量** 分配内存并设置类变量初始值，这些变量所使用的内存都将在方法去中进行分配。

这时候进行内存分配的仅包括 **类变量（被 static 修饰的变量）** ，而不包括实例变量，实例变量将会在对象实例化时随着对象一起分配在 Java 堆中。

初始值 -- 零值

```
public static int value = 123;
```

初始值：0， 不是 123。因为此时尚未开始执行任何 Java 方法。而把 value 赋值为 123 的 putstatic 指令是程序被编译后，存放于类构造器 <clinit\>() 方法之中，所以把 value 赋值为 123 的动作将在初始化阶段才会执行。

表 基本数据类型的零值

| 数据类型 | 零值     | 数据类型 | 零值 |
| :------------- | :------------- |
| int       | 0        | boolean | false |
| long      | 0L       | float   | 0.0f  |
| short     | (short)0 | double  | 0.0d  |
| char      | '\u0000' | reference | null |
| byte      | (byte)0  | - | - |

如果类字段的字段属性表中存在 ConstantValue 属性，准备阶段变量 value 会被初始化为属性指定值

```
public static int value = 123;
```

准备阶段虚拟机就会根据 ConstantValue 的设置将 value 赋值为 123。

##### 解析

解析阶段是虚拟机将常量池的符号引用替换为直接引用的过程。

符号引用，Class 文件中有：CONSTANT_Class_info, CONSTANT_Fieldref_info, CONSTANT_Methodref_info 等类型。

解析阶段中直接引用与符号引用关联：
 * 符号引用（Symbolic Reference）：符号引用以一组符号来描述所引用的目标，符号可以是任何形式的字面量，只要使用时能无歧义地定位到目标即可。符号引用与虚拟机实现的内存布局无关，引用目标并不一定已经加载到内存中。
 * 直接引用（Direct Reference）：直接引用可以是直接指向目标的指针、相对偏移量或是一个能间接定位到目标的句柄。直接引用和虚拟机实现的内存布局是相关的，同一个符号引用在不同虚拟机实例上翻译出来的直接引用一般不会相同。如果有了直接引用，那引用的目标必定已经在内存中存在。

 虚拟机规范并未规定解析阶段具体时间。只要求在执行 anewarray 等 16 个用于操作符号引用的字节码指令之前，先对他们说是用的符号引用进行解析。

 对同一个符号引用进行多次解析请求是很常见的事情，除 invokedynamic 指令外，虚拟机实现可以对第一次解析的结果进行缓存（在运行时常量池中记录直接引用，并把常量标识为已解析状态）从而避免解析重复进行。

 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型、方法句柄和调用点限定符 7 类符号引用进行，分别应用于常量池的 CONSTANT_Class_info, CONSTANT_Fieldref_info, CONSTANT_Methodref_info, CONSTANT_InterfaceMethodref_info, CONSTANT_MethodType_info, CONSTANT_MethodHandle_info, CONSTANTInvokeDynamic_info 7 种常量类型。

前面 4 种引用的解析过程：
 1. 类或接口的解析

 设有 D 类，如果要把一个从未解析过的符号引用 N 解析为一个类或接口 C 的直接引用，那虚拟机完成整个解析的过程需要 3 个步骤：
  * 如果 C 不是一个数组类型，那虚拟机将会把代表 N 的全限定名传递给 D 的类加载器去家在这个类 C。在加载过程中，由于元数据验证、字节码验证需要，有可能粗发其他相关类的家在动作，如加载这个类的父类或实现的接口。一旦这个加载过程出现任何异常，解析过程失败。
  * 如果 C 是一个数组类型，并且数组类型为对象，也就是 N 的描述符会是类似“Ljava/lang/Integer”的形式，将按照前一条规则家在数组元素类型。如果 N 的描述符如前面所假设的形式，需要加载的元素类型就是“java.lang.Integer”，接着由虚拟机生成一个代表次数组维度和元素的数组对象。
  * 如果上面步骤无任何异常，那么 C 在虚拟机中实际上已经成为一个有效的类或接口了，但在解析完成之前还要进行符号引用验证，确认 D 是否具备对 C 的访问权限。如果发现不具备访问权限，将抛出 java.lang.IllegalAccessError 异常。

 2. 字段解析

   要解析一个未被解析过的字段符号引用，首先将会对字段表内 class_index 项中索引的 CONSTANT_Class_info 符号引用进行解析，也就是字段所属的类或接口的符号引用。此过程如果出现任何异常，字段解析失败。解析成功，这个字段所属类或接口用 C 表示，虚拟机规范要求按照以下步骤对 C 进行后续字段的搜索。
   * 如果 C 本身就包含了简单名称和字段描述符都与目标相匹配的字段，则返回这个字段的直接饮用，查找结束。
   * 否则，如果在 C 中实现了接口，将会按照继承关系从下往上递归搜索各个接口和它的父接口，如果接口中包含了简单名称和字段描述符都与目标相匹配的字段，则返回这个字段的直接引用，查找结束。
   * 否则，如果 C 不是 java.lang.Object 的话，将会按照继承关系从下往上递归搜索其父类，如果在父类中包含了简单名称和字段描述都与目标相匹配的字段，则返回这个字段的直接引用，查找结束。
   * 否则， 查找失败，跑出 java.lang.NoSuchFieldError 异常。

   如果查找成功返回引用，将对这个字段进行权限验证，如果发现不具备访问权限，抛出 java.lang.IllegalAccessError 异常。

   代码示例 字段解析：FieldResolution

       ```
       public class FieldResolution {

         interface Interface0 {
             int A = 0;
         }

         interface Interface1 extends Interface0 {
             int A = 1;
         }

         interface Interface2 {
             int A = 2;
         }

         static class Parent implements Interface1 {
             public static int A = 3;
         }

         static class Sub extends Parent implements Interface2 {
           //        public static int A = 4;
             public static int A = 4;
         }

         public static void main(String[] args) {
             System.out.println(Sub.A);

             // 如果注释掉 Sub 类中的 public static int A = 4;
             // 不能编译 Reference to 'A' is ambiguous, ...
         }
       }
       ```

 3. 类方法解析

 与字段解析一样，需要先解析方法表的 class_index 项中索引的方法所属的类或接口的符号引用。如果解析成功，这个字段所属类或接口用 C 表示，后续按如下步骤进行类方法搜索。
  * 类方法和接口方法符号引用的常量类型定义是分开的，如果在类方法中发现 class_index 中索引的 C 是个接口，抛出 java.lang.IncompatibleClassChangeError 异常。
  * 如果通过了第 1 步，在类 C 中查找是否有简单名称和描述符都与目标相匹配的方法，如果有则返回这个方法的直接引用，查找结束。
  * 否则，在类 C 的父类中递归查找是否有简单名称和描述符都与目标相匹配的方法，如果有则返回这个方法的直接引用，查找结束。
  * 否则，在 类 C 实现的接口列表及它们的父接口之中递归查找是否有简单名称和描述符都与目标相匹配的方法，如果存在匹配的方法，说明类 C 是一个抽象类，查找结束，抛出 java.lang.AbstractMethodError 异常。
  * 否则，宣告方法查找失败，抛出 java.lang.NoSuchMethodError。

  最后，如果查找过程返回了直接引用，将会对这方法进行权限验证，如果发现不具备对此方法的访问权限，抛出 java.lang.IllegalAccessError 异常。
 4. 接口方法解析

 接口方法也需要先解析出接口方法表的 class_index 项中索引的方法所属的类或接口的符号引用。如果解析成功，这个字段所属接口用 C 表示，后续按如下步骤进行接口方法搜索。
  * 与类方法解析不同，如果接口方法表中发现 class_index 中的索引 C 是各类而不是接口，直接抛出 java.lang.IncompatibleClassChangeError 异常。
  * 否则，在接口 C 中查找是否有简单名称和描述符都与目标相匹配的方法，如果有则返回这个方法的直接引用，查找结束。
  * 否则，在接口 C 的父接口中递归查找，直到 java.lang.Object 类（查找范围包括 Object 类）为止，看是否有简单名称和描述符都与目标相匹配的方法，如果有则返回这个方法的直接引用，查找结束。
  * 否则，宣告方法查找失败，抛出 java.lang.NoSuchMethodError。

  由于接口中所有方法默认都是 public 的，所以不存访问权限问题，因此此过程不会抛出 java.lang.IllegalAccessError 异常。

##### 初始化

类初始化过程是类加载过程的最后一步，前面的类加载过程中，除了在加载阶段用户应用程序可以通过自定义类加载器参与之外，其余动作完全有虚拟机主导和控制。到了初始化阶段，才真正开始执行定义的 Java 程序代码（或说字节码）。

准备阶段，变量已经赋过一次系统要求的初始值，而在初始化阶段，则根据程序员通过程序制定的主管计划去初始化类变量和其他资源，从另一个角度表达：初始化阶段是执行类构造器 <clinit\>() 方法的过程。

 * <clinit\>() 方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块（static{} 块）中的语句合并产生的，编译器收集的顺序由语句在源文件中出现的顺序决定，静态语句块中只能访问到定义在静态语句块之前的变量，定义在其后的变量，在前面的静态语句块可以赋值，但不能访问，代码示例：

```
public class Test {
  static {
    i = 0;
    System.out.println(i);
  }
  static int i = 1;
}
```

 * <clinit\>() 方法与类的构造函数（或者说是实例构造器 <init\>() 方法）不同，他不需要显式的调用父类构造器，虚拟机会保证在子类的 <clinit\>() 方法执行之前，父类的 <clinit\>() 方法已经执行完毕。因此在虚拟机中第一个被执行的 <clinit\>() 方法的类肯定是 java.lang.Object。
 * 由于父类的 <clinit\>() 方法先执行，意味着父类中定义的静态语句要优先于子类的变量复制操作，如代码清单：

 ```
 public class Parent {

    public static int A = 1;
    static {
        A = 2;
    }

    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);  //   输出 2，而不是 1
    }
}
 ```
 * <clinit\>()方法对于类或接口来说并不是必需的，如果一个类没有静态语句块，也没有对变量的赋值操作，那没编译器可以不为这个类生成 <clinit\>() 方法。
 * 接口中不能使用静态语句块，但仍然有变量初始化的赋值操作，因此接口与类一样都会生成 <clinit\>() 方法。不同是，执行接口的 <clinit\>() 方法不需要先执行父接口的 <clinit\>() 方法。只有当父接口中定义的变量使用时，父接口才会初始化。另外，接口的实现类在初始化时也一样不会执行接口的 <clinit\>() 方法。
 * 虚拟机会保证一个类的 <clinit\>() 方法在多线程环境中被正确的加锁、同步，如果多线程同时去初始化一个类，那么只会有一个线程去执行这个类的 <clinit\>() 方法，其他线程都需要阻塞等待，直到活动线程执行 <clinit\>() 方法完毕。如果一个类的 <clinit\>() 方法中有耗时很长的操作，就可能造成多个进程（此处“进程”可能应为“线程”？作者笔误？）阻塞（其他线程虽然会被阻塞，但如果执行 <clinit\>() 方法的那个线程退出 <clinit\>() 方法后，其他线程唤醒不会再次进入 <clinit\>() 方法，同一个类加载器下，一个类型只会初始化一次。），实际应用中这种阻塞往往是很隐蔽的。

 代码示例：

 ```
  public class DeadLoopClass {

      static {
          /*如果不加上这个 if 语句，编译器将提示 "Initializer must be able to complete normally"*/
          if (true) {
              System.out.println(Thread.currentThread() + "init DeadLoopClass");
              while (true) {

              }
          }
      }
  }

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
 ```

### 类加载器














E.O.F
