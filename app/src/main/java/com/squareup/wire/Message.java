package com.squareup.wire;

import com.squareup.wire.MessageAdapter;
import com.squareup.wire.UnknownFieldMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.ByteString;

public abstract class Message {
    /* access modifiers changed from: private */
    public static final Wire WIRE = new Wire((Class<?>[]) new Class[0]);
    private transient int cachedSerializedSize;
    protected transient int hashCode = 0;
    private transient boolean haveCachedSerializedSize;
    /* access modifiers changed from: private */
    public transient UnknownFieldMap unknownFields;

    public enum Datatype {
        INT32(1),
        INT64(2),
        UINT32(3),
        UINT64(4),
        SINT32(5),
        SINT64(6),
        BOOL(7),
        ENUM(8),
        STRING(9),
        BYTES(10),
        MESSAGE(11),
        FIXED32(12),
        SFIXED32(13),
        FIXED64(14),
        SFIXED64(15),
        FLOAT(16),
        DOUBLE(17);
        
        public static final Comparator<Datatype> ORDER_BY_NAME = null;
        private static final Map<String, Datatype> TYPES_BY_NAME = null;
        private final int value;

        static {
            ORDER_BY_NAME = new Comparator<Datatype>() {
                public int compare(Datatype o1, Datatype o2) {
                    return o1.name().compareTo(o2.name());
                }
            };
            TYPES_BY_NAME = new LinkedHashMap();
            TYPES_BY_NAME.put("int32", INT32);
            TYPES_BY_NAME.put("int64", INT64);
            TYPES_BY_NAME.put("uint32", UINT32);
            TYPES_BY_NAME.put("uint64", UINT64);
            TYPES_BY_NAME.put("sint32", SINT32);
            TYPES_BY_NAME.put("sint64", SINT64);
            TYPES_BY_NAME.put("bool", BOOL);
            TYPES_BY_NAME.put("enum", ENUM);
            TYPES_BY_NAME.put("string", STRING);
            TYPES_BY_NAME.put("bytes", BYTES);
            TYPES_BY_NAME.put("message", MESSAGE);
            TYPES_BY_NAME.put("fixed32", FIXED32);
            TYPES_BY_NAME.put("sfixed32", SFIXED32);
            TYPES_BY_NAME.put("fixed64", FIXED64);
            TYPES_BY_NAME.put("sfixed64", SFIXED64);
            TYPES_BY_NAME.put("float", FLOAT);
            TYPES_BY_NAME.put("double", DOUBLE);
        }

        private Datatype(int value2) {
            this.value = value2;
        }

        public int value() {
            return this.value;
        }

        public WireType wireType() {
            switch (this) {
                case INT32:
                case INT64:
                case UINT32:
                case UINT64:
                case SINT32:
                case SINT64:
                case BOOL:
                case ENUM:
                    return WireType.VARINT;
                case FIXED32:
                case SFIXED32:
                case FLOAT:
                    return WireType.FIXED32;
                case FIXED64:
                case SFIXED64:
                case DOUBLE:
                    return WireType.FIXED64;
                case STRING:
                case BYTES:
                case MESSAGE:
                    return WireType.LENGTH_DELIMITED;
                default:
                    throw new AssertionError("No wiretype for datatype " + this);
            }
        }

        public static Datatype of(String typeString) {
            return TYPES_BY_NAME.get(typeString);
        }
    }

    public enum Label {
        REQUIRED(32),
        OPTIONAL(64),
        REPEATED(128),
        PACKED(256);
        
        public static final Comparator<Label> ORDER_BY_NAME = null;
        private final int value;

        static {
            ORDER_BY_NAME = new Comparator<Label>() {
                public int compare(Label o1, Label o2) {
                    return o1.name().compareTo(o2.name());
                }
            };
        }

        private Label(int value2) {
            this.value = value2;
        }

        public int value() {
            return this.value;
        }

        public boolean isRepeated() {
            return this == REPEATED || this == PACKED;
        }

        public boolean isPacked() {
            return this == PACKED;
        }
    }

    protected Message() {
    }

    /* access modifiers changed from: protected */
    public void setBuilder(Builder builder) {
        if (builder.unknownFieldMap != null) {
            this.unknownFields = new UnknownFieldMap(builder.unknownFieldMap);
        }
    }

    /* access modifiers changed from: protected */
    public Collection<List<UnknownFieldMap.FieldValue>> unknownFields() {
        if (this.unknownFields == null) {
            return Collections.emptySet();
        }
        return this.unknownFields.fieldMap.values();
    }

    /* access modifiers changed from: protected */
    public static <T> List<T> copyOf(List<T> source) {
        if (source == null) {
            return null;
        }
        return new ArrayList(source);
    }

    protected static <T> List<T> immutableCopyOf(List<T> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return !(source instanceof MessageAdapter.ImmutableList) ? Collections.unmodifiableList(new ArrayList(source)) : source;
    }

    public static <E extends Enum & ProtoEnum> int intFromEnum(E value) {
        return WIRE.enumAdapter(value.getClass()).toInt((ProtoEnum) value);
    }

    public static <E extends Enum & ProtoEnum> E enumFromInt(Class<E> enumClass, int value) {
        return (Enum) WIRE.enumAdapter(enumClass).fromInt(value);
    }

    public byte[] toByteArray() {
        return WIRE.messageAdapter(getClass()).toByteArray(this);
    }

    public void writeTo(byte[] output) {
        writeTo(output, 0, output.length);
    }

    public void writeTo(byte[] output, int offset, int count) {
        write(WireOutput.newInstance(output, offset, count));
    }

    private void write(WireOutput output) {
        try {
            WIRE.messageAdapter(getClass()).write(this, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeUnknownFieldMap(WireOutput output) throws IOException {
        if (this.unknownFields != null) {
            this.unknownFields.write(output);
        }
    }

    public int getSerializedSize() {
        if (!this.haveCachedSerializedSize) {
            this.cachedSerializedSize = WIRE.messageAdapter(getClass()).getSerializedSize(this);
            this.haveCachedSerializedSize = true;
        }
        return this.cachedSerializedSize;
    }

    public int getUnknownFieldsSerializedSize() {
        if (this.unknownFields == null) {
            return 0;
        }
        return this.unknownFields.getSerializedSize();
    }

    /* access modifiers changed from: protected */
    public boolean equals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    /* access modifiers changed from: protected */
    public boolean equals(List<?> a, List<?> b) {
        if (a != null && a.isEmpty()) {
            a = null;
        }
        if (b != null && b.isEmpty()) {
            b = null;
        }
        return a == b || (a != null && a.equals(b));
    }

    public String toString() {
        return WIRE.messageAdapter(getClass()).toString(this);
    }

    public static abstract class Builder<T extends Message> {
        UnknownFieldMap unknownFieldMap;

        public abstract T build();

        public Builder() {
        }

        public Builder(Message message) {
            if (message != null && message.unknownFields != null) {
                this.unknownFieldMap = new UnknownFieldMap(message.unknownFields);
            }
        }

        public void addVarint(int tag, long value) {
            try {
                ensureUnknownFieldMap().addVarint(tag, Long.valueOf(value));
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        public void addFixed32(int tag, int value) {
            try {
                ensureUnknownFieldMap().addFixed32(tag, Integer.valueOf(value));
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        public void addFixed64(int tag, long value) {
            try {
                ensureUnknownFieldMap().addFixed64(tag, Long.valueOf(value));
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        public void addLengthDelimited(int tag, ByteString value) {
            try {
                ensureUnknownFieldMap().addLengthDelimited(tag, value);
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        /* access modifiers changed from: package-private */
        public UnknownFieldMap ensureUnknownFieldMap() {
            if (this.unknownFieldMap == null) {
                this.unknownFieldMap = new UnknownFieldMap();
            }
            return this.unknownFieldMap;
        }

        public void checkRequiredFields() {
            Message.WIRE.builderAdapter(getClass()).checkRequiredFields(this);
        }

        protected static <T> List<T> checkForNulls(List<T> elements) {
            if (elements != null && !elements.isEmpty()) {
                int size = elements.size();
                for (int i = 0; i < size; i++) {
                    if (elements.get(i) == null) {
                        throw new NullPointerException("Element at index " + i + " is null");
                    }
                }
            }
            return elements;
        }
    }
}
