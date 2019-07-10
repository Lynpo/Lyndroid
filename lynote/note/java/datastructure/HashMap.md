### HashMap

##### 0. HashMap 设计思想

 **类比 hash 表索引，为了更快的搜索定位键值数据**

##### 1. 继承关系

* 继承自：AbstractMap
* 实现 Map
  * get, put

##### 2. 成员

* Node 数组：Node<K,V>[] table; 数组 + 链表（容量大于 64 时为红黑树）

###### 2.1 Node 数据结构

* Node
* TreeNode -(extend)-> LinkedHashMap.LinkedHashMapEntry -(extend)-> Node


##### 3. 扩容机制

###### 3.1 put() 方法 --> putVal() 方法

```
/**
 * Implements Map.put and related methods
 *
 * @param hash hash for key
 * @param key the key
 * @param value the value to put
 * @param onlyIfAbsent if true, don't change existing value
 * @param evict if false, the table is in creation mode.
 * @return previous value, or null if none
 */
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    // 未初始化则初始化，n 位 tab 容量，是 2 的 x 次幂
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    // 篮子（桶）中无此值，新建：n 是 2 的 x 次幂，（n - 1 & hash）将 hash 截取出低 x 位
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
      // 篮子（桶）中有此值，比较 hash，key
        Node<K,V> e; K k;
        // hash 及 key 均相同，e 指向 p，更新 value
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode)
            // 已经树化：执行树的扩容操作，e 指向树化后新节点
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            // 尚未树化树化：遍历链表
            for (int binCount = 0; ; ++binCount) {
                // e = p.next，链表中当前节点 p 无后续节点，新建并存入，退出循环
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // 检测是否满足树化条件，满足则树化，退出循环
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                // 与下一节点 hash 或 key 相同，则更新 value，退出循环
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                // 下一节点不为 nul（且 hash 及 key 均未冲突），当前节点指针指向下一节点
                p = e;
            }
        }

        // 更新 value
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;

    // size 自增，扩容检测
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}

```

###### 3.1.1 TreeNode 的 putTreeVal() 方法

```
/**
 * Tree version of putVal.
 */
final TreeNode<K,V> putTreeVal(HashMap<K,V> map, Node<K,V>[] tab,
                               int h, K k, V v) {
    Class<?> kc = null;
    boolean searched = false;
    // 找到根节点
    TreeNode<K,V> root = (parent != null) ? root() : this;
    // 从根节点遍历树，p 指向根节点
    for (TreeNode<K,V> p = root;;) {
        // dir for direction，表示插入方向，ph:parent hash, pk:parent key
        int dir, ph; K pk;
        // 插入 hash 小于根节点，插入左子树
        if ((ph = p.hash) > h)
            dir = -1;
        // 插入 hash 大于根节点，插入右子树
        else if (ph < h)
            dir = 1;
        // 如果 key 值相同，返回 p （供后续操作，在 putVal 方法中，后续更新 value）
        else if ((pk = p.key) == k || (k != null && k.equals(pk)))
            return p;

        // kc：k 所属 Class，是否可比较（是否实现 Comparable 接口）
        // 如果 kc 为 null 且 kc
        else if ((kc == null &&
                  (kc = comparableClassFor(k)) == null) ||
                 (dir = compareComparables(kc, k, pk)) == 0) {
            if (!searched) {
                TreeNode<K,V> q, ch;
                searched = true;
                if (((ch = p.left) != null &&
                     (q = ch.find(h, k, kc)) != null) ||
                    ((ch = p.right) != null &&
                     (q = ch.find(h, k, kc)) != null))
                    return q;
            }
            dir = tieBreakOrder(k, pk);
        }

        TreeNode<K,V> xp = p;
        // p 指向子节点
        if ((p = (dir <= 0) ? p.left : p.right) == null) {
            Node<K,V> xpn = xp.next;
            TreeNode<K,V> x = map.newTreeNode(h, k, v, xpn);
            if (dir <= 0)
                xp.left = x;
            else
                xp.right = x;
            xp.next = x;
            x.parent = x.prev = xp;
            if (xpn != null)
                ((TreeNode<K,V>)xpn).prev = x;
            moveRootToFront(tab, balanceInsertion(root, x));
            return null;
        }
    }
}
```

###### 3.2 HashMap 中关于红黑树的三个关键参数

```
//一个桶的树化阈值
//当桶中元素个数超过这个值时，需要使用红黑树节点替换链表节点
//这个值必须为 8，要不然频繁转换效率也不高
static final int TREEIFY_THRESHOLD = 8;

//一个树的链表还原阈值
//当扩容时，桶中元素个数小于这个值，就会把树形的桶元素 还原（切分）为链表结构
//这个值应该比上面那个小，至少为 6，避免频繁转换
static final int UNTREEIFY_THRESHOLD = 6;

//哈希表的最小树形化容量
//当哈希表中的容量大于这个值时，表中的桶才能进行树形化
//否则桶内元素太多时会扩容，而不是树形化
//为了避免进行扩容、树形化选择的冲突，这个值不能小于 4 * TREEIFY_THRESHOLD
static final int MIN_TREEIFY_CAPACITY = 64;
```


##### 4. 增删改查

##### 5. 并发


e.o.f
