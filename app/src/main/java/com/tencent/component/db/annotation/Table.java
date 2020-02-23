package com.tencent.component.db.annotation;

import com.tencent.component.annotation.PluginApi;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@PluginApi(since = 4)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    @PluginApi(since = 10)
    boolean dynamicClass() default false;

    @PluginApi(since = 4)
    String name() default "";

    @PluginApi(since = 4)
    int version();
}
