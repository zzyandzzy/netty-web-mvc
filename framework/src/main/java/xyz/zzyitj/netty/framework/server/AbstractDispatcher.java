package xyz.zzyitj.netty.framework.server;

import xyz.zzyitj.netty.framework.annotation.*;
import xyz.zzyitj.netty.framework.model.HandlerMapping;
import xyz.zzyitj.netty.framework.util.ClassScannerUtils;
import xyz.zzyitj.netty.framework.util.FrameworkUtils;
import xyz.zzyitj.netty.framework.util.ScannerClassException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 10:16 下午
 * @email zzy.main@gmail.com
 */
public abstract class AbstractDispatcher implements Dispatcher {

    /**
     * 保存配置文件
     */
    protected Properties properties = new Properties();

//    /**
//     * 保存扫描到的所有类名
//     */
//    protected List<String> classNameList = new ArrayList<>();

    /**
     * 核心IoC容器，保存所有初始化的Bean
     */
    protected Map<String, Object> ioc = new HashMap<>();

    /**
     * 保存url和方法的映射关系
     */
    protected Map<String, HandlerMapping> handlerMapping = new HashMap<>();

    public AbstractDispatcher() {
    }

    /**
     * 将文件读取到Properties对象中
     *
     * @param location 配置文件位置
     */
    public void doLoadConfig(String location) {
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(location);
            // 读取配置
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    /**
////     * 递归扫描出所有的Class文件
////     *
////     * @param packageName 包名
////     */
//    public void doScanner(String packageName) {
//        // 将所有的包路径转换为文件路径
//        URL url = getClass().getClassLoader().getResource(
//                packageName.replaceAll("\\.", "/"));
//        if (url != null) {
//            File dir = new File(url.getFile());
//            File[] files = dir.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    // 如果是文件夹，继续递归
//                    if (file.isDirectory()) {
//                        doScanner(packageName + "." + file.getName());
//                    } else {
//                        classNameList.add(packageName + "." +
//                                file.getName().replaceAll(".class", "").trim());
//                    }
//                }
//            }
//        }
//    }

    /**
     * 初始化所有相关的类，并放入到IoC容器之中。
     * IoC容器的key默认是类名首字母小写，如果是自己设置类名，则优先使用自定义的。
     */
    public void doInstance(String packageName) {
//        if (classNameList.size() == 0) {
//            return;
//        }
        try {
            Set<Class<?>> classSet = ClassScannerUtils.searchClasses(packageName);
            for (Class<?> clazz : classSet) {
                if (clazz.isAnnotationPresent(Controller.class)) {
                    String beanName = FrameworkUtils.toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName, clazz.newInstance());
                } else if (clazz.isAnnotationPresent(Service.class)) {
                    String beanName = FrameworkUtils.toLowerFirstCase(clazz.getSimpleName());
                    // 如果注解包含自定义名称
                    Service service = clazz.getAnnotation(Service.class);
                    if (!"".equals(service.value())) {
                        beanName = service.value();
                    }
                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    // 找类的接口
                    for (Class<?> i : clazz.getInterfaces()) {
                        if (ioc.containsKey(i.getName())) {
                            throw new Exception("The Bean Name Is Exist.");
                        }
                        ioc.put(i.getName(), instance);
                    }
                }
            }
        } catch (Exception | ScannerClassException e) {
            e.printStackTrace();
        }
//        try {
//            for (String className : classNameList) {
//                Class<?> clazz = Class.forName(className);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 将初始化到IOC容器中的类，需要赋值的字段进行赋值
     */
    public void doAutowired() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }
                // 获取注解对应的类
                Autowired autowired = field.getAnnotation(Autowired.class);
                String beanName = autowired.value().trim();
                // 获取 Autowired 注解的值
                if ("".equals(beanName)) {
                    beanName = field.getType().getName();
                }
                // 只要加了注解，都要加载，不管是 private 还是 protect
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(), ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将RequestMapping中配置的信息和Method进行关联，并保存这些关系。
     */
    public void initHandlerMapping() {
        if (ioc.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Class<?> clazz = entry.getValue().getClass();
            // 过滤掉没有加Controller的类
            if (!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }
            List<String> baseUrlList = new ArrayList<>();
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                baseUrlList = Arrays.asList(mapping.value());
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                // 过滤掉没有加RequestMapping的方法
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                    putHandlerMapping(baseUrlList, requestMapping, method);
                }
            }
        }
    }

    public void putHandlerMapping(List<String> baseUrlList, RequestMapping requestMapping, Method method) {
        baseUrlList.forEach(baseUrl -> {
            List<String> requestUrlList = Arrays.asList(requestMapping.value());
            requestUrlList.forEach(requestUrl -> {
                String url = ("/" + baseUrl + "/" + requestUrl).replaceAll("/+", "/");
                HandlerMapping mapping = new HandlerMapping(method, requestMapping.method(), requestMapping.produces());
                handlerMapping.put(url, mapping);
                System.err.println("method: " + Arrays.toString(requestMapping.method()) + ", mapping: " +
                        (FrameworkUtils.getWebUrl(HttpServer.localAddress, isSsl()) + url.replaceFirst("/", ""))
                        + ", " + method);
            });
        });
    }

    public String getHost() {
        String val = properties.getProperty("http.host");
        if (val == null) {
            return "localhost";
        }
        return val;
    }

    public int getPort() {
        String val = properties.getProperty("http.port");
        if (val == null) {
            return 8080;
        }
        return Integer.parseInt(val);
    }

    public boolean isSsl() {
        String val = properties.getProperty("http.ssl");
        if (val == null) {
            return false;
        }
        return Boolean.parseBoolean(val);
    }
}
