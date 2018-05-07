# JVM GC 算法简介

##### 综述 Overview
  窥探 Java 运行时内存模型， JVM 执行 GC 的时机，使用了哪些方法，如何执行。

##### Java 运行时数据区域
![RUNTIME DATA AREA](http://www.programering.com/images/remote/ZnJvbT1jbmJsb2dzJnVybD13WnVCbkx4UWpNNVFqTXhFek15RWpNNEFqTXhBak12Z0RNeUVETXk4Q2JoTldhMEZtY0Q5U2J2TjJYemQyYnNKbWJqOVNidk5tTHpkMmJzSm1iajV5Y2xkV1l0bDJMdm9EYzBSSGE.jpg)

Sample
```
public class ApplicationClass
  // 运行时， jvm 把 ApplicationClass 信息放入方法区
 {

  public static void mian(String[] args)
  {
      Sample clsA = new Sample("A");
      // clsA 作为引用，存放在栈区，ClassA 对象放到堆里面

      Sample clsB = new Sample("B");

      clsA.printName();
      clsB.printName();
  }
}

```

```
public class Sample
// 运行时，Sample 类信息存放到方法区
{
    private String name;
    // new Sample 实例化， name 引用放入栈区，name 对象放到堆

    public Sample(String name){
      this.name = name;
    }

    // print 方法放入方法区
    public void printName(){
      System.out.println(name);
    }
}

```

* Java 堆：容量大，存放对象实例，GC 堆
* 方法区：存储已被虚拟机加载的类信息、常量、静态变量、即时编译后的代码数据
* 虚拟机栈：虚拟机栈为线程私有，与线程同生命周期，为虚拟机执行 Java 方法服务，存储本地变量表，操作数栈等
* 本地方法栈为虚拟机使用的本地（Native）方法服务

  StackOverflowError(栈深度)

  OutOfMemoryError（栈可动态扩展，无法申请足够内存）
* 各线程共享的内存区域：**方法区**、**Java 堆**
* 各线程独有的内存区域：**程序计数器**、**虚拟机栈**、**本地方法栈**
<!-- ![Runtime-Data-Area](http://coding-geek.com/wp-content/uploads/2015/04/jvm_memory_overview.jpg) -->

## GC 算法和垃圾收集器
### 对象存活判定
1. 引用计数 —— 教科书算法

  每当一个对象有引用时，计数器 +1， 引用失效，计数器 -1.

  *问题：对象循环引用*

  ```
  objA.instance = objB;
  objB.instance = objA;

  objA = null;
  objB = null;

  System.gc();// 对象会回收吗？
  ```

2. 可达性分析——**实用**

  ![Object-accessability](https://plumbr.eu/wp-content/uploads/2015/05/Java-GC-mark-and-sweep.png)

  * GC Roots Set --> Reference Chain
  * GC Roots 对象有：
     * 虚拟机栈中的引用对象
     * 方法区中类静态属性引用对象
     * 方法区中常量引用的对象
     * 本地方法栈中 JNI（即 Native 方法）引用对象

### 垃圾收集算法
1. 标记-清除（Mark-Sweep）—— **基础**

  ![Mark-Sweep](http://3.bp.blogspot.com/-LNBSfvedEEM/VkxZomXBkvI/AAAAAAAAACI/EhsDaD0k-1g/s1600/output_oPN0rV.gif)

  [McCarthy, 1960]

  ```
   mark_sweep_collect() =
   mark(root)
   sweep()   
  ```

  **Mark**
  * 可达（accessable)，标记为 true
  * 不可达，标记为 false

    ```
    mark(o) =
     If mark-bit(o)=0
      mark-bit(o)=1
      For p in references(o)
        mark(p)
      EndFor
     EndIf
    ```

  **Sweep**
  * 标记为 true，修改为 false
  * 标记为 false，释放

    ```
    sweep()
     o = 0
     While o < N
      If mark-bit(o)=1
        mark-bit(o)=0
      Else
        free(o)
      EndIf
      o = o + size(o)
     EndWhile
     ```

     问题：时间复杂度 O(N)；内存碎片

1. 复制算法（Copying）—— **用空间换时间** / **新生代**

  ![Copying](http://zsuil.com/wp-content/uploads/2015/03/gc_copying.gif)

   [ Robert R. Fenichel, Jerome C. Yochelson, 1969]

  **Allocation**

  ```
  initialize() =
  	tospace = 0
  	fromspace = N/2
  	allocPtr = tospace

  allocate(n) =
  	If allocPtr + n > tospace + N/2
  		collect()
  	EndIf
  	If allocPtr + n > tospace + N/2
  	 	fail “insufficient memory”
  	EndIf
  	o = allocPtr
  	allocPtr = allocPtr + n
  	return o
  ```

    **Collect**

    ```
    collect() =
    	 swap(fromspace, tospace )
    	 allocPtr = tospace
    	 root = copy(root)
    ```

    复制操作
    [链接](http://kklin.farbox.com/post/cao-zuo-xi-tong/xu-ni-ji/3-gcfu-zhi-suan-fa)

    ```
    1 copying(){
    2   $free = $to_start
    3   for(r : $roots)
    4     *r = copy(*r)
    5
    6   swap($from_start, $to_start)
    7 }
    ```
    递归复制
    ```
    1 copy(obj){
    2   if(obj.tag != COPIED)   // 复制标识，避免多次复制
    3     copy_data($free, obj, obj.size)
    4     obj.tag = COPIED
    5     obj.forwarding = $free  // 新空间指针
    6     $free += obj.size
    7
    8     for(child : children(obj.forwarding))
    9       *child = copy(*child)
   10
   11  return obj.forwarding
   12 }
     ```
     对象存活率高，效率就低

3. 标记-整理算法（Mark-Compat）

 * [Mark - Moving - Sweep](http://www.cs.tau.ac.il/~maon/teaching/2014-2015/seminar/seminar1415a-lec2-mark-sweep-mark-compact.pdf)

4. 分代收集算法（Generational Collection）—— **商业虚拟机**

  时间和空间上分治

  根据对象存活周期划分内存区域：
 * 新生代
 * 老年代
 * 永久

### 垃圾收集器

###### Hotspot Heap Structure

  ![Mark-Sweep](https://databricks.com/wp-content/uploads/2015/05/Screen-Shot-2015-05-26-at-11.35.50-AM-1024x302.png)

  **GC Monitor**

  ```
  fujw$ jps
  5768
  7743 Jps
  fujw$ jstat -gc 5768 1000
  S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT   
  8704.0 8704.0 377.6   0.0   69952.0   6006.5   215544.0   195726.4  165976.0 159894.6 23344.0 21905.6    156    1.781   8      0.286    2.067
  8704.0 8704.0 377.6   0.0   69952.0   6006.5   215544.0   195726.4  165976.0 159894.6 23344.0 21905.6    156    1.781   8      0.286    2.067
  8704.0 8704.0 377.6   0.0   69952.0   6006.5   215544.0   195726.4  165976.0 159894.6 23344.0 21905.6    156    1.781   8      0.286    2.067
  ```

###### GC 时机
  * OutOfMemoryError 即将触发
  * 虚拟机配置：堆空间大小界限触发
  * GC 请求

###### 几个名词
  * STW(Stop The World): GC 执行时停顿所有 Java 执行线程
  * 安全点（SafePoint）:一个方法本地指令完成，在安全点才能执行 GC
    * 一般的 SafePoint 位置：方法调用、循环引用、异常跳转等
    * 主动式中断（Voluntary Suspension)：GC 设置中断标志，线程轮询读取标志
    * 问题：可能频繁 GC，线程 Sleep/Block —— 安全区域
  * 安全区域(SafeRegion)：引用关系不变的代码片段
    * 线程到达 SafeRegion, 标识自己

###### 一组参数：

  ```
  fujw$ jps -lvm
  5768
    -Dfile.encoding=UTF-8
    -XX:+UseConcMarkSweepGC
    -XX:HeapDumpPath=/Users/fujw/java_error_in_studio.hprof
    -Xms256m -Xmx1280m
    -XX:ReservedCodeCacheSize=240m
    -XX:+UseCompressedOops
    -Djb.vmOptionsFile=/Applications/Android Studio.app/Contents/bin/studio.vmoptions  
    -Didea.home.path=/Applications/Android Studio.app/Contents
    -Didea.executable=studio
    -Didea.platform.prefix=AndroidStudio
    -Didea.paths.selector=AndroidStudio2.3
  9561 sun.tools.jps.Jps -lvm
  -Dapplication.home=/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home
  -Xms8m
  8490 org.gradle.launcher.daemon.bootstrap.GradleDaemon 3.5
  -Xmx5120M
  -Dfile.encoding=UTF-8
  -Duser.country=CN -Duser.language=zh
  -Duser.variant
  ```

  * 更多 [Java HotSpot VM Options](http://www.oracle.com/technetwork/articles/java/vmoptions-jsp-140102.html)

###### 垃圾收集器
垃圾收集器历史：从 Serial 收集器 --> Parallel 收集器 --> CMS(Concurrent Mark Sweep) --> G1(Garbage First)

**基于 JDK 1.7 Update 14 之后的 Hotspot 虚拟机**

1. Serial 收集器
  * 单线程，简单高效
  * 新生代-Copying，Client 模式
  * STW
  * 老年代版本：Serial Old 收集器，Mark-Sweep

2. ParNew 收集器
  * Serial 的多线程版本
  * 新生代-copying，Server 模式首选
  * 和 CMS（来年代） 配合工作：ParNew（-XX:+UseConcMarkSweepGC 或 -XX:+UseParNewGC）
  * 多 CPU 对于 Serial 收集器 Serial 优势

        垃圾收集语境中的并行与并发：

        并行（Parallel）：多 GC 线程，用户线程等待

        并发（Concurrent）：GC/用户线程同时或交替执行，运行于不同 CPU

3. Parallel Scavenge 收集器
  * 吞吐量（Throughout）= 运行用户代码时间 / （运行用户代码时间 + GC 时间）
  * 适合后台运算
  * 参数：-XX:MaxGCPauseMillis, 最大 GC 停顿时间
  * 参数：-XX:GCTimeRatio，吞吐量
  * 老年代版本：Parallel Old 收集器，Mark-Compat

4. CMS 收集器

  ![CMS](https://www.cubrid.org/files/attach/images/1744/745/001/10ce40d924aebd3fc7a050dc7bcdba19.png)

  * 老年代，并发
  * 4 个步骤：
    * 初始标记，STW，仅标记 GC Roots 直接关联对象
    * 并发标记，程序运行，GC Roots Tracing
    * 重新标记，STW，修正并发标记期间变动部分对象
    * 并发清除，程序运行，清除工作并发执行
  * 缺点：
    * CPU 资源，并发设计通病，默认 GC 线程数（CPU 数 + 3）/ 4
    * 浮动垃圾，并发清理阶段产生的垃圾
    * 空间碎片，Mark-Sweep

5. G1 收集器 —— 面向服务端应用

  JDK 1.7 7u4 成熟商用
  * 并行与并发：充分利用多 CPU、多核环境
  * 分代收集：对不同生存期对象采用不同方式收集
  * 空间整合：基于 Mark-Compat 算法，局部基于 Copying 算法，不会产生碎片
  * 可预测的停顿：避免全区域 GC，跟踪评估各个 Region 回收能获得的空间和回收所需时间经验值，维护一个优先列表，根据允许收集时间，优先回收价值最大的 Region。

 5.1 G1 的内存模型

 ![G1-construct](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/G1GettingStarted/images/slide9.png)

 * 堆被等分为连续的区(1-32M)
 * 单个区同一个年代（eden, survivor, old），同一年代逻辑连续
 * 各年代大小是不定的（flexible）
 * 巨大的区——大型对象：超过半个区，或多个连续区

 5.2 G1 垃圾收集过程
  * 初始标记(Initial Marking),GC Roots scanning
  * 并发标记(Concurent Marking)
  * 最终标记(Final Marking)
  * 筛选回收(Live Data Counting and Evaluation)

  集中在有大量可回收对象的区域（Garbage first），依据用户设定的停顿时间（预测）对一定数量的区域进行回收（依据优先级，经验值）；

  5.2.1 Young Generation GC
  ![Y-GC](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/G1GettingStarted/images/slide11.png)
    * STW，所有用户线程停止工作
    * Young Generation 区非连续，需要时可调整大小
    * 并行（Parallel），多线程
    * 存活对象被复制或移动到 Survivor 区，达到年龄临界值，可被升级到 Old Generation 区

  5.2.2 G1 Old Generation Collection

  Liveness information：程序运行时并发计算

  和 CMS 收集器标记阶段类似，分为：
  * 初始标记， STW
  * GC Roots 扫描
  * 并发标记（Concurrent Marking），整个 heap ，可能被 Young GC 打断
  * 重新标记（Remark）STW，使用 snapshot-at-the-beginning (SATB) 算法
  * 清除（Cleanup）STW

  并发标记阶段
  ![O-GC](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/G1GettingStarted/images/slide14.png)
  如果发现空区（Empty region），在重新标记（Remark）阶段会被立即移除回收

  复制、清除阶段
  ![O-Copy/Cleanup](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/G1GettingStarted/images/slide16.png)
  * G1 会选择存活对象最少的区进行 GC，保证快速完成。这些区和 Young GC 同时执行
  * 回收和整理压缩

  GC 完成。

#### 工具  
  虚拟机性能监控工具和链接：

  [jps: 虚拟机进程状况工具](https://docs.oracle.com/javase/7/docs/technotes/tools/share/jps.html)

  [jstat: 虚拟机统计信息监视](https://docs.oracle.com/javase/7/docs/technotes/tools/share/jstat.html)

  [jmap: Java 内存映像工具](https://docs.oracle.com/javase/6/docs/technotes/tools/share/jmap.html)

  [jstack: Java 堆栈跟踪工具](https://docs.oracle.com/javase/7/docs/technotes/tools/share/jstack.html)

example：


#### 小结
  算法是方法论，垃圾收集器是算法的具体实现；垃圾收集器提供大量调节参数，可根据实际应用需求选择最优方式。

---

---
###### References
-
* 《深入理解 Java 虚拟机》第 2 版，周志明
* http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/G1GettingStarted/index.html
* https://plumbr.eu/handbook/garbage-collection-algorithms-implementations
* https://en.wikipedia.org/wiki/Cheney%27s_algorithm


-

---

<!-- ## 类文件结构和 Class Loading -->
