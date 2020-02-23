package com.squareup.wire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import okio.ByteString;

final class UnknownFieldMap {
    Map<Integer, List<FieldValue>> fieldMap;

    enum UnknownFieldType {
        VARINT,
        FIXED32,
        FIXED64,
        LENGTH_DELIMITED;

        public static UnknownFieldType of(String name) {
            if ("varint".equals(name)) {
                return VARINT;
            }
            if ("fixed32".equals(name)) {
                return FIXED32;
            }
            if ("fixed64".equals(name)) {
                return FIXED64;
            }
            if ("length-delimited".equals(name)) {
                return LENGTH_DELIMITED;
            }
            throw new IllegalArgumentException("Unknown type " + name);
        }
    }

    static abstract class FieldValue {
        private final int tag;
        private final WireType wireType;

        public abstract int getSerializedSize();

        public abstract void write(int i, WireOutput wireOutput) throws IOException;

        public FieldValue(int tag2, WireType wireType2) {
            this.tag = tag2;
            this.wireType = wireType2;
        }

        public static VarintFieldValue varint(int tag2, Long value) {
            return new VarintFieldValue(tag2, value);
        }

        public static Fixed32FieldValue fixed32(int tag2, Integer value) {
            return new Fixed32FieldValue(tag2, value);
        }

        public static Fixed64FieldValue fixed64(int tag2, Long value) {
            return new Fixed64FieldValue(tag2, value);
        }

        public static LengthDelimitedFieldValue lengthDelimited(int tag2, ByteString value) {
            return new LengthDelimitedFieldValue(tag2, value);
        }

        public int getTag() {
            return this.tag;
        }

        public WireType getWireType() {
            return this.wireType;
        }

        public Integer getAsInteger() {
            throw new IllegalStateException();
        }

        public Long getAsLong() {
            throw new IllegalStateException();
        }

        public ByteString getAsBytes() {
            throw new IllegalStateException();
        }
    }

    static final class VarintFieldValue extends FieldValue {
        private final Long value;

        public VarintFieldValue(int tag, Long value2) {
            super(tag, WireType.VARINT);
            this.value = value2;
        }

        public int getSerializedSize() {
            return WireOutput.varint64Size(this.value.longValue());
        }

        public void write(int tag, WireOutput output) throws IOException {
            output.writeTag(tag, WireType.VARINT);
            output.writeVarint64(this.value.longValue());
        }

        public Long getAsLong() {
            return this.value;
        }
    }

    static final class Fixed32FieldValue extends FieldValue {
        private final Integer value;

        public Fixed32FieldValue(int tag, Integer value2) {
            super(tag, WireType.FIXED32);
            this.value = value2;
        }

        public int getSerializedSize() {
            return 4;
        }

        public void write(int tag, WireOutput output) throws IOException {
            output.writeTag(tag, WireType.FIXED32);
            output.writeFixed32(this.value.intValue());
        }

        public Integer getAsInteger() {
            return this.value;
        }
    }

    static final class Fixed64FieldValue extends FieldValue {
        private final Long value;

        public Fixed64FieldValue(int tag, Long value2) {
            super(tag, WireType.FIXED64);
            this.value = value2;
        }

        public int getSerializedSize() {
            return 8;
        }

        public void write(int tag, WireOutput output) throws IOException {
            output.writeTag(tag, WireType.FIXED64);
            output.writeFixed64(this.value.longValue());
        }

        public Long getAsLong() {
            return this.value;
        }
    }

    static final class LengthDelimitedFieldValue extends FieldValue {
        private final ByteString value;

        public LengthDelimitedFieldValue(int tag, ByteString value2) {
            super(tag, WireType.LENGTH_DELIMITED);
            this.value = value2;
        }

        public int getSerializedSize() {
            return WireOutput.varint32Size(this.value.size()) + this.value.size();
        }

        public void write(int tag, WireOutput output) throws IOException {
            output.writeTag(tag, WireType.LENGTH_DELIMITED);
            output.writeVarint32(this.value.size());
            output.writeRawBytes(this.value.toByteArray());
        }

        public ByteString getAsBytes() {
            return this.value;
        }
    }

    UnknownFieldMap() {
    }

    UnknownFieldMap(UnknownFieldMap other) {
        if (other.fieldMap != null) {
            ensureFieldMap().putAll(other.fieldMap);
        }
    }

    /* access modifiers changed from: package-private */
    public void addVarint(int tag, Long value) throws IOException {
        addElement(ensureFieldMap(), tag, value, WireType.VARINT);
    }

    /* access modifiers changed from: package-private */
    public void addFixed32(int tag, Integer value) throws IOException {
        addElement(ensureFieldMap(), tag, value, WireType.FIXED32);
    }

    /* access modifiers changed from: package-private */
    public void addFixed64(int tag, Long value) throws IOException {
        addElement(ensureFieldMap(), tag, value, WireType.FIXED64);
    }

    /* access modifiers changed from: package-private */
    public void addLengthDelimited(int tag, ByteString value) throws IOException {
        addElement(ensureFieldMap(), tag, value, WireType.LENGTH_DELIMITED);
    }

    private Map<Integer, List<FieldValue>> ensureFieldMap() {
        if (this.fieldMap == null) {
            this.fieldMap = new TreeMap();
        }
        return this.fieldMap;
    }

    private <T> void addElement(Map<Integer, List<FieldValue>> map, int tag, T value, WireType wireType) throws IOException {
        FieldValue fieldValue;
        List<FieldValue> values = map.get(Integer.valueOf(tag));
        if (values == null) {
            values = new ArrayList<>();
            map.put(Integer.valueOf(tag), values);
        }
        switch (wireType) {
            case VARINT:
                fieldValue = FieldValue.varint(tag, (Long) value);
                break;
            case FIXED32:
                fieldValue = FieldValue.fixed32(tag, (Integer) value);
                break;
            case FIXED64:
                fieldValue = FieldValue.fixed64(tag, (Long) value);
                break;
            case LENGTH_DELIMITED:
                fieldValue = FieldValue.lengthDelimited(tag, (ByteString) value);
                break;
            default:
                throw new IllegalArgumentException("Unsupported wireType = " + wireType);
        }
        if (values.size() <= 0 || values.get(0).getWireType() == fieldValue.getWireType()) {
            values.add(fieldValue);
        } else {
            throw new IOException(String.format("Wire type %s differs from previous type %s for tag %s", new Object[]{fieldValue.getWireType(), values.get(0).getWireType(), Integer.valueOf(tag)}));
        }
    }

    /* access modifiers changed from: package-private */
    public int getSerializedSize() {
        int size = 0;
        if (this.fieldMap != null) {
            for (Map.Entry<Integer, List<FieldValue>> entry : this.fieldMap.entrySet()) {
                size += WireOutput.varintTagSize(entry.getKey().intValue());
                for (FieldValue value : entry.getValue()) {
                    size += value.getSerializedSize();
                }
            }
        }
        return size;
    }

    /* access modifiers changed from: package-private */
    public void write(WireOutput output) throws IOException {
        if (this.fieldMap != null) {
            for (Map.Entry<Integer, List<FieldValue>> entry : this.fieldMap.entrySet()) {
                int tag = entry.getKey().intValue();
                for (FieldValue value : entry.getValue()) {
                    value.write(tag, output);
                }
            }
        }
    }
}
