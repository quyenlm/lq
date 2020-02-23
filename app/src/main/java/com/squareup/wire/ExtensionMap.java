package com.squareup.wire;

import com.squareup.wire.ExtendableMessage;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

final class ExtensionMap<T extends ExtendableMessage<?>> {
    private static final int INITIAL_SIZE = 1;
    private Object[] data;
    private int size;

    public <E> ExtensionMap(Extension<T, E> extension, E value) {
        this.data = new Object[2];
        this.data[0] = extension;
        this.data[1] = value;
        this.size = 1;
    }

    public ExtensionMap(ExtensionMap<T> other) {
        this.data = (Object[]) other.data.clone();
        this.size = other.size;
    }

    public int size() {
        return this.size;
    }

    public Extension<T, ?> getExtension(int index) {
        if (index >= 0 && index < this.size) {
            return (Extension) this.data[index];
        }
        throw new IndexOutOfBoundsException("" + index);
    }

    public Object getExtensionValue(int index) {
        if (index >= 0 && index < this.size) {
            return this.data[this.size + index];
        }
        throw new IndexOutOfBoundsException("" + index);
    }

    public List<Extension<T, ?>> getExtensions() {
        List<Extension<T, ?>> keyList = new ArrayList<>(this.size);
        for (int i = 0; i < this.size; i++) {
            keyList.add((Extension) this.data[i]);
        }
        return Collections.unmodifiableList(keyList);
    }

    public <E> E get(Extension<T, E> extension) {
        int index = Arrays.binarySearch(this.data, 0, this.size, extension);
        if (index < 0) {
            return null;
        }
        return this.data[this.size + index];
    }

    public <E> void put(Extension<T, E> extension, E value) {
        int index = Arrays.binarySearch(this.data, 0, this.size, extension);
        if (index >= 0) {
            this.data[this.size + index] = value;
        } else {
            insert(extension, value, -(index + 1));
        }
    }

    private <E> void insert(Extension<T, E> key, E value, int insertionPoint) {
        Object[] dest = this.data;
        if (this.data.length < (this.size + 1) * 2) {
            dest = new Object[(this.data.length * 2)];
            System.arraycopy(this.data, 0, dest, 0, insertionPoint);
        }
        if (insertionPoint < this.size) {
            System.arraycopy(this.data, this.size + insertionPoint, dest, this.size + insertionPoint + 2, this.size - insertionPoint);
            System.arraycopy(this.data, insertionPoint, dest, insertionPoint + 1, this.size);
        } else {
            System.arraycopy(this.data, this.size, dest, this.size + 1, this.size);
        }
        this.size++;
        this.data = dest;
        this.data[insertionPoint] = key;
        this.data[this.size + insertionPoint] = value;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ExtensionMap)) {
            return false;
        }
        ExtensionMap<T> other = (ExtensionMap) o;
        if (this.size != other.size) {
            return false;
        }
        for (int i = 0; i < this.size * 2; i++) {
            if (!this.data[i].equals(other.data[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 0;
        for (int i = 0; i < this.size * 2; i++) {
            result = (result * 37) + this.data[i].hashCode();
        }
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String sep = "";
        for (int i = 0; i < this.size; i++) {
            sb.append(sep);
            sb.append(((Extension) this.data[i]).getTag());
            sb.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            sb.append(this.data[this.size + i]);
            sep = ", ";
        }
        sb.append("}");
        return sb.toString();
    }
}
