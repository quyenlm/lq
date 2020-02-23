package com.squareup.wire;

import com.squareup.wire.Message;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProtoField {
    boolean deprecated() default false;

    Class<? extends Enum> enumType() default Enum.class;

    Message.Label label() default Message.Label.OPTIONAL;

    Class<? extends Message> messageType() default Message.class;

    boolean redacted() default false;

    int tag();

    Message.Datatype type() default Message.Datatype.MESSAGE;
}
