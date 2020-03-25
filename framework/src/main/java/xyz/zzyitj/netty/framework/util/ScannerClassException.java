package xyz.zzyitj.netty.framework.util;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 6:27 下午
 * @email zzy.main@gmail.com
 */
public class ScannerClassException extends Throwable {
    public ScannerClassException(String message, ClassNotFoundException e) {
        e.printStackTrace();
    }
}
