### TCP/HTTP

##### TCP 三次握手：

| 连接次数     | C/S     | 报文 - 值 | 状态 |
| :------------- | :------------- | :------------- | :------------- |
| 建立连接       | C      | SYN - 1, SN - x | SYN_SENT |
| 二次握手       | S      | SYN, ACK(x+1) | SYN_RCVD |
| 三次握手       | C      | SYN+ACK,ACK(y+1),ACK | ESTABLISHED |

##### 四次挥手

不是每次连接都在数据收发后关闭连接，有一个超时时间，在超时时间内（keep-alive）可以继续下一次数据传输。

##### HTTP：

http://host[“:”port][abs_path]

C/S 模式
URI(URL): 资源路径
请求方法：GET/HEAD/POST/PUT/DELETE/TRACE/CONNECT/OPTIONS
HTTP 允许传输任意类型数据对象，有 Content-type 标识
无连接：限制每次连接只处理一个请求：C 请求，S 处理，收到 C 应答，断开。

请求报文：
1. 请求行：
Method Request-URI HTTP-Version CRLF (e.g.: GET http://blog.hihamlet.com/article/101101 HTTP/1.1
2. 报头：0 个或多个，（name:value)
3. 数据：不在 GET 中使用，在 POST 中使用。

响应报文
状态行（200 OK）、响应报头、空行、响应正文。

HttpURLConnection (> Android 2.3)


##### Volley

HttpStack
VERSION_SDK >= 9 ? HurlStack : HttpClientStack

缓存调度线程：CacheDispatcher， 1 个
网络调度线程：NetworkDispatcher，默认开启 4 个网络调度线程

RequestQueue.add(Request)



##### OkHttp

Android 4.4 开始，系统内置了 OkHttp

OkHttp 的 Callback.onResponse() 不是在 UI 线程回调。

源码解析

HttpClient.newCall() —> RealCall.newRealCall()
call.enqueue —> client.dispatcher.enqueue()

Dispatcher 任务调度

```
/** 最大并发请求数*/
private int maxRequests = 64;
/** 每个主机最大请求数*/
private int maxRequestsPerHost = 5;
private @Nullable Runnable idleCallback;

/** Executes calls. Created lazily. */    /** 消费者线程池*/
private @Nullable ExecutorService executorService;

/** Ready async calls in the order they'll be run. */    /** 将要运行的异步请求队列*/
private final Deque<AsyncCall> readyAsyncCalls = new ArrayDeque<>();

/** Running asynchronous calls. Includes canceled calls that haven't finished yet. */    /** 正在运行的异步请求队列*/
private final Deque<AsyncCall> runningAsyncCalls = new ArrayDeque<>();

/** Running synchronous calls. Includes canceled calls that haven't finished yet. */    /** 正在运行的同步请求队列*/
private final Deque<RealCall> runningSyncCalls = new ArrayDeque<>();

/** 构造*/
public Dispatcher(ExecutorService executorService) {
  this.executorService = executorService;
}

/**默认线程池，类似 CachedThreadPool*/
public synchronized ExecutorService executorService() {
  if (executorService == null) {
    executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp Dispatcher", false));
  }
  return executorService;
}
```

##### OkHttp 复用连接池

核心：用 Deque<RealConnection> 来存储连接

OkHttp 支持 5 个并发 Socket 连接，默认 keepAlive 5 分钟

ConnectionPool

```
/**
 * Background threads are used to cleanup expired connections. There will be at most a single
 * thread running per connection pool. The thread pool executor permits the pool itself to be
 * garbage collected.
 */    /**采用没有容量的 SynchronousQueue*/
private static final Executor executor = new ThreadPoolExecutor(0 /* corePoolSize */,
    Integer.MAX_VALUE /* maximumPoolSize */, 60L /* keepAliveTime */, TimeUnit.SECONDS,
    new SynchronousQueue<Runnable>(), Util.threadFactory("OkHttp ConnectionPool", true));

/** The maximum number of idle connections for each address. */     /**空闲 socket 最大连接数*/
private final int maxIdleConnections;
/**socket keepAlive 时间*/
private final long keepAliveDurationNs;
/**双向队列：同时具有队列和栈性质*/
private final Deque<RealConnection> connections = new ArrayDeque<>();
/**记录连接失败路线名单*/
final RouteDatabase routeDatabase = new RouteDatabase();
boolean cleanupRunning;

connections —> put, get, connectionBecameIdel, evictAll
```

引用计数：OkHttp 使用（类似）引用计数跟踪 socket 流的调用
RealConnection: socket 物理连接的包装，维护一个 List<Reference<StreamAllocation>>， list’s size 就是 socket 的引用计数

```
/** Current streams carried by this connection. */
public final List<Reference<StreamAllocation>> allocations = new ArrayList<>();
StreamAllocation—> acquire(add), release(remove)
```


##### Retrofit

3 类注解：
1. HTTP 请求方法注解：GET, PUT, …
2. 标记类注解： FormUrlEncoded, Multipart, Streaming(大文件使用)，数据默认加载到内存，大文件下载需要使用 Streaming
3. 参数类注解：Header, Body, Field, QueryMap, …

Retrofit 创建

Call 创建：Retrofit.create(ApiService.class) —> 返回一个 Proxy.newProxyInstance 动态代理对象
当 ApiService 的方法被调用时，会调用 InvocationHandler 的 invoke 方法
在 invoke 方法中：检查 serviceMethodCache 方法缓存(缓存对象为<ServiceMethod>，有则缓存，没有则创建并加入 serviceMethodCache

ServiceMethod
—> build(): createCallAdapter()—>createResponseConverter()—>parseMethodAnnotation()
