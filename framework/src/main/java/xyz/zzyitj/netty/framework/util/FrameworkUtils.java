package xyz.zzyitj.netty.framework.util;

import io.netty.handler.codec.http.HttpUtil;

import java.net.InetSocketAddress;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 7:45 下午
 * @email zzy.main@gmail.com
 */
public class FrameworkUtils {
    /**
     * 首字母小写的方法
     *
     * @param name 名字
     * @return 首字母小写
     */
    public static String toLowerFirstCase(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }

    public static String getWebUrl(InetSocketAddress localAddress, boolean ssl) {
        return (ssl ? "https" : "http") + "://" + HttpUtil.formatHostnameForHttp(localAddress) + ":" + localAddress.getPort() + "/";
    }
}
