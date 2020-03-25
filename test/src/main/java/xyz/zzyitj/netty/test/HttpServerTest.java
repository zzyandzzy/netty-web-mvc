package xyz.zzyitj.netty.test;

import xyz.zzyitj.netty.framework.server.HttpServer;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 10:05 下午
 * @email zzy.main@gmail.com
 */
public class HttpServerTest {
    public static void main(String[] args) throws Exception {
        new HttpServer().start();
    }
}
