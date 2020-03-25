package xyz.zzyitj.netty.framework.annotation;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/25 4:57 下午
 * @email zzy.main@gmail.com
 */
public enum RequestMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    private String name;

    RequestMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
