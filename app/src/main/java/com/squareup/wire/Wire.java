package com.squareup.wire;

import com.squareup.wire.Message;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Source;

public final class Wire {
    private final Map<Class<? extends Message.Builder>, BuilderAdapter<? extends Message.Builder>> builderAdapters;
    private final Map<Class<? extends ProtoEnum>, EnumAdapter<? extends ProtoEnum>> enumAdapters;
    private final Map<Class<? extends Message>, MessageAdapter<? extends Message>> messageAdapters;
    final ExtensionRegistry registry;

    public Wire(Class<?>... extensionClasses) {
        this((List<Class<?>>) Arrays.asList(extensionClasses));
    }

    public Wire(List<Class<?>> extensionClasses) {
        this.messageAdapters = new LinkedHashMap();
        this.builderAdapters = new LinkedHashMap();
        this.enumAdapters = new LinkedHashMap();
        this.registry = new ExtensionRegistry();
        for (Class<?> extensionClass : extensionClasses) {
            for (Field field : extensionClass.getDeclaredFields()) {
                if (field.getType().equals(Extension.class)) {
                    try {
                        this.registry.add((Extension) field.get((Object) null));
                    } catch (IllegalAccessException e) {
                        throw new AssertionError(e);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized <M extends Message> MessageAdapter<M> messageAdapter(Class<M> messageType) {
        MessageAdapter<M> adapter;
        adapter = this.messageAdapters.get(messageType);
        if (adapter == null) {
            adapter = new MessageAdapter<>(this, messageType);
            this.messageAdapters.put(messageType, adapter);
        }
        return adapter;
    }

    /* access modifiers changed from: package-private */
    public synchronized <B extends Message.Builder> BuilderAdapter<B> builderAdapter(Class<B> builderType) {
        BuilderAdapter<B> adapter;
        adapter = this.builderAdapters.get(builderType);
        if (adapter == null) {
            adapter = new BuilderAdapter<>(builderType);
            this.builderAdapters.put(builderType, adapter);
        }
        return adapter;
    }

    /* access modifiers changed from: package-private */
    public synchronized <E extends ProtoEnum> EnumAdapter<E> enumAdapter(Class<E> enumClass) {
        EnumAdapter<E> adapter;
        adapter = this.enumAdapters.get(enumClass);
        if (adapter == null) {
            adapter = new EnumAdapter<>(enumClass);
            this.enumAdapters.put(enumClass, adapter);
        }
        return adapter;
    }

    public <M extends Message> M parseFrom(byte[] bytes, Class<M> messageClass) throws IOException {
        Preconditions.checkNotNull(bytes, "bytes");
        Preconditions.checkNotNull(messageClass, "messageClass");
        return parseFrom(WireInput.newInstance(bytes), messageClass);
    }

    public <M extends Message> M parseFrom(byte[] bytes, int offset, int count, Class<M> messageClass) throws IOException {
        boolean z;
        boolean z2;
        boolean z3 = true;
        Preconditions.checkNotNull(bytes, "bytes");
        if (offset >= 0) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "offset < 0");
        if (count >= 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        Preconditions.checkArgument(z2, "count < 0");
        if (offset + count > bytes.length) {
            z3 = false;
        }
        Preconditions.checkArgument(z3, "offset + count > bytes");
        Preconditions.checkNotNull(messageClass, "messageClass");
        return parseFrom(WireInput.newInstance(bytes, offset, count), messageClass);
    }

    public <M extends Message> M parseFrom(InputStream input, Class<M> messageClass) throws IOException {
        Preconditions.checkNotNull(input, "input");
        Preconditions.checkNotNull(messageClass, "messageClass");
        return parseFrom(WireInput.newInstance(input), messageClass);
    }

    public <M extends Message> M parseFrom(Source input, Class<M> messageClass) throws IOException {
        Preconditions.checkNotNull(input, "input");
        Preconditions.checkNotNull(messageClass, "messageClass");
        return parseFrom(WireInput.newInstance(input), messageClass);
    }

    private <M extends Message> M parseFrom(WireInput input, Class<M> messageClass) throws IOException {
        return messageAdapter(messageClass).read(input);
    }

    public static <T> T get(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
