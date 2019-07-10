### Broadcast

Reference: [broadcast](http://gityuan.com/2016/06/04/broadcast-receiver/)

##### 1. 主要角色

```
framework/base/services/core/java/com/android/server/
  - ActivityManagerService.java
  - BroadcastQueue.java
  - BroadcastFilter.java
  - BroadcastRecord.java
  - ReceiverList.java
  - ProcessRecord.java

framework/base/core/java/android/content/
  - BroadcastReceiver.java
  - IntentFilter.java

framework/base/core/java/android/app/
  - ActivityManagerNative.java (内含AMP)
  - ActivityManager.java
  - ApplicationThreadNative.java (内含ATP)
  - ActivityThread.java (内含ApplicationThread)
  - ContextImpl.java
  - LoadedApk
  ```





e.o.f.
