package com.squareup.wire;

import com.squareup.wire.ExtendableMessage;
import com.squareup.wire.Message;
import java.util.Collections;
import java.util.List;

public abstract class ExtendableMessage<T extends ExtendableMessage<?>> extends Message {
    transient ExtensionMap<T> extensionMap;

    protected ExtendableMessage() {
    }

    /* access modifiers changed from: protected */
    public void setBuilder(ExtendableBuilder<T> builder) {
        super.setBuilder(builder);
        if (builder.extensionMap != null) {
            this.extensionMap = new ExtensionMap<>(builder.extensionMap);
        }
    }

    public List<Extension<T, ?>> getExtensions() {
        if (this.extensionMap == null) {
            return Collections.emptyList();
        }
        return this.extensionMap.getExtensions();
    }

    public <E> E getExtension(Extension<T, E> extension) {
        if (this.extensionMap == null) {
            return null;
        }
        return this.extensionMap.get(extension);
    }

    /* access modifiers changed from: protected */
    public boolean extensionsEqual(ExtendableMessage<T> other) {
        if (this.extensionMap == null) {
            return other.extensionMap == null;
        }
        return this.extensionMap.equals(other.extensionMap);
    }

    /* access modifiers changed from: protected */
    public int extensionsHashCode() {
        if (this.extensionMap == null) {
            return 0;
        }
        return this.extensionMap.hashCode();
    }

    /* access modifiers changed from: package-private */
    public String extensionsToString() {
        return this.extensionMap == null ? "{}" : this.extensionMap.toString();
    }

    public static abstract class ExtendableBuilder<T extends ExtendableMessage<?>> extends Message.Builder<T> {
        ExtensionMap<T> extensionMap;

        protected ExtendableBuilder() {
        }

        protected ExtendableBuilder(ExtendableMessage<T> message) {
            super(message);
            if (message != null && message.extensionMap != null) {
                this.extensionMap = new ExtensionMap<>(message.extensionMap);
            }
        }

        public <E> E getExtension(Extension<T, E> extension) {
            if (this.extensionMap == null) {
                return null;
            }
            return this.extensionMap.get(extension);
        }

        public <E> ExtendableBuilder<T> setExtension(Extension<T, E> extension, E value) {
            if (this.extensionMap == null) {
                this.extensionMap = new ExtensionMap<>(extension, value);
            } else {
                this.extensionMap.put(extension, value);
            }
            return this;
        }
    }
}
