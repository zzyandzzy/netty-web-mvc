package xyz.zzyitj.netty.framework.server;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 10:13 下午
 * @email zzy.main@gmail.com
 */
public interface Dispatcher {
    // 1. 加载配置文件
    void doLoadConfig(String location);

    // 2. 扫描所有相关的类
//    void doScanner(String packageName);

    // 3. 初始化所有相关的类并且保存到IoC容器里面
    void doInstance(String packageName);

    // 4. 依赖注入
    void doAutowired();

    // 5. 构造HandlerMapping
    void initHandlerMapping();
}
