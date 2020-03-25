# 基于Netty的Web Mvc

实现了IoC容器，依赖注入和Spring的一些注解。

# 功能

- 自定义RequestMapping
- 依赖注入

# 使用

- 配置文件

需要指定包扫描路径`http.scanPackage`

```properties
http.scanPackage=xyz.zzyitj.netty.test
http.host=localhost
http.port=8080
http.ssl=false
```

- Controller

```java
@Controller
@RequestMapping(value = {"/demo", "/"})
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello",
            method = RequestMethod.GET,
            produces = {MediaType.TEXT_PLAIN_VALUE, MediaType.CHARSET_UTF8_VALUE})
    public String sayHello(HttpRequest request) {
        return helloService.hello("Intent");
    }
}
```

- 启动
```java
public class HttpServerTest {
    public static void main(String[] args) throws Exception {
        new HttpServer().start();
    }
}
```
更多请看: [demo](./test/src/main/java/xyz/zzyitj/netty/test/HttpServerTest.java)

License
-------

    MIT License
    
    Copyright (c) 2020 intent
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
