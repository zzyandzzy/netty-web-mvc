package xyz.zzyitj.netty.test.service.impl;

import xyz.zzyitj.netty.framework.annotation.Service;
import xyz.zzyitj.netty.test.service.HelloService;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 1:00 下午
 * @email zzy.main@gmail.com
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return ("Hello " + name).toUpperCase();
    }

    @Override
    public String json(String name) {
        return "{\"name\": \"" + name + "\"}";
    }
}
