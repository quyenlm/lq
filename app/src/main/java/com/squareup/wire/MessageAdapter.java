package com.squareup.wire;

import com.squareup.wire.ExtendableMessage;
import com.squareup.wire.Message;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import okio.ByteString;

final class MessageAdapter<M extends Message> {
    private static final String FULL_BLOCK = "█";
    private static final String REDACTED = "██";
    private final Class<Message.Builder<M>> builderType;
    private final TagMap<FieldInfo> fieldInfoMap;
    private final Class<M> messageType;
    private final Map<String, Integer> tagMap = new LinkedHashMap();
    private final Wire wire;

    public static final class FieldInfo {
        /* access modifiers changed from: private */
        public final Field builderField;
        final Message.Datatype datatype;
        EnumAdapter<? extends ProtoEnum> enumAdapter;
        final Class<? extends ProtoEnum> enumType;
        final Message.Label label;
        MessageAdapter<? extends Message> messageAdapter;
        /* access modifiers changed from: private */
        public final Field messageField;
        final Class<? extends Message> messageType;
        final String name;
        final boolean redacted;
        final int tag;

        private FieldInfo(int tag2, String name2, Message.Datatype datatype2, Message.Label label2, boolean redacted2, Class<?> enumOrMessageType, Field messageField2, Field builderField2) {
            this.tag = tag2;
            this.name = name2;
            this.datatype = datatype2;
            this.label = label2;
            this.redacted = redacted2;
            if (datatype2 == Message.Datatype.ENUM) {
                this.enumType = enumOrMessageType;
                this.messageType = null;
            } else if (datatype2 == Message.Datatype.MESSAGE) {
                this.messageType = enumOrMessageType;
                this.enumType = null;
            } else {
                this.enumType = null;
                this.messageType = null;
            }
            this.messageField = messageField2;
            this.builderField = builderField2;
        }
    }

    /* access modifiers changed from: package-private */
    public Message.Builder<M> newBuilder() {
        try {
            return this.builderType.newInstance();
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InstantiationException e2) {
            throw new AssertionError(e2);
        }
    }

    /* access modifiers changed from: package-private */
    public Collection<FieldInfo> getFields() {
        return this.fieldInfoMap.values();
    }

    /* access modifiers changed from: package-private */
    public FieldInfo getField(String name) {
        Integer key = this.tagMap.get(name);
        if (key == null) {
            return null;
        }
        return this.fieldInfoMap.get(key.intValue());
    }

    /* access modifiers changed from: package-private */
    public Object getFieldValue(M message, FieldInfo fieldInfo) {
        if (fieldInfo.messageField == null) {
            throw new AssertionError("Field is not of type \"Message\"");
        }
        try {
            return fieldInfo.messageField.get(message);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    public void setBuilderField(Message.Builder<M> builder, int tag, Object value) {
        try {
            this.fieldInfoMap.get(tag).builderField.set(builder, value);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    MessageAdapter(Wire wire2, Class<M> messageType2) {
        this.wire = wire2;
        this.messageType = messageType2;
        this.builderType = getBuilderType(messageType2);
        Map<Integer, FieldInfo> map = new LinkedHashMap<>();
        Field[] declaredFields = messageType2.getDeclaredFields();
        int length = declaredFields.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length) {
                Field messageField = declaredFields[i2];
                ProtoField annotation = (ProtoField) messageField.getAnnotation(ProtoField.class);
                if (annotation != null) {
                    int tag = annotation.tag();
                    String name = messageField.getName();
                    this.tagMap.put(name, Integer.valueOf(tag));
                    Class cls = null;
                    Message.Datatype datatype = annotation.type();
                    if (datatype == Message.Datatype.ENUM) {
                        cls = getEnumType(messageField);
                    } else if (datatype == Message.Datatype.MESSAGE) {
                        cls = getMessageType(messageField);
                    }
                    map.put(Integer.valueOf(tag), new FieldInfo(tag, name, datatype, annotation.label(), annotation.redacted(), cls, messageField, getBuilderField(name)));
                }
                i = i2 + 1;
            } else {
                this.fieldInfoMap = TagMap.of(map);
                return;
            }
        }
    }

    private Class<Message.Builder<M>> getBuilderType(Class<M> messageType2) {
        try {
            return Class.forName(messageType2.getName() + "$Builder");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("No builder class found for message type " + messageType2.getName());
        }
    }

    private Field getBuilderField(String name) {
        try {
            return this.builderType.getField(name);
        } catch (NoSuchFieldException e) {
            throw new AssertionError("No builder field " + this.builderType.getName() + "." + name);
        }
    }

    private Class<? extends Message> getMessageType(Field field) {
        Class<?> fieldType = field.getType();
        if (Message.class.isAssignableFrom(fieldType)) {
            return fieldType;
        }
        if (List.class.isAssignableFrom(fieldType)) {
            return ((ProtoField) field.getAnnotation(ProtoField.class)).messageType();
        }
        return null;
    }

    private Class<? extends Enum> getEnumType(Field field) {
        Class<?> fieldType = field.getType();
        if (Enum.class.isAssignableFrom(fieldType)) {
            return fieldType;
        }
        if (List.class.isAssignableFrom(fieldType)) {
            return ((ProtoField) field.getAnnotation(ProtoField.class)).enumType();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getSerializedSize(M message) {
        int size = 0;
        for (FieldInfo fieldInfo : getFields()) {
            Object value = getFieldValue(message, fieldInfo);
            if (value != null) {
                int tag = fieldInfo.tag;
                Message.Datatype datatype = fieldInfo.datatype;
                Message.Label label = fieldInfo.label;
                if (!label.isRepeated()) {
                    size += getSerializedSize(tag, value, datatype);
                } else if (label.isPacked()) {
                    size += getPackedSize((List) value, tag, datatype);
                } else {
                    size += getRepeatedSize((List) value, tag, datatype);
                }
            }
        }
        if (message instanceof ExtendableMessage) {
            ExtendableMessage extendableMessage = (ExtendableMessage) message;
            if (extendableMessage.extensionMap != null) {
                size += getExtensionsSerializedSize(extendableMessage.extensionMap);
            }
        }
        return size + message.getUnknownFieldsSerializedSize();
    }

    private <T extends ExtendableMessage<?>> int getExtensionsSerializedSize(ExtensionMap<T> map) {
        int serializedSize;
        int size = 0;
        for (int i = 0; i < map.size(); i++) {
            Extension<T, ?> extension = map.getExtension(i);
            Object value = map.getExtensionValue(i);
            int tag = extension.getTag();
            Message.Datatype datatype = extension.getDatatype();
            Message.Label label = extension.getLabel();
            if (!label.isRepeated()) {
                serializedSize = getSerializedSize(tag, value, datatype);
            } else if (label.isPacked()) {
                serializedSize = getPackedSize((List) value, tag, datatype);
            } else {
                serializedSize = getRepeatedSize((List) value, tag, datatype);
            }
            size += serializedSize;
        }
        return size;
    }

    private int getRepeatedSize(List<?> value, int tag, Message.Datatype datatype) {
        int size = 0;
        for (Object o : value) {
            size += getSerializedSize(tag, o, datatype);
        }
        return size;
    }

    private int getPackedSize(List<?> value, int tag, Message.Datatype datatype) {
        int packedLength = 0;
        for (Object o : value) {
            packedLength += getSerializedSizeNoTag(o, datatype);
        }
        return WireOutput.varint32Size(WireOutput.makeTag(tag, WireType.LENGTH_DELIMITED)) + WireOutput.varint32Size(packedLength) + packedLength;
    }

    /* access modifiers changed from: package-private */
    public void write(M message, WireOutput output) throws IOException {
        for (FieldInfo fieldInfo : getFields()) {
            Object value = getFieldValue(message, fieldInfo);
            if (value != null) {
                int tag = fieldInfo.tag;
                Message.Datatype datatype = fieldInfo.datatype;
                Message.Label label = fieldInfo.label;
                if (!label.isRepeated()) {
                    writeValue(output, tag, value, datatype);
                } else if (label.isPacked()) {
                    writePacked(output, (List) value, tag, datatype);
                } else {
                    writeRepeated(output, (List) value, tag, datatype);
                }
            }
        }
        if (message instanceof ExtendableMessage) {
            ExtendableMessage extendableMessage = (ExtendableMessage) message;
            if (extendableMessage.extensionMap != null) {
                writeExtensions(output, extendableMessage.extensionMap);
            }
        }
        message.writeUnknownFieldMap(output);
    }

    private <T extends ExtendableMessage<?>> void writeExtensions(WireOutput output, ExtensionMap<T> extensionMap) throws IOException {
        for (int i = 0; i < extensionMap.size(); i++) {
            Extension<T, ?> extension = extensionMap.getExtension(i);
            Object value = extensionMap.getExtensionValue(i);
            int tag = extension.getTag();
            Message.Datatype datatype = extension.getDatatype();
            Message.Label label = extension.getLabel();
            if (!label.isRepeated()) {
                writeValue(output, tag, value, datatype);
            } else if (label.isPacked()) {
                writePacked(output, (List) value, tag, datatype);
            } else {
                writeRepeated(output, (List) value, tag, datatype);
            }
        }
    }

    private void writeRepeated(WireOutput output, List<?> value, int tag, Message.Datatype datatype) throws IOException {
        for (Object o : value) {
            writeValue(output, tag, o, datatype);
        }
    }

    private void writePacked(WireOutput output, List<?> value, int tag, Message.Datatype datatype) throws IOException {
        int packedLength = 0;
        for (Object o : value) {
            packedLength += getSerializedSizeNoTag(o, datatype);
        }
        output.writeTag(tag, WireType.LENGTH_DELIMITED);
        output.writeVarint32(packedLength);
        for (Object o2 : value) {
            writeValueNoTag(output, o2, datatype);
        }
    }

    /* access modifiers changed from: package-private */
    public byte[] toByteArray(M message) {
        byte[] result = new byte[getSerializedSize(message)];
        try {
            write(message, WireOutput.newInstance(result));
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: package-private */
    public String toString(M message) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.messageType.getSimpleName());
        sb.append("{");
        String sep = "";
        for (FieldInfo fieldInfo : getFields()) {
            Object value = getFieldValue(message, fieldInfo);
            if (value != null) {
                sb.append(sep);
                sep = ", ";
                sb.append(fieldInfo.name);
                sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                if (fieldInfo.redacted) {
                    value = REDACTED;
                }
                sb.append(value);
            }
        }
        if (message instanceof ExtendableMessage) {
            sb.append(sep);
            sb.append("{extensions=");
            sb.append(message.extensionsToString());
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }

    private int getSerializedSize(int tag, Object value, Message.Datatype datatype) {
        return WireOutput.varintTagSize(tag) + getSerializedSizeNoTag(value, datatype);
    }

    private int getSerializedSizeNoTag(Object value, Message.Datatype datatype) {
        switch (datatype) {
            case INT32:
                return WireOutput.int32Size(((Integer) value).intValue());
            case INT64:
            case UINT64:
                return WireOutput.varint64Size(((Long) value).longValue());
            case UINT32:
                return WireOutput.varint32Size(((Integer) value).intValue());
            case SINT32:
                return WireOutput.varint32Size(WireOutput.zigZag32(((Integer) value).intValue()));
            case SINT64:
                return WireOutput.varint64Size(WireOutput.zigZag64(((Long) value).longValue()));
            case BOOL:
                return 1;
            case ENUM:
                return getEnumSize((ProtoEnum) value);
            case STRING:
                int utf8Length = utf8Length((String) value);
                return WireOutput.varint32Size(utf8Length) + utf8Length;
            case BYTES:
                int length = ((ByteString) value).size();
                return WireOutput.varint32Size(length) + length;
            case MESSAGE:
                return getMessageSize((Message) value);
            case FIXED32:
            case SFIXED32:
            case FLOAT:
                return 4;
            case FIXED64:
            case SFIXED64:
            case DOUBLE:
                return 8;
            default:
                throw new RuntimeException();
        }
    }

    private int utf8Length(String s) {
        int count = 0;
        int i = 0;
        int length = s.length();
        while (i < length) {
            char ch = s.charAt(i);
            if (ch <= 127) {
                count++;
            } else if (ch <= 2047) {
                count += 2;
            } else if (Character.isHighSurrogate(ch)) {
                count += 4;
                i++;
            } else {
                count += 3;
            }
            i++;
        }
        return count;
    }

    private <E extends ProtoEnum> int getEnumSize(E value) {
        return WireOutput.varint32Size(this.wire.enumAdapter(value.getClass()).toInt(value));
    }

    private <M extends Message> int getMessageSize(M message) {
        int messageSize = message.getSerializedSize();
        return WireOutput.varint32Size(messageSize) + messageSize;
    }

    private void writeValue(WireOutput output, int tag, Object value, Message.Datatype datatype) throws IOException {
        output.writeTag(tag, datatype.wireType());
        writeValueNoTag(output, value, datatype);
    }

    private void writeValueNoTag(WireOutput output, Object value, Message.Datatype datatype) throws IOException {
        switch (datatype) {
            case INT32:
                output.writeSignedVarint32(((Integer) value).intValue());
                return;
            case INT64:
            case UINT64:
                output.writeVarint64(((Long) value).longValue());
                return;
            case UINT32:
                output.writeVarint32(((Integer) value).intValue());
                return;
            case SINT32:
                output.writeVarint32(WireOutput.zigZag32(((Integer) value).intValue()));
                return;
            case SINT64:
                output.writeVarint64(WireOutput.zigZag64(((Long) value).longValue()));
                return;
            case BOOL:
                output.writeRawByte(((Boolean) value).booleanValue() ? 1 : 0);
                return;
            case ENUM:
                writeEnum((ProtoEnum) value, output);
                return;
            case STRING:
                byte[] bytes = ((String) value).getBytes("UTF-8");
                output.writeVarint32(bytes.length);
                output.writeRawBytes(bytes);
                return;
            case BYTES:
                ByteString byteString = (ByteString) value;
                output.writeVarint32(byteString.size());
                output.writeRawBytes(byteString.toByteArray());
                return;
            case MESSAGE:
                writeMessage((Message) value, output);
                return;
            case FIXED32:
            case SFIXED32:
                output.writeFixed32(((Integer) value).intValue());
                return;
            case FLOAT:
                output.writeFixed32(Float.floatToIntBits(((Float) value).floatValue()));
                return;
            case FIXED64:
            case SFIXED64:
                output.writeFixed64(((Long) value).longValue());
                return;
            case DOUBLE:
                output.writeFixed64(Double.doubleToLongBits(((Double) value).doubleValue()));
                return;
            default:
                throw new RuntimeException();
        }
    }

    private <M extends Message> void writeMessage(M message, WireOutput output) throws IOException {
        output.writeVarint32(message.getSerializedSize());
        this.wire.messageAdapter(message.getClass()).write(message, output);
    }

    private <E extends ProtoEnum> void writeEnum(E value, WireOutput output) throws IOException {
        output.writeVarint32(this.wire.enumAdapter(value.getClass()).toInt(value));
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x004a, code lost:
        setBuilderField(r4, r16, r13.get(r16));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
        setExtension((com.squareup.wire.ExtendableMessage.ExtendableBuilder) r4, getExtension(r16), r13.get(r16));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x008f, code lost:
        return r4.build();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0022, code lost:
        r22 = r13.getTags().iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002e, code lost:
        if (r22.hasNext() == false) goto L_0x008b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0030, code lost:
        r16 = r22.next().intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0048, code lost:
        if (r26.fieldInfoMap.containsKey(r16) == false) goto L_0x0063;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public M read(com.squareup.wire.WireInput r27) throws java.io.IOException {
        /*
            r26 = this;
            r0 = r26
            java.lang.Class<com.squareup.wire.Message$Builder<M>> r0 = r0.builderType     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = r0
            java.lang.Object r4 = r21.newInstance()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            com.squareup.wire.Message$Builder r4 = (com.squareup.wire.Message.Builder) r4     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            com.squareup.wire.MessageAdapter$Storage r13 = new com.squareup.wire.MessageAdapter$Storage     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = 0
            r0 = r21
            r13.<init>()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
        L_0x0015:
            r7 = 0
            int r18 = r27.readTag()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            int r17 = r18 >> 3
            com.squareup.wire.WireType r20 = com.squareup.wire.WireType.valueOf((int) r18)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            if (r17 != 0) goto L_0x0090
            java.util.Set r21 = r13.getTags()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            java.util.Iterator r22 = r21.iterator()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
        L_0x002a:
            boolean r21 = r22.hasNext()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            if (r21 == 0) goto L_0x008b
            java.lang.Object r21 = r22.next()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            java.lang.Integer r21 = (java.lang.Integer) r21     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            int r16 = r21.intValue()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r26
            com.squareup.wire.TagMap<com.squareup.wire.MessageAdapter$FieldInfo> r0 = r0.fieldInfoMap     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = r0
            r0 = r21
            r1 = r16
            boolean r9 = r0.containsKey(r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            if (r9 == 0) goto L_0x0063
            r0 = r16
            java.util.List r21 = r13.get(r0)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r26
            r1 = r16
            r2 = r21
            r0.setBuilderField(r4, r1, r2)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x002a
        L_0x005a:
            r6 = move-exception
            java.lang.RuntimeException r21 = new java.lang.RuntimeException
            r0 = r21
            r0.<init>(r6)
            throw r21
        L_0x0063:
            r0 = r4
            com.squareup.wire.ExtendableMessage$ExtendableBuilder r0 = (com.squareup.wire.ExtendableMessage.ExtendableBuilder) r0     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = r0
            r0 = r26
            r1 = r16
            com.squareup.wire.Extension r23 = r0.getExtension((int) r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r16
            java.util.List r24 = r13.get(r0)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r26
            r1 = r21
            r2 = r23
            r3 = r24
            r0.setExtension(r1, r2, r3)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x002a
        L_0x0082:
            r6 = move-exception
            java.lang.RuntimeException r21 = new java.lang.RuntimeException
            r0 = r21
            r0.<init>(r6)
            throw r21
        L_0x008b:
            com.squareup.wire.Message r21 = r4.build()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            return r21
        L_0x0090:
            r0 = r26
            com.squareup.wire.TagMap<com.squareup.wire.MessageAdapter$FieldInfo> r0 = r0.fieldInfoMap     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = r0
            r0 = r21
            r1 = r17
            java.lang.Object r8 = r0.get(r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            com.squareup.wire.MessageAdapter$FieldInfo r8 = (com.squareup.wire.MessageAdapter.FieldInfo) r8     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            if (r8 == 0) goto L_0x00fa
            com.squareup.wire.Message$Datatype r5 = r8.datatype     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            com.squareup.wire.Message$Label r10 = r8.label     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
        L_0x00a6:
            boolean r21 = r10.isPacked()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            if (r21 == 0) goto L_0x013c
            com.squareup.wire.WireType r21 = com.squareup.wire.WireType.LENGTH_DELIMITED     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r20
            r1 = r21
            if (r0 != r1) goto L_0x013c
            int r11 = r27.readVarint32()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            long r14 = r27.getPosition()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r27
            int r12 = r0.pushLimit(r11)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
        L_0x00c2:
            long r22 = r27.getPosition()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            long r0 = (long) r11     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r24 = r0
            long r24 = r24 + r14
            int r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
            if (r21 >= 0) goto L_0x0122
            r0 = r26
            r1 = r27
            r2 = r17
            java.lang.Object r19 = r0.readValue(r1, r2, r5)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            com.squareup.wire.Message$Datatype r21 = com.squareup.wire.Message.Datatype.ENUM     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r21
            if (r5 != r0) goto L_0x011a
            r0 = r19
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = r0
            if (r21 == 0) goto L_0x011a
            java.lang.Integer r19 = (java.lang.Integer) r19     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            int r21 = r19.intValue()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r21
            long r0 = (long) r0     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r22 = r0
            r0 = r17
            r1 = r22
            r4.addVarint(r0, r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x00c2
        L_0x00fa:
            r0 = r26
            r1 = r17
            com.squareup.wire.Extension r7 = r0.getExtension((int) r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            if (r7 != 0) goto L_0x0111
            r0 = r26
            r1 = r27
            r2 = r17
            r3 = r20
            r0.readUnknownField(r4, r1, r2, r3)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x0015
        L_0x0111:
            com.squareup.wire.Message$Datatype r5 = r7.getDatatype()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            com.squareup.wire.Message$Label r10 = r7.getLabel()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x00a6
        L_0x011a:
            r0 = r17
            r1 = r19
            r13.add(r0, r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x00c2
        L_0x0122:
            r0 = r27
            r0.popLimit(r12)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            long r22 = r27.getPosition()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            long r0 = (long) r11     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r24 = r0
            long r24 = r24 + r14
            int r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
            if (r21 == 0) goto L_0x0015
            java.io.IOException r21 = new java.io.IOException     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            java.lang.String r22 = "Packed data had wrong length!"
            r21.<init>(r22)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            throw r21     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
        L_0x013c:
            r0 = r26
            r1 = r27
            r2 = r17
            java.lang.Object r19 = r0.readValue(r1, r2, r5)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            com.squareup.wire.Message$Datatype r21 = com.squareup.wire.Message.Datatype.ENUM     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r21
            if (r5 != r0) goto L_0x0168
            r0 = r19
            boolean r0 = r0 instanceof java.lang.Integer     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = r0
            if (r21 == 0) goto L_0x0168
            java.lang.Integer r19 = (java.lang.Integer) r19     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            int r21 = r19.intValue()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r0 = r21
            long r0 = (long) r0     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r22 = r0
            r0 = r17
            r1 = r22
            r4.addVarint(r0, r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x0015
        L_0x0168:
            boolean r21 = r10.isRepeated()     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            if (r21 == 0) goto L_0x0177
            r0 = r17
            r1 = r19
            r13.add(r0, r1)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x0015
        L_0x0177:
            if (r7 == 0) goto L_0x0189
            r0 = r4
            com.squareup.wire.ExtendableMessage$ExtendableBuilder r0 = (com.squareup.wire.ExtendableMessage.ExtendableBuilder) r0     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            r21 = r0
            r0 = r26
            r1 = r21
            r2 = r19
            r0.setExtension(r1, r7, r2)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x0015
        L_0x0189:
            r0 = r26
            r1 = r17
            r2 = r19
            r0.setBuilderField(r4, r1, r2)     // Catch:{ IllegalAccessException -> 0x005a, InstantiationException -> 0x0082 }
            goto L_0x0015
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.wire.MessageAdapter.read(com.squareup.wire.WireInput):com.squareup.wire.Message");
    }

    private Object readValue(WireInput input, int tag, Message.Datatype datatype) throws IOException {
        switch (datatype) {
            case INT32:
            case UINT32:
                return Integer.valueOf(input.readVarint32());
            case INT64:
            case UINT64:
                return Long.valueOf(input.readVarint64());
            case SINT32:
                return Integer.valueOf(WireInput.decodeZigZag32(input.readVarint32()));
            case SINT64:
                return Long.valueOf(WireInput.decodeZigZag64(input.readVarint64()));
            case BOOL:
                return Boolean.valueOf(input.readVarint32() != 0);
            case ENUM:
                EnumAdapter<? extends ProtoEnum> adapter = getEnumAdapter(tag);
                int value = input.readVarint32();
                try {
                    return adapter.fromInt(value);
                } catch (IllegalArgumentException e) {
                    return Integer.valueOf(value);
                }
            case STRING:
                return input.readString();
            case BYTES:
                return input.readBytes();
            case MESSAGE:
                return readMessage(input, tag);
            case FIXED32:
            case SFIXED32:
                return Integer.valueOf(input.readFixed32());
            case FLOAT:
                return Float.valueOf(Float.intBitsToFloat(input.readFixed32()));
            case FIXED64:
            case SFIXED64:
                return Long.valueOf(input.readFixed64());
            case DOUBLE:
                return Double.valueOf(Double.longBitsToDouble(input.readFixed64()));
            default:
                throw new RuntimeException();
        }
    }

    private Message readMessage(WireInput input, int tag) throws IOException {
        int length = input.readVarint32();
        if (input.recursionDepth >= 64) {
            throw new IOException("Wire recursion limit exceeded");
        }
        int oldLimit = input.pushLimit(length);
        input.recursionDepth++;
        Message message = getMessageAdapter(tag).read(input);
        input.checkLastTagWas(0);
        input.recursionDepth--;
        input.popLimit(oldLimit);
        return message;
    }

    private MessageAdapter<? extends Message> getMessageAdapter(int tag) {
        FieldInfo fieldInfo = this.fieldInfoMap.get(tag);
        if (fieldInfo != null && fieldInfo.messageAdapter != null) {
            return fieldInfo.messageAdapter;
        }
        MessageAdapter<? extends Message> result = this.wire.messageAdapter(getMessageClass(tag));
        if (fieldInfo == null) {
            return result;
        }
        fieldInfo.messageAdapter = result;
        return result;
    }

    private EnumAdapter<? extends ProtoEnum> getEnumAdapter(int tag) {
        FieldInfo fieldInfo = this.fieldInfoMap.get(tag);
        if (fieldInfo != null && fieldInfo.enumAdapter != null) {
            return fieldInfo.enumAdapter;
        }
        EnumAdapter<? extends ProtoEnum> result = this.wire.enumAdapter(getEnumClass(tag));
        if (fieldInfo == null) {
            return result;
        }
        fieldInfo.enumAdapter = result;
        return result;
    }

    private Class<Message> getMessageClass(int tag) {
        Extension<ExtendableMessage<?>, ?> extension;
        FieldInfo fieldInfo = this.fieldInfoMap.get(tag);
        Class<? extends Message> cls = fieldInfo == null ? null : fieldInfo.messageType;
        if (cls != null || (extension = getExtension(tag)) == null) {
            return cls;
        }
        return extension.getMessageType();
    }

    private void readUnknownField(Message.Builder builder, WireInput input, int tag, WireType type) throws IOException {
        switch (type) {
            case VARINT:
                builder.ensureUnknownFieldMap().addVarint(tag, Long.valueOf(input.readVarint64()));
                return;
            case FIXED32:
                builder.ensureUnknownFieldMap().addFixed32(tag, Integer.valueOf(input.readFixed32()));
                return;
            case FIXED64:
                builder.ensureUnknownFieldMap().addFixed64(tag, Long.valueOf(input.readFixed64()));
                return;
            case LENGTH_DELIMITED:
                builder.ensureUnknownFieldMap().addLengthDelimited(tag, input.readBytes(input.readVarint32()));
                return;
            case START_GROUP:
                input.skipGroup();
                return;
            case END_GROUP:
                return;
            default:
                throw new RuntimeException("Unsupported wire type: " + type);
        }
    }

    private static class Storage {
        private Map<Integer, ImmutableList<Object>> map;

        private Storage() {
        }

        /* access modifiers changed from: package-private */
        public void add(int tag, Object value) {
            ImmutableList<Object> list = this.map == null ? null : this.map.get(Integer.valueOf(tag));
            if (list == null) {
                list = new ImmutableList<>();
                if (this.map == null) {
                    this.map = new LinkedHashMap();
                }
                this.map.put(Integer.valueOf(tag), list);
            }
            list.list.add(value);
        }

        /* access modifiers changed from: package-private */
        public Set<Integer> getTags() {
            if (this.map == null) {
                return Collections.emptySet();
            }
            return this.map.keySet();
        }

        /* access modifiers changed from: package-private */
        public List<Object> get(int tag) {
            if (this.map == null) {
                return null;
            }
            return this.map.get(Integer.valueOf(tag));
        }
    }

    private Extension<ExtendableMessage<?>, ?> getExtension(int tag) {
        ExtensionRegistry registry = this.wire.registry;
        if (registry == null) {
            return null;
        }
        return registry.getExtension(this.messageType, tag);
    }

    /* access modifiers changed from: package-private */
    public Extension<ExtendableMessage<?>, ?> getExtension(String name) {
        ExtensionRegistry registry = this.wire.registry;
        if (registry == null) {
            return null;
        }
        return registry.getExtension(this.messageType, name);
    }

    private void setExtension(ExtendableMessage.ExtendableBuilder builder, Extension<?, ?> extension, Object value) {
        builder.setExtension(extension, value);
    }

    private Class<? extends ProtoEnum> getEnumClass(int tag) {
        Extension<ExtendableMessage<?>, ?> extension;
        FieldInfo fieldInfo = this.fieldInfoMap.get(tag);
        Class<? extends ProtoEnum> enumType = fieldInfo == null ? null : fieldInfo.enumType;
        if (enumType != null || (extension = getExtension(tag)) == null) {
            return enumType;
        }
        return extension.getEnumType();
    }

    static class ImmutableList<T> extends AbstractList<T> implements Cloneable, RandomAccess, Serializable {
        /* access modifiers changed from: private */
        public final List<T> list = new ArrayList();

        ImmutableList() {
        }

        public Object clone() {
            return this;
        }

        public int size() {
            return this.list.size();
        }

        public T get(int i) {
            return this.list.get(i);
        }
    }
}
