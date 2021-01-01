
### 文件查找，文件数统计

```
<!-- 查找文件 -->
find . -type f | wc -l

<!-- 按文件后缀查找文件数 -->
ls *.java | wc -l
ls -lR . | grep ".kt$" | wc -l
ls -lR . | grep ".java$" | wc -l
ls -lR . | grep ".xml$" | wc -l
find . -type f | sed -n 's/..*\.//p' | sort | uniq -c

```
