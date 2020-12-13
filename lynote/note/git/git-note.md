### Git

##### Git flow

* git flow release 用于开始新版本开发
* git flow release 完成时会自动打上版本号 tag，且 tag 会合入 master 分支，
用于版本回溯
* git flow feature 默认从 develop 上检出版本
* feature 分支如果用于发版，要往 release 分支合并
* feature 分支如果不用于发（当前）版（可能用于下一个版本），可以往 develop 分支合并，
合并后的 develop 分支不会影响 release 分支（版本）
* 发版在 release 分支上打包，往后如果回溯版本，直接按 tag 检出分支（不用怀疑，此时
依据 tag 检出的分支不会因 develop 分支合入了 feature 分支代码而受影响。
* 上一条即是说，release 分支从检出到发版到打 tag，始终都在自身分支上完成，按此版本
tag 检出的分支只会有该 release 分支上的代码（直接在该分支提交的代码或其他分支合并入
的代码。）
* 综上，存在这样一种（合理）状态：release 完成后（被删除），master 分支上标记了该
 release 版本 tag，master 代码可能不同于此时 develop 的代码（如果 develop 分支
 合入了某一个未用于 release 版本的 feature 分支的话。）

##### Git reverse(Source tree)

* Source tree 提交回滚：回滚一次 merge 操作可以完全回滚合入分支的屡次（所有差异）提交，
可以放心使用。

##### Git submodle

###### 1. 创建

[git-book: Starting with Submodules](https://git-scm.com/book/en/v2/Git-Tools-Submodules)
```
$ git submodule add https://github.com/chaconinc/DbConnector
Cloning into 'DbConnector'...
remote: Counting objects: 11, done.
remote: Compressing objects: 100% (10/10), done.
remote: Total 11 (delta 0), reused 11 (delta 0)
Unpacking objects: 100% (11/11), done.
Checking connectivity... done.
```

###### 2. push

[Stackoverflow: push a submodle](https://stackoverflow.com/questions/5814319/git-submodule-push)

```
$ cd your_submodule
$ git checkout master
<hack,edit>
$ git commit -a -m "commit in submodule"
$ git push
$ cd ..
$ git add your_submodule
$ git commit -m "Updated submodule"
```

###### 3. 删除

[Stackoverflow: remove a submodle](https://stackoverflow.com/questions/1260748/how-do-i-remove-a-submodule)

```
1. Delete the relevant section from the .gitmodules file.
2. Stage the .gitmodules changes:
  git add .gitmodules
3. Delete the relevant section from .git/config.
4. Remove the submodule files from the working tree and index:
  git rm --cached path_to_submodule (no trailing slash).
5. Remove the submodule's .git directory:
  rm -rf .git/modules/path_to_submodule
6. Commit the changes:
  git commit -m "Removed submodule <name>"
7. Delete the now untracked submodule files:
  rm -rf path_to_submodule
```
