package xyz.zzyitj.netty.framework.model;

import xyz.zzyitj.netty.framework.annotation.RequestMethod;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 5:02 下午
 * @email zzy.main@gmail.com
 */
public class HandlerMapping implements Serializable {
    private static final long serialVersionUID = -7758897553727380664L;

    private Method method;
    private RequestMethod[] requestMethods;
    private String[] produces;

    public HandlerMapping() {
    }

    public HandlerMapping(Method method, RequestMethod[] requestMethods, String[] produces) {
        this.method = method;
        this.requestMethods = requestMethods;
        this.produces = produces;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public RequestMethod[] getRequestMethods() {
        return requestMethods;
    }

    public void setRequestMethods(RequestMethod[] requestMethods) {
        this.requestMethods = requestMethods;
    }

    public String[] getProduces() {
        return produces;
    }

    public void setProduces(String[] produces) {
        this.produces = produces;
    }
}
