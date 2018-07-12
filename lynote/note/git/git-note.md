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

