### LinkedList & ArrayList

##### 功能概述

| Option | LinkedList | ArrayList     |
| :------------- | :------------- | :------------- |
| 实现接口 | List, Deque, Cloneable, java.io.Serializable       |List, RandomAccess, Cloneable, java.io.Serializable|
| 数据结构   | 双向链表  |对象数组|
| 容量 | size       | size |
| 相邻元素  | Node.prev, Node.next | - |
| 按索引获取元素  | get(index) | get(index) |
| 增删改查  | 增,删(remove(index))-O（1）；改删(remove(Object)),查-O(n) | 数组操作，增改查-常量时间-O（1）；删-线性时间（数组拷贝）-O（n) |
| 内部类  | ListItr(imp ListIterator), Node, LLSpliterator(imp Iterator), DescendingIterator(imp Iterator) | Itr, ListItr(imp ListIterator), SubList, ArrayListSpliterator(imp Iterator) |
