package xyz.zzyitj.netty.framework.util;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 6:26 下午
 * @email zzy.main@gmail.com
 */
public class ScanExecutor implements Scan {

    private volatile static ScanExecutor instance;

    @Override
    public Set<Class<?>> search(String packageName, Predicate<Class<?>> predicate)
            throws ScannerClassException {
        Scan fileSc = new FileScanner();
        Set<Class<?>> fileSearch = fileSc.search(packageName, predicate);
        Scan jarScanner = new JarScanner();
        Set<Class<?>> jarSearch = jarScanner.search(packageName, predicate);
        fileSearch.addAll(jarSearch);
        return fileSearch;
    }

    private ScanExecutor() {
    }

    public static ScanExecutor getInstance() {
        if (instance == null) {
            synchronized (ScanExecutor.class) {
                if (instance == null) {
                    instance = new ScanExecutor();
                }
            }
        }
        return instance;
    }

}