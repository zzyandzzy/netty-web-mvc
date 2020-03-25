package xyz.zzyitj.netty.framework.annotation;

import xyz.zzyitj.netty.framework.util.MediaType;

import java.lang.annotation.*;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 7:09 下午
 * @email zzy.main@gmail.com
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String name() default "";

    String[] value() default {};

    RequestMethod[] method() default {RequestMethod.GET, RequestMethod.POST};

    String[] produces() default MediaType.APPLICATION_JSON_VALUE;
}
