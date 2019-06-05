### Fragment

##### 几个角色

* 管理器：FragmentManager(由 FragmentManagerImpl 实现)
* 任务控制：FragmentTransaction
* 记录：FragmentRecorder(BackStackRecord)(extends FragmentTransaction)

##### ViewPager + PagerAdapter + Fragment

1. Fragment 的生命周期
ViewPager 默认缓存相邻两页 Fragment，滑动页面时，会新建不可见位置的 Fragment，
即便该 Fragment 在滑动过程中已经创建过。新建时 Fragment 的全部生命周期都会执行：
```
onAttach()
onCreate()
onCreateView()
onViewCreated()
onActivityCreated()
```



e.o.f
