package xyz.zzyitj.netty.framework.util;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 6:25 下午
 * @email zzy.main@gmail.com
 */
public class ClassScannerUtils {
    public static Set<Class<?>> searchClasses(String packageName)
            throws ScannerClassException {
        return searchClasses(packageName, null);
    }

    public static Set<Class<?>> searchClasses(String packageName, Predicate predicate)
            throws ScannerClassException {
        return ScanExecutor.getInstance().search(packageName, predicate);
    }
}
