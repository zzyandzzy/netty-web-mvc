package xyz.zzyitj.netty.test.controller;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import xyz.zzyitj.netty.framework.annotation.*;
import xyz.zzyitj.netty.framework.http.HttpResponse;
import xyz.zzyitj.netty.framework.util.MediaType;
import xyz.zzyitj.netty.test.model.User;
import xyz.zzyitj.netty.test.service.HelloService;

import java.util.List;
import java.util.Map;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 10:06 下午
 * @email zzy.main@gmail.com
 */
@Controller
@RequestMapping(value = {"/demo", "/"})
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello",
            method = RequestMethod.GET,
            produces = {MediaType.TEXT_PLAIN_VALUE, MediaType.CHARSET_UTF8_VALUE})
    public String sayHello(HttpRequest request) {
        String name = "Netty";
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = queryStringDecoder.parameters();
        if (!params.isEmpty()) {
            name = params.get("name").get(0);
        }
        return helloService.hello(name);
    }

    @RequestMapping(value = {"/get", "/getUser"},
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(HttpRequest request, HttpResponse response) {
        return new User("intent", "zzy.main@gmail.com", true, (short) 22);
    }
}
