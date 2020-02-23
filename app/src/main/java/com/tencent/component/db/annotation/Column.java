package com.tencent.component.db.annotation;

import com.tencent.component.annotation.PluginApi;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@PluginApi(since = 4)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    public enum ConflictAction {
        ROLLBACK,
        ABORT,
        FAIL,
        IGNORE,
        REPLACE
    }

    @PluginApi(since = 4)
    String defaultValue() default "";

    boolean insertable() default true;

    @PluginApi(since = 4)
    int length() default -1;

    @PluginApi(since = 4)
    String name() default "";

    boolean nullable() default true;

    ConflictAction onNullConflict() default ConflictAction.FAIL;

    ConflictAction onUniqueConflict() default ConflictAction.FAIL;

    @PluginApi(since = 4)
    boolean unique() default false;

    boolean updatable() default true;
}
