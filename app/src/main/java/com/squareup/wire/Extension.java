package com.squareup.wire;

import com.squareup.wire.ExtendableMessage;
import com.squareup.wire.Message;
import java.util.List;
import okio.ByteString;

public final class Extension<T extends ExtendableMessage<?>, E> implements Comparable<Extension<?, ?>> {
    private final Message.Datatype datatype;
    private final Class<? extends ProtoEnum> enumType;
    private final Class<T> extendedType;
    private final Message.Label label;
    private final Class<? extends Message> messageType;
    private final String name;
    private final int tag;

    public static final class Builder<T extends ExtendableMessage<?>, E> {
        private final Message.Datatype datatype;
        private final Class<? extends ProtoEnum> enumType;
        private final Class<T> extendedType;
        private Message.Label label;
        private final Class<? extends Message> messageType;
        private String name;
        private int tag;

        private Builder(Class<T> extendedType2, Message.Datatype datatype2) {
            this.name = null;
            this.tag = -1;
            this.label = null;
            this.extendedType = extendedType2;
            this.messageType = null;
            this.enumType = null;
            this.datatype = datatype2;
        }

        private Builder(Class<T> extendedType2, Class<? extends Message> messageType2, Class<? extends ProtoEnum> enumType2, Message.Datatype datatype2) {
            this.name = null;
            this.tag = -1;
            this.label = null;
            this.extendedType = extendedType2;
            this.messageType = messageType2;
            this.enumType = enumType2;
            this.datatype = datatype2;
        }

        public Builder<T, E> setName(String name2) {
            this.name = name2;
            return this;
        }

        public Builder<T, E> setTag(int tag2) {
            this.tag = tag2;
            return this;
        }

        public Extension<T, E> buildOptional() {
            this.label = Message.Label.OPTIONAL;
            validate();
            return new Extension<>(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
        }

        public Extension<T, E> buildRequired() {
            this.label = Message.Label.REQUIRED;
            validate();
            return new Extension<>(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
        }

        public Extension<T, List<E>> buildRepeated() {
            this.label = Message.Label.REPEATED;
            validate();
            return new Extension<>(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
        }

        public Extension<T, List<E>> buildPacked() {
            this.label = Message.Label.PACKED;
            validate();
            return new Extension<>(this.extendedType, this.messageType, this.enumType, this.name, this.tag, this.label, this.datatype);
        }

        private void validate() {
            if (this.extendedType == null) {
                throw new IllegalArgumentException("extendedType == null");
            } else if (this.name == null) {
                throw new IllegalArgumentException("name == null");
            } else if (this.datatype == null) {
                throw new IllegalArgumentException("datatype == null");
            } else if (this.label == null) {
                throw new IllegalArgumentException("label == null");
            } else if (this.tag <= 0) {
                throw new IllegalArgumentException("tag == " + this.tag);
            } else if (this.datatype == Message.Datatype.MESSAGE) {
                if (this.messageType == null || this.enumType != null) {
                    throw new IllegalStateException("Message w/o messageType or w/ enumType");
                }
            } else if (this.datatype == Message.Datatype.ENUM) {
                if (this.messageType != null || this.enumType == null) {
                    throw new IllegalStateException("Enum w/ messageType or w/o enumType");
                }
            } else if (this.messageType != null || this.enumType != null) {
                throw new IllegalStateException("Scalar w/ messageType or enumType");
            }
        }
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> int32Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.INT32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> sint32Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.SINT32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> uint32Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.UINT32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> fixed32Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.FIXED32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Integer> sfixed32Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.SFIXED32);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> int64Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.INT64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> sint64Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.SINT64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> uint64Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.UINT64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> fixed64Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.FIXED64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Long> sfixed64Extending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.SFIXED64);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Boolean> boolExtending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.BOOL);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, String> stringExtending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.STRING);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, ByteString> bytesExtending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.BYTES);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Float> floatExtending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.FLOAT);
    }

    public static <T extends ExtendableMessage<?>> Builder<T, Double> doubleExtending(Class<T> extendedType2) {
        return new Builder<>(extendedType2, Message.Datatype.DOUBLE);
    }

    public static <T extends ExtendableMessage<?>, E extends Enum & ProtoEnum> Builder<T, E> enumExtending(Class<E> enumType2, Class<T> extendedType2) {
        return new Builder<>(extendedType2, (Class) null, enumType2, Message.Datatype.ENUM);
    }

    public static <T extends ExtendableMessage<?>, M extends Message> Builder<T, M> messageExtending(Class<M> messageType2, Class<T> extendedType2) {
        return new Builder<>(extendedType2, messageType2, (Class) null, Message.Datatype.MESSAGE);
    }

    private Extension(Class<T> extendedType2, Class<? extends Message> messageType2, Class<? extends ProtoEnum> enumType2, String name2, int tag2, Message.Label label2, Message.Datatype datatype2) {
        this.extendedType = extendedType2;
        this.name = name2;
        this.tag = tag2;
        this.datatype = datatype2;
        this.label = label2;
        this.messageType = messageType2;
        this.enumType = enumType2;
    }

    public int compareTo(Extension<?, ?> o) {
        if (o == this) {
            return 0;
        }
        if (this.tag != o.tag) {
            return this.tag - o.tag;
        }
        if (this.datatype != o.datatype) {
            return this.datatype.value() - o.datatype.value();
        }
        if (this.label != o.label) {
            return this.label.value() - o.label.value();
        }
        if (this.extendedType != null && !this.extendedType.equals(o.extendedType)) {
            return this.extendedType.getName().compareTo(o.extendedType.getName());
        }
        if (this.messageType != null && !this.messageType.equals(o.messageType)) {
            return this.messageType.getName().compareTo(o.messageType.getName());
        }
        if (this.enumType == null || this.enumType.equals(o.enumType)) {
            return 0;
        }
        return this.enumType.getName().compareTo(o.enumType.getName());
    }

    public boolean equals(Object other) {
        return (other instanceof Extension) && compareTo((Extension<?, ?>) (Extension) other) == 0;
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int value = ((((((this.tag * 37) + this.datatype.value()) * 37) + this.label.value()) * 37) + this.extendedType.hashCode()) * 37;
        if (this.messageType != null) {
            i = this.messageType.hashCode();
        } else {
            i = 0;
        }
        int i3 = (value + i) * 37;
        if (this.enumType != null) {
            i2 = this.enumType.hashCode();
        }
        return i3 + i2;
    }

    public String toString() {
        return String.format("[%s %s %s = %d]", new Object[]{this.label, this.datatype, this.name, Integer.valueOf(this.tag)});
    }

    public Class<T> getExtendedType() {
        return this.extendedType;
    }

    public Class<? extends Message> getMessageType() {
        return this.messageType;
    }

    public Class<? extends ProtoEnum> getEnumType() {
        return this.enumType;
    }

    public String getName() {
        return this.name;
    }

    public int getTag() {
        return this.tag;
    }

    public Message.Datatype getDatatype() {
        return this.datatype;
    }

    public Message.Label getLabel() {
        return this.label;
    }
}
