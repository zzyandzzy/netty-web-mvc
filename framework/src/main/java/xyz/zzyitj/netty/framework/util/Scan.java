package xyz.zzyitj.netty.framework.util;

import java.util.Set;
import java.util.function.Predicate;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 6:26 下午
 * @email zzy.main@gmail.com
 */
public interface Scan {

    String CLASS_SUFFIX = ".class";

    Set<Class<?>> search(String packageName, Predicate<Class<?>> predicate) throws ScannerClassException;

    default Set<Class<?>> search(String packageName) throws ScannerClassException {
        return search(packageName, null);
    }
}
