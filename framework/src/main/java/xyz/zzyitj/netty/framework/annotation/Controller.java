package xyz.zzyitj.netty.framework.annotation;

import java.lang.annotation.*;

/**
 * @author intent
 * @version 1.0
 * @date 2020/3/24 7:07 下午
 * @email zzy.main@gmail.com
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
