### Application Launch

* see this log first

```
at cn.**.MyApplication.onCreate(MyApplication.java:43)
	at android.app.Instrumentation.callApplicationOnCreate(Instrumentation.java:1015)
	at android.app.ActivityThread.handleBindApplication(ActivityThread.java:4834)
	at android.app.ActivityThread.access$1600(ActivityThread.java:168)
	at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1440)
	at android.os.Handler.dispatchMessage(Handler.java:102)
	at android.os.Looper.loop(Looper.java:150)
	at android.app.ActivityThread.main(ActivityThread.java:5659)
	at java.lang.reflect.Method.invoke(Native Method)
	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:822)
	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:712)
  ```
