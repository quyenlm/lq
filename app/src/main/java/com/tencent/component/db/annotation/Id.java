package com.tencent.component.db.annotation;

import com.tencent.component.annotation.PluginApi;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@PluginApi(since = 4)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
    @PluginApi(since = 4)
    String defaultValue() default "";

    @PluginApi(since = 4)
    String name() default "";

    @PluginApi(since = 4)
    int strategy() default 3;
}
