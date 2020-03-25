package xyz.zzyitj.netty.framework.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import xyz.zzyitj.netty.framework.annotation.RequestMethod;
import xyz.zzyitj.netty.framework.http.HttpResponse;
import xyz.zzyitj.netty.framework.model.HandlerMapping;
import xyz.zzyitj.netty.framework.util.FrameworkUtils;

import java.io.Serializable;
import java.lang.reflect.Method;

import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 10:21 下午
 * @email zzy.main@gmail.com
 */
public class HttpDispatcher extends AbstractDispatcher implements Serializable {

    private static final long serialVersionUID = -3659893241533608883L;

    private HttpRequest request;
    private HttpResponse response;

    public HttpDispatcher() {
        init();
    }

    private void init() {
        // 1. 加载配置文件
        doLoadConfig("application.properties");
        // 2. 扫描所有相关的类
//        doScanner(properties.getProperty("http.scanPackage"));
        // 3. 初始化所有相关的类并且保存到IoC容器里面
        doInstance(properties.getProperty("http.scanPackage"));
        // 4. 依赖注入
        doAutowired();
    }

    public void doDispatch(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            this.request = (HttpRequest) msg;
            this.response = new HttpResponse(ctx);

            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx, response);
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            if (content.isReadable()) {
                System.out.println(content.toString(CharsetUtil.UTF_8));
            }
            if (msg instanceof LastHttpContent) {
                LastHttpContent trailer = (LastHttpContent) msg;
                if (!trailer.trailingHeaders().isEmpty()) {
                    for (CharSequence name : trailer.trailingHeaders().names()) {
                        for (CharSequence value : trailer.trailingHeaders().getAll(name)) {
                            System.out.println("TRAILING HEADER: ");
                            System.out.println("name: " + name + " = " + value);
                        }
                    }
                }
                doHttp();
            }
        }
    }

    private void doHttp() {
        if (handlerMapping.isEmpty()) {
            return;
        }
        String url = request.uri();
        url = url.replaceAll("/+", "/").replaceAll("\\?.*", "");
        if (!handlerMapping.containsKey(url)) {
            notfound();
            return;
        }
        HandlerMapping mapping = handlerMapping.get(url);
        boolean contains = false;
        for (RequestMethod requestMethod : mapping.getRequestMethods()) {
            if (requestMethod.getName().equals(request.method().name())) {
                contains = true;
            }
        }
        if (!contains) {
            notfound();
            return;
        }
        try {
            response.setStatus(OK);
            Method method = mapping.getMethod();
            String beanName = FrameworkUtils.toLowerFirstCase(method.getDeclaringClass().getSimpleName());
            // 利用反射调用方法
            Object result;
            if (method.getParameterCount() == 0) {
                result = method.invoke(ioc.get(beanName));
            } else if (method.getParameterCount() == 1) {
                if (method.getParameterTypes()[0].getSimpleName().equals(HttpRequest.class.getSimpleName())) {
                    result = method.invoke(ioc.get(beanName), request);
                } else if (method.getParameterTypes()[0].getSimpleName().equals(HttpResponse.class.getSimpleName())) {
                    result = method.invoke(ioc.get(beanName), response);
                } else {
                    throw new Exception("method: " + method + ", parameter arg error!");
                }
            } else if (method.getParameterCount() == 2) {
                result = method.invoke(ioc.get(beanName), request, response);
            } else {
                throw new Exception("method: " + method + ", parameter count error!");
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mapping.getProduces().length; i++) {
                sb.append(mapping.getProduces()[i]);
                if (i != mapping.getProduces().length - 1) {
                    sb.append(";");
                }
            }
            response.setHeader(HttpHeaderNames.CONTENT_TYPE, sb.toString());
            response.writeAndFlush(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notfound() {
        response.setStatus(NOT_FOUND);
        response.writeAndFlush("404 Not Found!");
    }

    private static void send100Continue(ChannelHandlerContext ctx, HttpResponse response) {
        response.setStatus(CONTINUE);
        response.write(Unpooled.EMPTY_BUFFER);
        ctx.write(response);
    }
}
