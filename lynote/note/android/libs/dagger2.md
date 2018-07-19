### Dagger2

@Inject, @Component, @Module

1. 常规注入
2. 需要注入抽象类: 通过 module provide 创建抽象类
    使用 @Module @Provide


@Named, @Qualifier

1. 场景： 两个相同的依赖，继承自同一个超类或实现统一接口
2. 用法： demo
